package com.wu.framework.inner.lazy.database.smart.database.factory;

import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyOperationProxyFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperationAutoStuffed;
import com.wu.framework.inner.lazy.database.smart.database.persistence.LazyOperationSmartAutoStuffed;

/**
 * 自动填充数据  工厂
 */
public final class AutoStuffedFactory {


    /**
     * 创建 自动填充数据对象
     *
     * @param lazyDataSourceProperties 数据库链接配置
     * @return
     */
    public static SmartLazyOperationAutoStuffed createAutoStuffed(LazyDataSourceProperties lazyDataSourceProperties) {
        LazySqlOperation lazySqlOperation = LazyOperationProxyFactory.createLazyOperation(lazyDataSourceProperties);
        LazyLambdaStream lazyLambdaStream = new LazyLambdaStream(lazySqlOperation);
        LazyOperationSmartAutoStuffed lazyOperationAutoStuffed = new LazyOperationSmartAutoStuffed(lazyLambdaStream);
        return lazyOperationAutoStuffed;
    }

    /**
     * 创建 自动填充数据对象
     *
     * @param url      数据库地址
     * @param username 用户名称
     * @param password 用户密码
     * @return 自动填充数据对象
     */
    public static SmartLazyOperationAutoStuffed createAutoStuffed(String url, String username, String password) {
        LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(url);
        lazyDataSourceProperties.setUsername(username);
        lazyDataSourceProperties.setPassword(password);
        return createAutoStuffed(lazyDataSourceProperties);
    }

    /**
     * 创建 自动填充数据对象
     *
     * @param host     数据库IP
     * @param port     数据库端口
     * @param username 用户名称
     * @param password 用户密码
     * @return 自动填充数据对象
     * @return
     */
    public static SmartLazyOperationAutoStuffed createAutoStuffed(String host, int port, String schema, String username, String password) {

        String urlFormat = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
        String url = String.format(urlFormat, host, port, schema);

        return createAutoStuffed(url, username, password);
    }
}
