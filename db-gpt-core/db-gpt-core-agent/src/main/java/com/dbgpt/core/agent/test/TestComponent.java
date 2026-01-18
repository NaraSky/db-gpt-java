package com.dbgpt.core.agent.test;

import com.dbgpt.common.constant.ComponentType;
import com.dbgpt.core.agent.component.AbstractComponent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试组件
 * 用于验证系统应用和组件生命周期
 */
public class TestComponent extends AbstractComponent {

    private static final AtomicInteger counter = new AtomicInteger(0);

    private final int id;
    private final boolean shouldFail;

    public TestComponent(String name, ComponentType type, int priority) {
        this(name, type, priority, false);
    }

    public TestComponent(String name, ComponentType type, int priority, boolean shouldFail) {
        super(name, type, priority);
        this.id = counter.incrementAndGet();
        this.shouldFail = shouldFail;
    }

    @Override
    protected void doInitialize() throws Exception {
        logger.info("TestComponent #{} initializing...", id);
        if (shouldFail) {
            throw new RuntimeException("Simulated initialization failure");
        }
        Thread.sleep(100);
        logger.info("TestComponent #{} initialized", id);
    }

    @Override
    protected void doStart() throws Exception {
        logger.info("TestComponent #{} starting...", id);
        if (shouldFail) {
            throw new RuntimeException("Simulated start failure");
        }
        Thread.sleep(100);
        logger.info("TestComponent #{} started", id);
    }

    @Override
    protected void doStop() throws Exception {
        logger.info("TestComponent #{} stopping...", id);
        Thread.sleep(100);
        logger.info("TestComponent #{} stopped", id);
    }

    @Override
    protected void doDestroy() throws Exception {
        logger.info("TestComponent #{} destroying...", id);
        Thread.sleep(100);
        logger.info("TestComponent #{} destroyed", id);
    }

    public int getId() {
        return id;
    }
}
