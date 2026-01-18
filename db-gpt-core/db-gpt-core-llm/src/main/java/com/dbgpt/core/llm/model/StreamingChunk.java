package com.dbgpt.core.llm.model;

/**
 * 流式输出分块
 */
public final class StreamingChunk {

    private final int index;          // 分块索引
    private final String content;      // 分块内容
    private final String delta;       // 增量内容（用于拼接）
    private final String finishReason; // 结束原因

    private StreamingChunk(Builder builder) {
        this.index = builder.index;
        this.content = builder.content;
        this.delta = builder.delta;
        this.finishReason = builder.finishReason;
    }

    // Static factory method
    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public int getIndex() {
        return index;
    }

    public String getContent() {
        return content;
    }

    public String getDelta() {
        return delta;
    }

    public String getFinishReason() {
        return finishReason;
    }

    // Builder
    public static class Builder {
        private int index;
        private String content;
        private String delta;
        private String finishReason;

        public Builder index(int index) {
            this.index = index;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder delta(String delta) {
            this.delta = delta;
            return this;
        }

        public Builder finishReason(String finishReason) {
            this.finishReason = finishReason;
            return this;
        }

        public StreamingChunk build() {
            return new StreamingChunk(this);
        }
    }
}