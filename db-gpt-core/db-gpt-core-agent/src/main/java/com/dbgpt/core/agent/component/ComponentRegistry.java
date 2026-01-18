package com.dbgpt.core.agent.component;

import com.dbgpt.common.constant.ComponentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 组件注册表
 * 管理系统中所有注册的组件
 */
public class ComponentRegistry {

    private static final Logger logger = LoggerFactory.getLogger(ComponentRegistry.class);

    /**
     * 组件名称到组件的映射
     */
    private final Map<String, Component> components = new ConcurrentHashMap<>();

    /**
     * 按类型分组的组件
     */
    private final Map<ComponentType, List<Component>> componentsByType = new ConcurrentHashMap<>();

    /**
     * 注册组件
     *
     * @param component 要注册的组件
     * @throws IllegalArgumentException 如果组件已注册
     */
    public void register(Component component) {
        String name = component.getName();
        if (components.containsKey(name)) {
            throw new IllegalArgumentException("Component already registered: " + name);
        }
        components.put(name, component);

        // 按类型分组
        componentsByType.computeIfAbsent(component.getType(), k -> new ArrayList<>())
                .add(component);

        logger.info("Component registered: {} [type: {}, priority: {}]",
                name, component.getType(), component.getPriority());
    }

    /**
     * 取消注册组件
     *
     * @param name 组件名称
     * @return 被移除的组件，如果不存在返回 null
     */
    public Component unregister(String name) {
        Component component = components.remove(name);
        if (component != null) {
            componentsByType.getOrDefault(component.getType(), new ArrayList<>())
                    .remove(component);
            logger.info("Component unregistered: {}", name);
        }
        return component;
    }

    /**
     * 获取组件
     *
     * @param name 组件名称
     * @param <T>  组件类型
     * @return 组件实例
     * @throws NoSuchElementException 如果组件不存在
     */
    @SuppressWarnings("unchecked")
    public <T extends Component> T get(String name) {
        Component component = components.get(name);
        if (component == null) {
            throw new NoSuchElementException("Component not found: " + name);
        }
        return (T) component;
    }

    /**
     * 获取指定类型的所有组件
     *
     * @param type 组件类型
     * @return 组件列表
     */
    public List<Component> getByType(ComponentType type) {
        return new ArrayList<>(componentsByType.getOrDefault(type, Collections.emptyList()));
    }

    /**
     * 获取所有组件
     *
     * @return 所有组件的列表
     */
    public List<Component> getAll() {
        return new ArrayList<>(components.values());
    }

    /**
     * 获取所有组件（按优先级排序）
     *
     * @return 排序后的组件列表
     */
    public List<Component> getAllSorted() {
        List<Component> sorted = getAll();
        sorted.sort((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));
        return sorted;
    }

    /**
     * 判断组件是否已注册
     *
     * @param name 组件名称
     * @return 如果已注册返回 true
     */
    public boolean contains(String name) {
        return components.containsKey(name);
    }

    /**
     * 获取注册的组件数量
     *
     * @return 组件数量
     */
    public int size() {
        return components.size();
    }

    /**
     * 清空所有组件
     */
    public void clear() {
        components.clear();
        componentsByType.clear();
        logger.info("Component registry cleared");
    }
}