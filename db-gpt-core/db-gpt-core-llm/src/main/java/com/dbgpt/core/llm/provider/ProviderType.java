package com.dbgpt.core.llm.provider;

/**
 * LLM提供商类型枚举
 */
public enum ProviderType {
    OPENAI("openai"),
    PROXY("proxy"),
    HUGGINGFACE("huggingface"),
    ANTHROPIC("anthropic"),
    DEEPSEEK("deepseek"),
    QWEN("qwen");

    private final String value;

    ProviderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ProviderType fromString(String value) {
        for (ProviderType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown provider type: " + value);
    }
}