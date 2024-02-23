package com.wu.framework.inner.lazy.database.expand.database.persistence.factory;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

import javax.sql.DataSource;

/**
 * describe : LazyLambdaStream 工厂
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/25 15:31
 */
public final class LazyLambdaStreamFactory {


    /**
     * 创建 LazyLambdaStream 对象
     *
     * @param lazyDataSourceProperties 配置信息
     * @return LazyLambdaStream
     */
    public static LazyLambdaStream createLazyLambdaStream(LazyDataSourceProperties lazyDataSourceProperties, LazyOperationConfig lazyOperationConfig) {
        LazySqlOperation lazySqlOperation = LazyOperationProxyFactory.createLazyOperation(lazyDataSourceProperties, lazyOperationConfig);
        return new LazyLambdaStream(lazySqlOperation);
    }

    /**
     * 创建 LazyLambdaStream 对象
     *
     * @param lazyDataSourceProperties 配置信息
     * @return LazyLambdaStream
     */
    public static LazyLambdaStream createLazyLambdaStream(LazyDataSourceProperties lazyDataSourceProperties) {
        return createLazyLambdaStream(lazyDataSourceProperties, null);
    }

    /**
     * 创建 LazyLambdaStream 对象
     *
     * @param url      数据库地址
     * @param username 用户名称
     * @param password 用户密码
     * @return 自动填充数据对象
     * @return LazyLambdaStream
     */
    public static LazyLambdaStream createLazyLambdaStream(String url, String username, String password) {
        LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(url);
        lazyDataSourceProperties.setUsername(username);
        lazyDataSourceProperties.setPassword(password);
        return createLazyLambdaStream(lazyDataSourceProperties);
    }

    /**
     * 创建 LazyLambdaStream 对象
     *
     * @param url      数据库地址
     * @param username 用户名称
     * @param password 用户密码
     * @param type     数据源类型
     * @param alias    数据源别名
     * @return LazyLambdaStream
     */
    public static LazyLambdaStream createLazyLambdaStream(String url, String username, String password, Class<? extends DataSource> type, String alias) {
        LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(url);
        lazyDataSourceProperties.setUsername(username);
        lazyDataSourceProperties.setPassword(password);
        lazyDataSourceProperties.setType(type);
        lazyDataSourceProperties.setAlias(alias);
        return createLazyLambdaStream(lazyDataSourceProperties);
    }

    /**
     * 创建 LazyLambdaStream 对象
     *
     * @param url      数据库地址
     * @param username 用户名称
     * @param password 用户密码
     * @param type     数据源类型
     * @return LazyLambdaStream
     */
    public static LazyLambdaStream createLazyLambdaStream(String url, String username, String password, Class<? extends DataSource> type) {
        return createLazyLambdaStream(url, username, password, type,"default");
    }

    /**
     * 创建 LazyLambdaStream 对象
     *
     * @param host     数据库IP
     * @param port     数据库端口
     * @param username 用户名称
     * @param password 用户密码
     * @return LazyLambdaStream
     */
    public static LazyLambdaStream createLazyLambdaStream(String host, int port, String schema, String username, String password) {
        return createLazyLambdaStream(host, port, schema, username, password, null);
    }

    /**
     * 创建 LazyLambdaStream 对象
     *
     * @param host     数据库IP
     * @param port     数据库端口
     * @param username 用户名称
     * @param password 用户密码
     * @param type     数据源类型
     * @return 自动填充数据对象
     * @return
     */
    public static LazyLambdaStream createLazyLambdaStream(String host, int port, String schema, String username, String password, Class<? extends DataSource> type) {
        String urlFormat = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai&databaseTerm=SCHEMA";
        String url = String.format(urlFormat, host, port, schema);
        return createLazyLambdaStream(url, username, password, type);
    }
}
