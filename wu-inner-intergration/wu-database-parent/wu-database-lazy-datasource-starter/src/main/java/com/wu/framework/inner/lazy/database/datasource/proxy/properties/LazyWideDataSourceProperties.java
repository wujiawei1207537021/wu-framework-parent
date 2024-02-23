package com.wu.framework.inner.lazy.database.datasource.proxy.properties;

import lombok.Data;

import java.sql.Driver;

/**
 * 数据源属性
 */
@Data
public class LazyWideDataSourceProperties {
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * url
     */
    private String url;
    /**
     * 驱动
     */
    private Class<? extends Driver> driverClass;
}
