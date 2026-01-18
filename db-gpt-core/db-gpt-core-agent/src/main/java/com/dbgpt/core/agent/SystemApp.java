package com.dbgpt.core.agent;

import com.dbgpt.core.agent.component.Component;
import com.dbgpt.core.agent.component.ComponentRegistry;
import com.dbgpt.core.agent.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 系统应用
 * 这是 DB-GPT 框架的核心应用类，管理所有组件的生命周期
 */
public class SystemApp {

    private static final Logger logger = LoggerFactory.getLogger(SystemApp.class);

    /**
     * 应用名称
     */
    private final String name;

    /**
     * 应用上下文
     */
    private final ApplicationContext context;

    /**
     * 组件注册表
     */
    private final ComponentRegistry componentRegistry;

    /**
     * 是否已启动
     */
    private volatile boolean started = false;

    public SystemApp(String name) {
        this.name = name;
        this.context = new ApplicationContext();
        this.componentRegistry = context.getComponentRegistry();
    }

    public SystemApp(String name, ApplicationContext context) {
        this.name = name;
        this.context = context;
        this.componentRegistry = context.getComponentRegistry();
    }

    /**
     * 初始化应用
     * 初始化所有已注册的组件
     *
     * @throws Exception 初始化失败
     */
    public void initialize() throws Exception {
        logger.info("Initializing application: {}", name);

        List<Component> components = componentRegistry.getAllSorted();
        for (Component component : components) {
            component.initialize();
        }

        logger.info("Application initialized: {}", name);
    }

    /**
     * 启动应用
     * 启动所有已初始化的组件
     *
     * @throws Exception 启动失败
     */
    public void start() throws Exception {
        if (started) {
            logger.warn("Application is already started: {}", name);
            return;
        }

        logger.info("Starting application: {}", name);

        List<Component> components = componentRegistry.getAllSorted();
        for (Component component : components) {
            component.start();
        }

        started = true;
        logger.info("Application started: {}", name);
    }

    /**
     * 停止应用
     * 停止所有运行中的组件
     *
     * @throws Exception 停止失败
     */
    public void stop() throws Exception {
        if (!started) {
            logger.warn("Application is not started: {}", name);
            return;
        }

        logger.info("Stopping application: {}", name);

        List<Component> components = componentRegistry.getAll();
        // 按优先级逆序停止
        components.sort((a, b) -> Integer.compare(a.getPriority(), b.getPriority()));

        for (Component component : components) {
            try {
                component.stop();
            } catch (Exception e) {
                logger.error("Error stopping component: {}", component.getName(), e);
            }
        }

        started = false;
        logger.info("Application stopped: {}", name);
    }

    /**
     * 销毁应用
     * 销毁所有组件
     *
     * @throws Exception 销毁失败
     */
    public void destroy() throws Exception {
        logger.info("Destroying application: {}", name);

        List<Component> components = componentRegistry.getAll();
        for (Component component : components) {
            try {
                component.destroy();
            } catch (Exception e) {
                logger.error("Error destroying component: {}", component.getName(), e);
            }
        }

        logger.info("Application destroyed: {}", name);
    }

    /**
     * 注册组件
     *
     * @param component 要注册的组件
     */
    public void registerComponent(Component component) {
        componentRegistry.register(component);
    }

    /**
     * 获取组件
     *
     * @param name 组件名称
     * @param <T>  组件类型
     * @return 组件实例
     */
    public <T extends Component> T getComponent(String name) {
        return componentRegistry.get(name);
    }

    /**
     * 获取应用上下文
     *
     * @return 应用上下文
     */
    public ApplicationContext getContext() {
        return context;
    }

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    public String getName() {
        return name;
    }

    /**
     * 判断应用是否已启动
     *
     * @return 如果已启动返回 true
     */
    public boolean isStarted() {
        return started;
    }
}