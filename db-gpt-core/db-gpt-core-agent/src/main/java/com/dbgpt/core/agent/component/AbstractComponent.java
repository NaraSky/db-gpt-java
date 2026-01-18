package com.dbgpt.core.agent.component;

import com.dbgpt.common.constant.ComponentType;
import com.dbgpt.core.agent.lifecycle.AbstractLifecycle;

/**
 * 组件抽象基类
 * 提供组件的默认实现
 */
public abstract class AbstractComponent extends AbstractLifecycle implements Component {

    /**
     * 组件类型
     */
    protected final ComponentType type;

    /**
     * 组件优先级
     */
    protected final int priority;

    public AbstractComponent(String name, ComponentType type, int priority) {
        super(name);
        this.type = type;
        this.priority = priority;
    }

    public AbstractComponent(ComponentType type, int priority) {
        super();
        this.type = type;
        this.priority = priority;
    }

    public AbstractComponent(ComponentType type) {
        this(type, 0);
    }

    @Override
    public ComponentType getType() {
        return type;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}