package com.dbgpt.core.llm.provider;

import com.dbgpt.core.llm.client.LLMClient;
import com.dbgpt.core.llm.config.ModelConfig;

/**
 * LLM提供商接口
 * 定义不同提供商的统一抽象
 */
public interface LLMProvider {

    /**
     * 获取提供商名称
     */
    String getName();

    /**
     * 获取提供商类型
     */
    ProviderType getType();

    /**
     * 创建LLM客户端
     *
     * @param config 配置
     * @return 客户端实例
     */
    LLMClient createClient(ModelConfig config);

    /**
     * 验证配置
     *
     * @param config 配置
     * @return 是否有效
     */
    boolean validateConfig(ModelConfig config);

    /**
     * 获取默认模型列表
     */
    java.util.List<String> getDefaultModels();
}