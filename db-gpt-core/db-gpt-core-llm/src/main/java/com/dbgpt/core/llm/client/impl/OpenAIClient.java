package com.dbgpt.core.llm.client.impl;

import com.dbgpt.core.llm.client.AbstractLLMClient;
import com.dbgpt.core.llm.config.ModelConfig;
import com.dbgpt.core.llm.exception.LLMApiException;
import com.dbgpt.core.llm.exception.LLMException;
import com.dbgpt.core.llm.model.ModelMetadata;
import com.dbgpt.core.llm.model.ModelOutput;
import com.dbgpt.core.llm.model.ModelRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * OpenAI LLM客户端实现
 */
public class OpenAIClient extends AbstractLLMClient {

    private static final String DEFAULT_API_BASE = "https://api.openai.com/v1";
    private static final String SSE_DATA_PREFIX = "data: ";
    private static final Pattern SSE_FINISH_PATTERN = Pattern.compile("\"finish_reason\"\\s*:\\s*\"([^\"]+)\"");

    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenAIClient(ModelConfig config) throws LLMException {
        super(config);
    }

    @Override
    protected void validateConfig() throws LLMException {
        super.validateConfig();
        if (config.getApiBase() == null) {
            config.setApiBase(DEFAULT_API_BASE);
        }
    }

    @Override
    protected ModelOutput doGenerate(ModelRequest request) throws Exception {
        String requestBody = buildRequestBody(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(config.getApiBase() + "/chat/completions"))
                .header("Authorization", "Bearer " + config.getApiKey())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = sendHttpRequest(httpRequest);

        return parseResponse(response.body(), request.getModel());
    }

    @Override
    protected void doGenerateStream(ModelRequest request, Consumer<String> consumer) throws Exception {
        String requestBody = buildStreamRequestBody(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(config.getApiBase() + "/chat/completions"))
                .header("Authorization", "Bearer " + config.getApiKey())
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
                // 解析SSE格式：data: {...}
                if (line.startsWith(SSE_DATA_PREFIX)) {
                    String json = line.substring(SSE_DATA_PREFIX.length());
                    // 跳过 [DONE] 消息
                    if (json.equals("[DONE]")) {
                        break;
                    }
                    ModelOutput chunk = parseResponse(json, request.getModel());
                    consumer.accept(chunk.getContent());

                    // 检查是否完成
                    if (chunk.getFinishReason() != null) {
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected String buildRequestBody(ModelRequest request) {
        // 使用Jackson构建JSON
        try {
            return objectMapper.writeValueAsString(Map.of(
                    "model", request.getModel(),
                    "messages", request.getMessages(),
                    "temperature", request.getTemperature(),
                    "max_tokens", request.getMaxTokens(),
                    "top_p", request.getTopP(),
                    "stream", false
            ));
        } catch (Exception e) {
            logger.error("Failed to build request body", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String buildStreamRequestBody(ModelRequest request) {
        try {
            return objectMapper.writeValueAsString(Map.of(
                    "model", request.getModel(),
                    "messages", request.getMessages(),
                    "temperature", request.getTemperature(),
                    "max_tokens", request.getMaxTokens(),
                    "top_p", request.getTopP(),
                    "stream", true
            ));
        } catch (Exception e) {
            logger.error("Failed to build stream request body", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected ModelOutput parseResponse(String response, String model) throws LLMApiException {
        try {
            JsonNode root = objectMapper.readTree(response);

            // 错误检查
            if (root.has("error")) {
                JsonNode error = root.get("error");
                String message = error.has("message") ? error.get("message").asText() : "Unknown error";
                throw new LLMApiException(500, "API Error: " + message);
            }

            String content = root.path("choices").get(0)
                    .path("message").path("content").asText();

            String finishReason = root.path("choices").get(0)
                    .path("finish_reason").asText();

            JsonNode usage = root.path("usage");
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
    public List<ModelMetadata> listModels() {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(config.getApiBase() + "/models"))
                    .header("Authorization", "Bearer " + config.getApiKey())
                    .GET()
                    .build();

            HttpResponse<String> response = sendHttpRequest(httpRequest);

            JsonNode root = objectMapper.readTree(response.body());
            List<ModelMetadata> models = new ArrayList<>();

            for (JsonNode modelNode : root.path("data")) {
                ModelMetadata metadata = ModelMetadata.builder()
                        .id(modelNode.path("id").asText())
                        .name(modelNode.path("id").asText())
                        .provider("openai")
                        .maxTokens(modelNode.path("context_length").asInt())
                        .contextWindow(modelNode.path("context_length").asLong())
                        .build();
                models.add(metadata);
            }

            return models;
        } catch (Exception e) {
            logger.error("Failed to list models", e);
            return Collections.emptyList();
        }
    }
}
