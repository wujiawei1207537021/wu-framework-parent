package com.wu.framework.inner.layer.data;

/**
 * description 数据处理异常
 *
 * @author 吴佳伟
 * @date 2021/5/8 4:06 下午
 */
public class ProcessException extends Exception {
    public ProcessException(Throwable cause) {
        super(cause);
    }

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
