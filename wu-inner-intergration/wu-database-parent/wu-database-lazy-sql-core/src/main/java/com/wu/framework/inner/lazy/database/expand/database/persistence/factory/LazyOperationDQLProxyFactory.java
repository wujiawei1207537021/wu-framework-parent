package com.wu.framework.inner.lazy.database.expand.database.persistence.factory;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.dynamic.LazyDynamicAdapter;
import com.wu.framework.inner.lazy.database.dynamic.factory.LazyDynamicAdapterFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDQLOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql.*;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.SqlInterceptorAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyDQLOperationProxy;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.adapter.LazyTranslationAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.factory.LazyTranslationAdapterFactory;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * LazyOperationDQLDQLProxy 代理工厂
 */
public final class LazyOperationDQLProxyFactory {


    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties 数据库链接配置
     * @return
     */
    public static LazyDQLOperationProxy createDefaultLazyDQLOperationProxy(LazyDataSourceProperties sourceProperties) {
        return createDefaultLazyDQLOperationProxy(sourceProperties, null);
    }

    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties    数据库链接配置
     * @param lazyOperationConfig 操作配置
     * @return
     */
    public static LazyDQLOperationProxy createDefaultLazyDQLOperationProxy(LazyDataSourceProperties sourceProperties,
                                                                           LazyOperationConfig lazyOperationConfig) {
        SqlInterceptorAdapter defaultSqlInterceptorAdapter = SqlInterceptorAdapterFactory.createDefaultSqlInterceptorAdapter();
        LazyOperationParameter lazyOperationParameter =
                new LazyOperationParameter()
                        .setLazyOperationConfig(lazyOperationConfig)
                        .setSqlInterceptorAdapter(defaultSqlInterceptorAdapter);
        List<LazyDQLOperationMethod> lazyOperationMethods = Arrays.asList(
                new LazyOperationMethodExecute(lazyOperationParameter),
                new LazyOperationMethodExecutePage(lazyOperationParameter),
                new LazyOperationMethodExecuteOne(lazyOperationParameter),
                new LazyOperationMethodExecuteSQL(lazyOperationParameter),
                new LazyOperationMethodExecuteSQLForBean(lazyOperationParameter),
                new LazyOperationMethodPage(lazyOperationParameter)
        );
        return createLazyDQLOperationProxy(null, sourceProperties, lazyOperationMethods);
    }


    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties     数据库链接配置
     * @param lazyOperationMethods 操作的方法
     * @return
     */
    public static LazyDQLOperationProxy createLazyDQLOperationProxy(LazyDataSourceProperties sourceProperties,
                                                                    List<LazyDQLOperationMethod> lazyOperationMethods) {
        return createLazyDQLOperationProxy(null, sourceProperties, lazyOperationMethods);
    }

    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties     数据库链接配置
     * @param lazyOperationMethods 操作的方法
     * @param dataSourceMap        上下文 数据源
     * @return
     */
    public static LazyDQLOperationProxy createLazyDQLOperationProxy(Map<String, DataSource> dataSourceMap,
                                                                    LazyDataSourceProperties sourceProperties,
                                                                    List<LazyDQLOperationMethod> lazyOperationMethods
    ) {
        LazyDynamicAdapter lazyDynamicAdapter = LazyDynamicAdapterFactory.createLazyDynamicAdapter(dataSourceMap, sourceProperties);
        LazyTranslationAdapter lazyTranslationAdapter = LazyTranslationAdapterFactory.createLazyTranslationAdapter();
        LazyDQLOperationProxy lazyOperationProxy = new LazyDQLOperationProxy(lazyOperationMethods, lazyDynamicAdapter, lazyTranslationAdapter);
        try {
            lazyOperationProxy.afterPropertiesSet();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return lazyOperationProxy;
    }


    /**
     * 创建对象操作
     *
     * @param lazyDataSourceProperties 数据库链接配置
     * @return
     */
    public static LazyBaseDQLOperation createLazyDQLOperation(LazyDataSourceProperties lazyDataSourceProperties) {
        LazyDQLOperationProxy defaultLazyDQLOperationProxy = createDefaultLazyDQLOperationProxy(lazyDataSourceProperties);
        return createLazyDQLOperation(defaultLazyDQLOperationProxy);
    }

    /**
     * 创建对象操作
     *
     * @param lazyOperationProxy 操作对象代理对象
     * @return
     */
    public static LazyBaseDQLOperation createLazyDQLOperation(LazyDQLOperationProxy lazyOperationProxy) {
        return (LazyBaseDQLOperation) Proxy.newProxyInstance(LazyBaseDQLOperation.class.getClassLoader(), new Class[]{LazyBaseDQLOperation.class}, lazyOperationProxy);
    }

    /**
     * 创建 LazyOperationDQL 对象
     *
     * @param url      数据库地址
     * @param username 用户名称
     * @param password 用户密码
     * @return 自动填充数据对象
     * @return
     */
    public static LazyBaseDQLOperation createLazyDQLOperation(String url, String username, String password) {
        LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(url);
        lazyDataSourceProperties.setUsername(username);
        lazyDataSourceProperties.setPassword(password);
        return createLazyDQLOperation(lazyDataSourceProperties);
    }

    /**
     * 创建 LazyOperationDQL 对象
     *
     * @param host     数据库IP
     * @param port     数据库端口
     * @param username 用户名称
     * @param password 用户密码
     * @return 自动填充数据对象
     * @return
     */
    public static LazyBaseDQLOperation createLazyDQLOperation(String host, int port, String schema, String username, String password) {
        String urlFormat = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
        String url = String.format(urlFormat, host, port, schema);

        return createLazyDQLOperation(url, username, password);
    }


}
