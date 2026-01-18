package com.dbgpt.common.constant;

/**
 * 组件状态枚举
 * 定义组件的生命周期状态
 */
public enum ComponentStatus {
    /**
     * 未初始化
     */
    UNINITIALIZED,

    /**
     * 初始化中
     */
    INITIALIZING,

    /**
     * 已初始化
     */
    INITIALIZED,

    /**
     * 启动中
     */
    STARTING,

    /**
     * 运行中
     */
    RUNNING,

    /**
     * 停止中
     */
    STOPPING,

    /**
     * 已停止
     */
    STOPPED,

    /**
     * 错误状态
     */
    ERROR
}