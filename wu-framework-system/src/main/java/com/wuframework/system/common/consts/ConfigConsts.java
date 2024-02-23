package com.wuframework.system.common.consts;

/**
 * 静态变量配置
 *
 * @author maohuanhuan
 */
@Deprecated
public class ConfigConsts {
    /**
     * token的名称
     */
    public static String TOKEN_NAME = "access_token";
    /**
     * 刷新校验的token字段
     */
    public static String REFRESH_TOKEN = "refresh_token";
    /**
     * 加密盐
     */
    public static String SECRET_KEY = "yuntsoft!@#";
    /**
     * 过期时间2个小时
     */
    public static Long EXPIRE_TIME = Long.parseLong("7200000");
    // 2 * 60 * 60 * 1000 7200000
    /**
     * 刷新的token过期时间为7天
     * 7 * 24 * 60 * 60 * 1000;
     */
    public static Long REFRESH_TIME = Long.parseLong("604800000");

    /**
     * 用户账号开通成功短信模板CODE
     */
    public static String SMS_USER_CODE = "SMS_175543791";

    /**
     * 采购代办事项短信模板CODE
     */
    public static String SMS_PURCHASING_CODE = "SMS_175543791";
}
