package com.dbgpt.core.llm.model;

/**
 * 消息角色枚举
 */
public enum Role {
    /**
     * 用户消息
     */
    USER("user"),

    /**
     * 助手消息
     */
    ASSISTANT("assistant"),

    /**
     * 系统消息（用于设置行为指令）
     */
    SYSTEM("system");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}