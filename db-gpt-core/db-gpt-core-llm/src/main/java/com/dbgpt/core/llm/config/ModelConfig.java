package com.dbgpt.core.llm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * LLM模型配置
 */
@Component
@ConfigurationProperties(prefix = "dbgpt.llm")
public class ModelConfig {

    // 1. 连接配置
    private String provider = "proxy";      // 提供商（openai/proxy/huggingface）
    private String apiKey;                      // API密钥
    private String apiBase;                      // API基础URL
    private Integer timeout = 60;                // 超时时间（秒）

    // 2. 模型配置
    private String defaultModel = "gpt-4o";   // 默认模型
    private Map<String, String> models = new HashMap<>();  // 模型映射表

    // 3. 生成参数
    private Double defaultTemperature = 0.7;    // 默认温度
    private Integer defaultMaxTokens = 2048;      // 默认最大token

    // 4. 功能开关
    private boolean enableCache = true;            // 是否启用缓存
    private boolean enableMetrics = true;           // 是否启用指标收集
    private boolean enableStream = false;           // 是否启用流式输出

    // Getters and Setters
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getApiBase() { return apiBase; }
    public void setApiBase(String apiBase) { this.apiBase = apiBase; }

    public Integer getTimeout() { return timeout; }
    public void setTimeout(Integer timeout) { this.timeout = timeout; }

    public String getDefaultModel() { return defaultModel; }
    public void setDefaultModel(String defaultModel) { this.defaultModel = defaultModel; }

    public Map<String, String> getModels() { return new HashMap<>(models); }
    public void setModels(Map<String, String> models) { this.models = models; }

    public Double getDefaultTemperature() { return defaultTemperature; }
    public void setDefaultTemperature(Double defaultTemperature) { this.defaultTemperature = defaultTemperature; }

    public Integer getDefaultMaxTokens() { return defaultMaxTokens; }
    public void setDefaultMaxTokens(Integer defaultMaxTokens) { this.defaultMaxTokens = defaultMaxTokens; }

    public boolean isEnableCache() { return enableCache; }
    public void setEnableCache(boolean enableCache) { this.enableCache = enableCache; }

    public boolean isEnableMetrics() { return enableMetrics; }
    public void setEnableMetrics(boolean enableMetrics) { this.enableMetrics = enableMetrics; }

    public boolean isEnableStream() { return enableStream; }
    public void setEnableStream(boolean enableStream) { this.enableStream = enableStream; }
}
