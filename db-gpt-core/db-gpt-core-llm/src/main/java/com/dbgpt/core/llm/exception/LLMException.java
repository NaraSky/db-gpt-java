package com.dbgpt.core.llm.exception;

/**
 * LLM异常基类
 */
public class LLMException extends Exception {
    private final String errorCode;

    public LLMException(String message) {
        super(message);
        this.errorCode = null;
    }

    public LLMException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    public LLMException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public LLMException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}