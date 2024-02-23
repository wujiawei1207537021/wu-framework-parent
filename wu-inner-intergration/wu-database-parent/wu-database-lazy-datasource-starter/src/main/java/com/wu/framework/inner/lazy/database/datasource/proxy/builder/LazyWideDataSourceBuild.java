package com.wu.framework.inner.lazy.database.datasource.proxy.builder;

import com.wu.framework.inner.lazy.database.datasource.proxy.LazyWideDataSource;

import java.sql.Driver;

/**
 * 构建数据源
 */
public class LazyWideDataSourceBuild {

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
    private String driverName;

    /**
     * 连接地址
     *
     * @param url 连接地址
     * @return 构建数据源
     */
    private LazyWideDataSourceBuild url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 驱动
     *
     * @param driverName 驱动名称
     * @return 构建数据源
     */
    private LazyWideDataSourceBuild driverName(String driverName) {
        this.driverName = driverName;
        return this;
    }

    /**
     * 账号
     *
     * @param username 账号
     * @return 构建数据源
     */
    private LazyWideDataSourceBuild username(String username) {
        this.username = username;
        return this;
    }

    /**
     * 密码
     *
     * @param password 密码
     * @return 构建数据源
     */
    private LazyWideDataSourceBuild password(String password) {
        this.password = password;
        return this;
    }

    private LazyWideDataSource build() {
        LazyWideDataSource lazyWideDataSource = new LazyWideDataSource();

        try {
            Class<? extends Driver> driverClass = (Class<? extends Driver>) Class.forName(driverName);
            lazyWideDataSource.setUrl(url);
            lazyWideDataSource.setDriverClassName(driverName);
            lazyWideDataSource.setUsername(username);
            lazyWideDataSource.setPassword(password);
            return lazyWideDataSource;
        } catch (Exception e) {
            throw new IllegalArgumentException("无法获取到驱动:" + driverName);
        }

    }

}
