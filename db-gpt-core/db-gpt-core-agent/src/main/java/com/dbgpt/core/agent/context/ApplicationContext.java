package com.dbgpt.core.agent.context;

import com.dbgpt.core.agent.component.ComponentRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 应用上下文
 * 提供全局的上下文管理和属性存储
 */
public class ApplicationContext {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);

    /**
     * 组件注册表
     */
    private final ComponentRegistry componentRegistry;

    /**
     * 上下文属性
     */
    private final Map<String, Object> attributes = new ConcurrentHashMap<>();

    public ApplicationContext() {
        this.componentRegistry = new ComponentRegistry();
    }

    public ApplicationContext(ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    /**
     * 获取组件注册表
     *
     * @return 组件注册表
     */
    public ComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }

    /**
     * 设置属性
     *
     * @param key   属性键
     * @param value 属性值
     */
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    /**
     * 获取属性
     *
     * @param key 属性键
     * @param <T> 属性类型
     * @return 属性值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }

    /**
     * 获取属性（带默认值）
     *
     * @param key          属性键
     * @param defaultValue 默认值
     * @param <T>          属性类型
     * @return 属性值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key, T defaultValue) {
        Object value = attributes.get(key);
        return value != null ? (T) value : defaultValue;
    }

    /**
     * 移除属性
     *
     * @param key 属性键
     * @return 被移除的属性值
     */
    public Object removeAttribute(String key) {
        return attributes.remove(key);
    }

    /**
     * 判断属性是否存在
     *
     * @param key 属性键
     * @return 如果存在返回 true
     */
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    /**
     * 获取所有属性
     *
     * @return 属性映射
     */
    public Map<String, Object> getAttributes() {
        return new ConcurrentHashMap<>(attributes);
    }

    /**
     * 清空所有属性
     */
    public void clearAttributes() {
        attributes.clear();
        logger.info("Application context attributes cleared");
    }
}