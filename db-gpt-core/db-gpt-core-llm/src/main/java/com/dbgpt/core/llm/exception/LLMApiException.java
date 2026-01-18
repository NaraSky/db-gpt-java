package com.dbgpt.core.llm.exception;

/**
 * LLM API异常
 * 用于表示API调用失败
 */
public class LLMApiException extends LLMException {

    private final int statusCode;

    public LLMApiException(int statusCode, String message) {
        super(message, "API_ERROR_" + statusCode);
        this.statusCode = statusCode;
    }

    public LLMApiException(int statusCode, String message, Throwable cause) {
        super(message, "API_ERROR_" + statusCode, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}