package com.wu.framework.response.exceptions;

/**
 * 自定义异常
 */
public class ShiroException extends RuntimeException {
    public ShiroException(String message) {
        super(message);
    }

    public ShiroException(Throwable cause) {
        super(cause);
    }
}
