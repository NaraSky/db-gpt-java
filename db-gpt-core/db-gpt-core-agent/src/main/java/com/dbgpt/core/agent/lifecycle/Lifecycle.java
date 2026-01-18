package com.dbgpt.core.agent.lifecycle;

/**
 * 生命周期接口
 * 定义组件的标准生命周期方法
 */
public interface Lifecycle {

    /**
     * 初始化组件
     * 在组件启动前调用，用于初始化资源
     *
     * @throws Exception 初始化失败时抛出异常
     */
    void initialize() throws Exception;

    /**
     * 启动组件
     * 组件开始提供服务
     *
     * @throws Exception 启动失败时抛出异常
     */
    void start() throws Exception;

    /**
     * 停止组件
     * 组件停止提供服务，释放资源
     *
     * @throws Exception 停止失败时抛出异常
     */
    void stop() throws Exception;

    /**
     * 销毁组件
     * 在组件停止后调用，用于清理资源
     *
     * @throws Exception 销毁失败时抛出异常
     */
    void destroy() throws Exception;
}