package com.wu.framework.inner.lazy.database.smart.database.factory;

import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.expand.database.persistence.config.ExportDataConfiguration;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.smart.database.Perfect;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperationAutoStuffed;
import com.wu.framework.inner.lazy.database.smart.database.persistence.LazyOperationSmartAutoStuffed;
import com.wu.framework.inner.lazy.database.smart.database.persistence.LazySmartLazyOperation;
import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;

/**
 * description 聪明懒人的操作工厂
 *
 * @author 吴佳伟
 * @date 2023/05/17 19:11
 */
public class LazySmartLazyOperationFactory {

    /**
     * 创建 聪明懒人的操作工厂
     *
     * @param lazyDataSourceProperties
     * @return
     */
    public static LazySmartLazyOperation createLazySmartLazyOperation(LazyDataSourceProperties lazyDataSourceProperties) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(lazyDataSourceProperties);
        SmartLazyOperationAutoStuffed autoStuffedLazyOperation = new LazyOperationSmartAutoStuffed(lazyLambdaStream);
        Perfect perfect = new
                PerfectLazyOperation(new ExportDataConfiguration(), lazyLambdaStream);
        LazySmartLazyOperation lazyOperationAutoStuffed = new LazySmartLazyOperation(autoStuffedLazyOperation, perfect, lazyLambdaStream);
        return lazyOperationAutoStuffed;
    }

    /**
     * 创建 聪明懒人的操作工厂
     *
     * @param url      数据库地址
     * @param username 用户名称
     * @param password 用户密码
     * @return 自动填充数据对象
     */
    public static LazySmartLazyOperation createLazySmartLazyOperation(String url, String username, String password) {
        LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(url);
        lazyDataSourceProperties.setUsername(username);
        lazyDataSourceProperties.setPassword(password);
        return createLazySmartLazyOperation(lazyDataSourceProperties);
    }

    /**
     * 创建 聪明懒人的操作工厂
     *
     * @param host     数据库IP
     * @param port     数据库端口
     * @param username 用户名称
     * @param password 用户密码
     * @return 自动填充数据对象
     * @return
     */
    public static LazySmartLazyOperation createLazySmartLazyOperation(String host, int port, String schema, String username, String password) {

        String urlFormat = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
        String url = String.format(urlFormat, host, port, schema);

        return createLazySmartLazyOperation(url, username, password);
    }
}
