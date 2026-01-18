package com.dbgpt.core.agent.component;

import com.dbgpt.common.constant.ComponentType;
import com.dbgpt.core.agent.lifecycle.Lifecycle;

/**
 * 组件接口
 * 定义系统中的所有组件
 */
public interface Component extends Lifecycle {

    /**
     * 获取组件名称
     *
     * @return 组件名称
     */
    String getName();

    /**
     * 获取组件类型
     *
     * @return 组件类型
     */
    ComponentType getType();

    /**
     * 获取组件优先级
     * 优先级高的组件先初始化和启动
     *
     * @return 优先级，数值越大优先级越高
     */
    default int getPriority() {
        return 0;
    }

    /**
     * 判断组件是否是核心组件
     *
     * @return 如果是核心组件返回 true
     */
    default boolean isCore() {
        return getType() == ComponentType.CORE;
    }
}