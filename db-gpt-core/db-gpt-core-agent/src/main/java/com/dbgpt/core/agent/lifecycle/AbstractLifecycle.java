package com.dbgpt.core.agent.lifecycle;

import com.dbgpt.common.constant.ComponentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生命周期抽象基类
 * 提供生命周期的默认实现
 */
public abstract class AbstractLifecycle implements Lifecycle {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 组件名称
     */
    protected String name;

    /**
     * 组件状态
     */
    protected ComponentStatus status = ComponentStatus.UNINITIALIZED;

    public AbstractLifecycle(String name) {
        this.name = name;
    }

    public AbstractLifecycle() {
        this.name = getClass().getSimpleName();
    }

    @Override
    public void initialize() throws Exception {
        if (status != ComponentStatus.UNINITIALIZED) {
            throw new IllegalStateException("Component is already initialized or running: " + name);
        }
        logger.info("Initializing component: {}", name);
        status = ComponentStatus.INITIALIZING;
        doInitialize();
        status = ComponentStatus.INITIALIZED;
        logger.info("Component initialized: {}", name);
    }

    @Override
    public void start() throws Exception {
        if (status != ComponentStatus.INITIALIZED) {
            throw new IllegalStateException("Component is not initialized: " + name);
        }
        logger.info("Starting component: {}", name);
        status = ComponentStatus.STARTING;
        doStart();
        status = ComponentStatus.RUNNING;
        logger.info("Component started: {}", name);
    }

    @Override
    public void stop() throws Exception {
        if (status != ComponentStatus.RUNNING) {
            logger.warn("Component is not running: {}", name);
            return;
        }
        logger.info("Stopping component: {}", name);
        status = ComponentStatus.STOPPING;
        doStop();
        status = ComponentStatus.STOPPED;
        logger.info("Component stopped: {}", name);
    }

    @Override
    public void destroy() throws Exception {
        if (status != ComponentStatus.STOPPED && status != ComponentStatus.INITIALIZED) {
            logger.warn("Component is not in a state that can be destroyed: {}", name);
            return;
        }
        logger.info("Destroying component: {}", name);
        doDestroy();
        status = ComponentStatus.UNINITIALIZED;
        logger.info("Component destroyed: {}", name);
    }

    /**
     * 子类实现具体的初始化逻辑
     */
    protected abstract void doInitialize() throws Exception;

    /**
     * 子类实现具体的启动逻辑
     */
    protected abstract void doStart() throws Exception;

    /**
     * 子类实现具体的停止逻辑
     */
    protected abstract void doStop() throws Exception;

    /**
     * 子类实现具体的销毁逻辑
     */
    protected abstract void doDestroy() throws Exception;

    public String getName() {
        return name;
    }

    public ComponentStatus getStatus() {
        return status;
    }
}