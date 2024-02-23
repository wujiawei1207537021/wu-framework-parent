package com.wu.framework.response.exceptions;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
