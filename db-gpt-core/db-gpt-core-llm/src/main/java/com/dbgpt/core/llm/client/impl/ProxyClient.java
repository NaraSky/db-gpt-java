package com.dbgpt.core.llm.client.impl;

import com.dbgpt.core.llm.client.AbstractLLMClient;
import com.dbgpt.core.llm.config.ModelConfig;
import com.dbgpt.core.llm.exception.LLMApiException;
import com.dbgpt.core.llm.exception.LLMException;
import com.dbgpt.core.llm.model.ModelMetadata;
import com.dbgpt.core.llm.model.ModelOutput;
import com.dbgpt.core.llm.model.ModelRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 代理LLM客户端实现
 * 通过中转代理服务调用不同的LLM提供商
 */
public class ProxyClient extends AbstractLLMClient {

    private static final String DEFAULT_API_BASE = "http://localhost:5670/api/v1";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProxyClient(ModelConfig config) throws LLMException {
        super(config);
    }

    @Override
    protected void validateConfig() {
        // 代理服务可能有不同的验证逻辑
        if (config.getApiBase() == null) {
            config.setApiBase(DEFAULT_API_BASE);
        }
    }

    @Override
    protected ModelOutput doGenerate(ModelRequest request) throws Exception {
        // 代理服务通常与OpenAI API兼容
        String requestBody = buildRequestBody(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(config.getApiBase() + "/chat/completions"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = sendHttpRequest(httpRequest);

        return parseResponse(response.body(), request.getModel());
    }

    @Override
    protected void doGenerateStream(ModelRequest request, Consumer<String> consumer) throws Exception {
        // 实现代理的流式调用
        String requestBody = buildStreamRequestBody(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(config.getApiBase() + "/chat/completions"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // 同步方式处理流式响应
        HttpResponse<java.io.InputStream> response = httpClient.send(httpRequest,
                HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            throw new LLMApiException(response.statusCode(), "API request failed");
        }

        try (BufferedReader reader = new BufferedReader(
                new java.io.InputStreamReader(response.body()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("data: ")) {
                    String json = line.substring(6);
                    // 跳过 [DONE] 消息
                    if (json.equals("[DONE]")) {
                        break;
                    }
                    ModelOutput chunk = parseResponse(json, request.getModel());
                    consumer.accept(chunk.getContent());
                }
            }
        }
    }

    @Override
    protected String buildRequestBody(ModelRequest request) {
        // 使用与OpenAI相同的请求格式
        try {
            Map<String, Object> body = new java.util.HashMap<>();
            body.put("model", request.getModel());
            body.put("messages", request.getMessages());
            body.put("temperature", request.getTemperature());
            body.put("max_tokens", request.getMaxTokens());
            body.put("top_p", request.getTopP());
            body.put("stream", false);
            return objectMapper.writeValueAsString(body);
        } catch (Exception e) {
            logger.error("Failed to build request body", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String buildStreamRequestBody(ModelRequest request) {
        try {
            Map<String, Object> body = new java.util.HashMap<>();
            body.put("model", request.getModel());
            body.put("messages", request.getMessages());
            body.put("temperature", request.getTemperature());
            body.put("max_tokens", request.getMaxTokens());
            body.put("top_p", request.getTopP());
            body.put("stream", true);
            return objectMapper.writeValueAsString(body);
        } catch (Exception e) {
            logger.error("Failed to build stream request body", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected ModelOutput parseResponse(String response, String model) throws LLMApiException {
        // 与OpenAI兼容的解析
        try {
            com.fasterxml.jackson.databind.JsonNode root = objectMapper.readTree(response);

            // 错误检查
            if (root.has("error")) {
                com.fasterxml.jackson.databind.JsonNode error = root.get("error");
                String message = error.has("message") ? error.get("message").asText() : "Unknown error";
                throw new LLMApiException(500, "API Error: " + message);
            }

            String content = root.path("choices").get(0)
                    .path("message").path("content").asText();

            String finishReason = root.path("choices").get(0)
                    .path("finish_reason").asText();

            com.fasterxml.jackson.databind.JsonNode usage = root.path("usage");
            int promptTokens = usage.has("prompt_tokens") ? usage.get("prompt_tokens").asInt() : 0;
            int completionTokens = usage.has("completion_tokens") ? usage.get("completion_tokens").asInt() : 0;

            return ModelOutput.builder()
                    .content(content)
                    .finishReason(finishReason)
                    .promptTokens(promptTokens)
                    .completionTokens(completionTokens)
                    .model(model)
                    .timestamp(System.currentTimeMillis())
                    .build();
        } catch (Exception e) {
            logger.error("Failed to parse response: {}", response, e);
            throw new LLMApiException(500, "Failed to parse response: " + e.getMessage());
        }
    }

    @Override
    public java.util.List<ModelMetadata> listModels() {
        // 返回空列表，或者调用代理服务的模型列表接口
        return Collections.emptyList();
    }
}
