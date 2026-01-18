package com.dbgpt.core.llm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 模型生成响应
 */
public final class ModelOutput {

    // 1. 核心结果
    private final String content;               // 生成的内容
    private final String finishReason;          // 结束原因（length/stop）

    // 2. Token使用情况
    private final Integer promptTokens;          // 输入token数
    private final Integer completionTokens;       // 输出token数
    private final Integer totalTokens;           // 总token数

    // 3. 元数据
    private final String model;                 // 使用的模型
    private final Long timestamp;               // 响应时间戳

    // 4. 流式支持
    private final boolean streaming;              // 是否流式
    private final List<StreamingChunk> chunks; // 分块列表

    private ModelOutput(Builder builder) {
        this.content = builder.content;
        this.finishReason = builder.finishReason;
        this.promptTokens = builder.promptTokens;
        this.completionTokens = builder.completionTokens;
        this.totalTokens = calculateTotalTokens();
        this.model = builder.model;
        this.timestamp = builder.timestamp;
        this.streaming = builder.streaming;
        this.chunks = builder.chunks != null ? new ArrayList<>(builder.chunks) : new ArrayList<>();
    }

    private int calculateTotalTokens() {
        if (promptTokens != null && completionTokens != null) {
            return promptTokens + completionTokens;
        }
        return 0;
    }

    // Static factory method
    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public String getContent() {
        return content;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public Integer getPromptTokens() {
        return promptTokens;
    }

    public Integer getCompletionTokens() {
        return completionTokens;
    }

    public Integer getTotalTokens() {
        return totalTokens;
    }

    public String getModel() {
        return model;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public boolean isStreaming() {
        return streaming;
    }

    public List<StreamingChunk> getChunks() {
        return new ArrayList<>(chunks);
    }

    // Builder
    public static class Builder {
        private String content;
        private String finishReason;
        private Integer promptTokens;
        private Integer completionTokens;
        private String model;
        private Long timestamp;
        private boolean streaming = false;
        private List<StreamingChunk> chunks;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder finishReason(String finishReason) {
            this.finishReason = finishReason;
            return this;
        }

        public Builder promptTokens(int promptTokens) {
            this.promptTokens = promptTokens;
            return this;
        }

        public Builder completionTokens(int completionTokens) {
            this.completionTokens = completionTokens;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder streaming(boolean streaming) {
            this.streaming = streaming;
            return this;
        }

        public Builder chunks(List<StreamingChunk> chunks) {
            this.chunks = chunks;
            return this;
        }

        public ModelOutput build() {
            return new ModelOutput(this);
        }
    }

    @Override
    public String toString() {
        return "ModelOutput{model=" + model + ", tokens=" + totalTokens + ", streaming=" + streaming + "}";
    }
}
