package com.dbgpt.core.llm.model;

/**
 * 模型元数据
 */
public final class ModelMetadata {

    private final String id;              // 模型ID
    private final String name;            // 模型名称
    private final String provider;        // 提供商
    private final Integer maxTokens;      // 最大token数
    private final Long contextWindow;    // 上下文窗口大小

    private ModelMetadata(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.provider = builder.provider;
        this.maxTokens = builder.maxTokens;
        this.contextWindow = builder.contextWindow;
    }

    // Static factory method
    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getProvider() { return provider; }
    public Integer getMaxTokens() { return maxTokens; }
    public Long getContextWindow() { return contextWindow; }

    // Builder
    public static class Builder {
        private String id;
        private String name;
        private String provider;
        private Integer maxTokens;
        private Long contextWindow;

        public Builder id(String id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder provider(String provider) { this.provider = provider; return this; }
        public Builder maxTokens(Integer maxTokens) { this.maxTokens = maxTokens; return this; }
        public Builder contextWindow(Long contextWindow) { this.contextWindow = contextWindow; return this; }

        public ModelMetadata build() {
            return new ModelMetadata(this);
        }
    }
}