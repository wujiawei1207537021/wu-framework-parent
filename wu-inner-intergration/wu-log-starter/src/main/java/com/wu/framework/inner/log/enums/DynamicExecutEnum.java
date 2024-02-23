package com.wu.framework.inner.log.enums;

public class DynamicExecutEnum {

    /**
     * 执行模式
     */
    public enum ExecutionMode {
        /**
         * 成功
         */
        SUCCESS,
        /**
         * 失败
         */
        FAIL,
        /**
         * 总是
         */
        ALWAYS;
    }

    /**
     * 执行时机
     */
    public enum ExecutionTiming {
        /**
         * 之前
         */
        BEFORE,
        /**
         * 之后
         */
        AFTER,
        /**
         * 之后返回
         */
        AFTER_RETURNING,
        /**
         * 之后异常
         */
        AFTER_THROWING,
        /**
         * 之后立体环绕
         */
        AROUND;
    }

    /**
     * 参数模式
     */
    public enum ParamMode {
        /**
         * 出参
         */
        MODE_IN,
        /**
         * 入参
         */
        MODE_OUT,
        /**
         * 无
         */
        MODE_EMPTY,
        /**
         * 出参和入参
         */
        MODE_IN_AND_OUT;
    }

}
