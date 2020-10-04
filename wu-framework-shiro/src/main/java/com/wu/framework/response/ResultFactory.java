package com.wu.framework.response;


import com.alibaba.fastjson.JSON;
import com.wu.framework.response.enmus.DefaultResultCode;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

public class ResultFactory {
    public static Result of(ResultCode resultCode) {
        return new Result(resultCode.getCode(), resultCode.getMessage());
    }

    public static Result of(ResultCode resultCode, Object data) {
        return new Result(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static Result of(ResultCode resultCode, Object data, Map<String, Object> ext) {
        return new Result(resultCode.getCode(), resultCode.getMessage(), data, ext);
    }

    /**
     *
     * @param response
     * @param resultCode
     * @throws Exception
     */
    public static void of(HttpServletResponse response,ResultCode resultCode) throws Exception {
        of(response,resultCode,null);
    }

    /**
     *
     * @param response
     * @param resultCode
     * @param data
     * @throws Exception
     */
    public static void of(HttpServletResponse response, ResultCode resultCode, Object data) throws Exception {
        Result result=  new Result(resultCode.getCode(), resultCode.getMessage(), data);
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(result);
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    public static Result successOf() {
        return of(DefaultResultCode.SUCCESS);
    }

    public static Result invalidParamOf() {
        return of(DefaultResultCode.INVALID_PARAMETER);
    }

    public static Result invalidParamOf(String data) {
        return of(DefaultResultCode.INVALID_PARAMETER, data);
    }

    public static Result invalidUserOf() {
        return of(DefaultResultCode.INVALID_USER);
    }

    public static Result invalidTokenAuthorizationOf() {
        return of(DefaultResultCode.TOKEN_AUTHORIZATION_FAILED);
    }

    public static Result invalidTokenAuthorizationOf(String data) {
        return of(DefaultResultCode.TOKEN_AUTHORIZATION_FAILED, data);
    }

    public static Result successOf(Object data) {
        return of(DefaultResultCode.SUCCESS, data);
    }

    public static Result errorOf() {
        return of(DefaultResultCode.INTERNAL_SERVER_ERROR);
    }

    public static Result errorOf(Object data) {
        return of(DefaultResultCode.INTERNAL_SERVER_ERROR, data);
    }

}