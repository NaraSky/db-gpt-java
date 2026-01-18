package com.dbgpt.core.llm.strategy;

import com.dbgpt.core.llm.config.ModelConfig;
import com.dbgpt.core.llm.model.ModelRequest;

/**
 * 请求构建策略接口
 */
public interface RequestBuilderStrategy {

    /**
     * 构建请求体（非流式）
     */
    String build(ModelRequest request, ModelConfig config);

    /**
     * 构建流式请求体
     */
    String buildStream(ModelRequest request, ModelConfig config);
}