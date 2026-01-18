package com.dbgpt.core.llm.client;

public interface ClientConfig {
    String getApiKey();
    String getApiBase();
    Integer getTimeout();
}