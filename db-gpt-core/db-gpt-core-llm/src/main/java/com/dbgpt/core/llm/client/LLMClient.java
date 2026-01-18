package com.dbgpt.core.llm.client;

import com.dbgpt.core.llm.exception.LLMException;
import com.dbgpt.core.llm.model.ModelMetadata;
import com.dbgpt.core.llm.model.ModelOutput;
import com.dbgpt.core.llm.model.ModelRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * LLM客户端接口
 * 定义与大语言模型交互的标准方式
 */
public interface LLMClient {

    /**
     * 同步生成文本
     *
     * @param request 生成请求
     * @return 生成结果
     * @throws LLMException 生成失败
     */
    ModelOutput generate(ModelRequest request) throws LLMException;

    /**
     * 异步生成文本
     *
     * @param request 生成请求
     * @return 异步结果
     */
    CompletableFuture<ModelOutput> generateAsync(ModelRequest request);

    /**
     * 流式生成文本
     *
     * @param request 生成请求
     * @param consumer 消息消费者（接收每个分块）
     * @throws LLMException 生成失败
     */
    void generateStream(ModelRequest request, Consumer<String> consumer) throws LLMException;

    /**
     * 获取支持的模型列表
     *
     * @return 模型列表
     */
    List<ModelMetadata> listModels();

    /**
     * 获取客户端配置
     *
     * @return 配置信息
     */
    ClientConfig getConfig();
}