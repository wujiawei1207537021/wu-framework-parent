package com.wu.framework.response.exceptions;


import com.wu.framework.response.ResultCode;

/**
 * description 运行时异常工厂
 *
 * @author 吴佳伟
 * @company 杭州来回科技有限公司
 * @date 2022/5/31 7:22 下午
 */
public class RuntimeExceptionFactory {


    /**
     * 运行时异常
     *
     * @param resultCode 错误编码运行时异常
     */
    public static void of(ResultCode resultCode) {
        of("错误编码:【%s】,错误信息:【%s】", resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * 运行时异常转换成返回结果
     *
     * @param code
     * @param message
     */
    public static void of(Integer code, String message) {
        throw new DefaultRuntimeException(code, message);
    }

    /**
     * 运行时异常
     *
     * @param message
     */
    public static void of(String message) {
        throw new DefaultRuntimeException(message);
    }

    /**
     * 运行时异常
     *
     * @param cause 异常信息
     */
    public static void of(Throwable cause) {
        throw new DefaultRuntimeException(cause);
    }

    /**
     * 运行时异常
     *
     * @param message 中台消息推送编号错误:%s
     * @param args    参数信息
     */
    public static void of(String message, Object... args) {
        throw new DefaultRuntimeException(String.format(message, args));
    }
}
