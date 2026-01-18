package com.dbgpt.core.llm.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模型生成请求
 */
public final class ModelRequest {

    // 1. 核心参数
    private final List<Message> messages;      // 对话历史
    private final String model;                  // 模型名称

    // 2. 生成参数
    private final Double temperature;            // 温度（0-2，越高越随机）
    private final Integer maxTokens;              // 最大生成token数
    private final Double topP;                   // 核采样概率
    private final Integer topK;                   // 采样候选数

    // 3. 流式控制
    private final boolean stream;                 // 是否流式输出

    // 4. 扩展参数
    private final Map<String, Object> additionalParams; // 其他参数

    private ModelRequest(Builder builder) {
        this.messages = builder.messages;
        this.model = builder.model;
        this.temperature = builder.temperature;
        this.maxTokens = builder.maxTokens;
        this.topP = builder.topP;
        this.topK = builder.topK;
        this.stream = builder.stream;
        this.additionalParams = builder.additionalParams != null
                ? new HashMap<>(builder.additionalParams)
                : new HashMap<>();
    }

    // Static factory method
    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public List<Message> getMessages() {
        return messages;
    }

    public String getModel() {
        return model;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public Double getTopP() {
        return topP;
    }

    public Integer getTopK() {
        return topK;
    }

    public boolean isStream() {
        return stream;
    }

    public Map<String, Object> getAdditionalParams() {
        return new HashMap<>(additionalParams);
    }

    // Builder
    public static class Builder {
        private List<Message> messages;
        private String model;
        private Double temperature = 0.7;
        private Integer maxTokens = 2048;
        private Double topP = 1.0;
        private Integer topK;
        private boolean stream = false;
        private Map<String, Object> additionalParams;

        public Builder messages(List<Message> messages) {
            this.messages = messages;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder temperature(Double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder maxTokens(Integer maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        public Builder topP(Double topP) {
            this.topP = topP;
            return this;
        }

        public Builder topK(Integer topK) {
            this.topK = topK;
            return this;
        }

        public Builder stream(boolean stream) {
            this.stream = stream;
            return this;
        }

        public Builder addParam(String key, Object value) {
            if (this.additionalParams == null) {
                this.additionalParams = new HashMap<>();
            }
            this.additionalParams.put(key, value);
            return this;
        }

        public ModelRequest build() {
            return new ModelRequest(this);
        }
    }
}