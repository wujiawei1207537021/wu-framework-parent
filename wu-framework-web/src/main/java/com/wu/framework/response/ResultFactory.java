package com.wu.framework.response;

import com.alibaba.fastjson.JSON;
import com.wu.framework.response.enmus.DefaultResultCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.util.Map;

public class ResultFactory {
    public static <T> Result<T> of(Integer code, String message) {
        return new Result<T>(code, message);
    }

    public static <T> Result<T> of(ResultCode resultCode) {
        return new Result<T>(resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> Result<T> of(ResultCode resultCode, T data) {
        return new Result<T>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static <T> Result<T> of(ResultCode resultCode, T data, Map<String, Object> ext) {
        return new Result<T>(resultCode.getCode(), resultCode.getMessage(), data, ext);
    }

    /**
     * @param response
     * @param resultCode
     * @throws Exception
     */
    public static void of(HttpServletResponse response, ResultCode resultCode) throws Exception {
        of(response, resultCode, null);
    }

    /**
     * @param response
     * @param resultCode
     * @param data
     * @throws Exception
     */
    public static <T> void of(HttpServletResponse response, ResultCode resultCode, T data) throws Exception {
        Result result = new Result<T>(resultCode.getCode(), resultCode.getMessage(), data);
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(result);
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    public static <T> Result<T> successOf() {
        return of(DefaultResultCode.SUCCESS);
    }

    public static <T> Result<T> invalidParamOf() {
        return of(DefaultResultCode.INVALID_PARAMETER);
    }

    public static <T> Result<T> invalidParamOf(T data) {
        return of(DefaultResultCode.INVALID_PARAMETER, data);
    }

    public static <T> Result<T> invalidUserOf() {
        return of(DefaultResultCode.INVALID_USER);
    }

    public static <T> Result<T> invalidTokenAuthorizationOf() {
        return of(DefaultResultCode.TOKEN_AUTHORIZATION_FAILED);
    }

    public static <T> Result<T> invalidTokenAuthorizationOf(T data) {
        return of(DefaultResultCode.TOKEN_AUTHORIZATION_FAILED, data);
    }


    public static <T> Result<T> successOf(T data) {
        return of(DefaultResultCode.SUCCESS, data);
    }

    public static <T> Result<T> successOf(T data, Map<String, Object> ext) {
        return of(DefaultResultCode.SUCCESS, data, ext);
    }

    public static <T> Result<T> errorOf() {
        return of(DefaultResultCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> Result<T> errorOf(String message) {
        return of(DefaultResultCode.INTERNAL_SERVER_ERROR.getCode(), message);
    }


    public static <T> Result errorOf(T data) {
        return of(DefaultResultCode.INTERNAL_SERVER_ERROR, data);
    }

    /**
     * 抛出异常
     * @param exception 异常信息
     * @return
     */
    public static Result errorOf(Exception exception) {
        return of(DefaultResultCode.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    public static <T, R> Result<T> errorOf(Result<R> result) {
        return new Result<T>(result.getCode(), result.getMessage());
    }

}