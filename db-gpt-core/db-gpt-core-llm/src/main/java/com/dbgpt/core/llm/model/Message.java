package com.dbgpt.core.llm.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 对话消息
 */
public final class Message {

    // 1. 基本信息
    private final String id;              // 消息唯一标识
    private final Role role;             // 角色（用户/助手/系统）
    private final String content;          // 消息内容

    // 2. 元数据
    private final String model;            // 使用的模型
    private final Instant createdAt;        // 创建时间
    private final Map<String, Object> metadata; // 扩展元数据

    // 私有构造器，强制使用Builder
    private Message(Builder builder) {
        this.id = builder.id;
        this.role = builder.role;
        this.content = builder.content;
        this.model = builder.model;
        this.createdAt = builder.createdAt;
        this.metadata = builder.metadata != null ? new HashMap<>(builder.metadata) : new HashMap<>();
    }

    // Static factory method
    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public String getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public String getModel() {
        return model;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Map<String, Object> getMetadata() {
        return new HashMap<>(metadata);
    }

    // Builder
    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private Role role = Role.USER;
        private String content;
        private String model;
        private Instant createdAt = Instant.now();
        private Map<String, Object> metadata;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }

    @Override
    public String toString() {
        return "Message{id=" + id + ", role=" + role + ", content='" + content + "'}";
    }
}