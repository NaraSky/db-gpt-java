package com.dbgpt.core.llm.adapter;

import java.util.Map;

/**
 * 模型适配器接口
 * 用于适配不同格式的模型
 */
public interface ModelAdapter {

    /**
     * 加载模型
     *
     * @param modelPath 模型路径
     * @param params 加载参数
     * @return 模型实例
     */
    Object load(String modelPath, Map<String, Object> params);

    /**
     * 匹配模型
     *
     * @param provider 提供商
     * @param modelName 模型名称
     * @param modelPath 模型路径
     * @return 是否匹配
     */
    boolean match(String provider, String modelName, String modelPath);
}