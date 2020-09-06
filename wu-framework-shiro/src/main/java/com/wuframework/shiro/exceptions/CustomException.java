package com.wuframework.shiro.exceptions;

/**
 * 自定义异常
 * 请使用 {@link com.wuframework.response.exceptions.CustomException}
 */

@Deprecated
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
