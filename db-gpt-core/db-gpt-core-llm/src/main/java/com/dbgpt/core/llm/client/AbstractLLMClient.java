package com.dbgpt.core.llm.client;

import com.dbgpt.core.llm.config.ModelConfig;
import com.dbgpt.core.llm.exception.LLMApiException;
import com.dbgpt.core.llm.exception.LLMException;
import com.dbgpt.core.llm.model.ModelOutput;
import com.dbgpt.core.llm.model.ModelRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * LLM客户端抽象基类
 * 提供通用的客户端功能
 */
public abstract class AbstractLLMClient implements LLMClient, ClientConfig {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final ModelConfig config;
    protected final HttpClient httpClient;

    public AbstractLLMClient(ModelConfig config) throws LLMException {
        this.config = config;
        this.httpClient = createHttpClient();
        initialize();
    }

    protected void initialize() throws LLMException {
        validateConfig();
        logger.info("Initializing {} client for model: {}", getClass().getSimpleName(), config.getDefaultModel());
    }

    protected void destroy() {
        if (httpClient != null) {
            httpClient.close();
        }
    }

    protected HttpClient createHttpClient() {
        return HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(config.getTimeout())).build();
    }

    protected void validateConfig() throws LLMException {
        if (config.getApiKey() == null) {
            throw new LLMException("API key is required");
        }
    }

    @Override
    public ModelOutput generate(ModelRequest request) throws LLMException {
        try {
            return doGenerate(request);
        } catch (Exception e) {
            logger.error("Failed to generate response", e);
            throw new LLMException("Generation failed: " + e.getMessage(), e);
        }
    }

    @Override
    public CompletableFuture<ModelOutput> generateAsync(ModelRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return generate(request);
            } catch (LLMException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void generateStream(ModelRequest request, Consumer<String> consumer) throws LLMException {
        try {
            doGenerateStream(request, consumer);
        } catch (Exception e) {
            logger.error("Failed to generate stream", e);
            throw new LLMException("Stream generation failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String getApiKey() {
        return config.getApiKey();
    }

    @Override
    public String getApiBase() {
        return config.getApiBase();
    }

    @Override
    public Integer getTimeout() {
        return config.getTimeout();
    }

    @Override
    public ClientConfig getConfig() {
        return this;
    }

    protected abstract ModelOutput doGenerate(ModelRequest request) throws Exception;

    protected abstract void doGenerateStream(ModelRequest request, Consumer<String> consumer) throws Exception;

    protected abstract String buildRequestBody(ModelRequest request);

    protected abstract String buildStreamRequestBody(ModelRequest request);

    protected abstract ModelOutput parseResponse(String response, String model) throws LLMApiException;

    protected HttpResponse<String> sendHttpRequest(HttpRequest httpRequest) throws Exception {
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new LLMApiException(response.statusCode(), "API request failed");
        }
        return response;
    }
}
