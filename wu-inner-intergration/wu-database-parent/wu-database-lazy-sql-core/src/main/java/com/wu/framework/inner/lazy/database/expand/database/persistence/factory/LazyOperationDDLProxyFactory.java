package com.wu.framework.inner.lazy.database.expand.database.persistence.factory;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.dynamic.LazyDynamicAdapter;
import com.wu.framework.inner.lazy.database.dynamic.factory.LazyDynamicAdapterFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDDLOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.*;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.SqlInterceptorAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyDDLOperationProxy;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * LazyDDLOperationProxy 代理工厂
 */
public final class LazyOperationDDLProxyFactory {


    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties 数据库链接配置
     * @return
     */
    public static LazyDDLOperationProxy createDefaultLazyDDLOperationProxy(LazyDataSourceProperties sourceProperties) {
        return createDefaultLazyDDLOperationProxy(sourceProperties, null);
    }

    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties    数据库链接配置
     * @param lazyOperationConfig 操作配置
     * @return
     */
    public static LazyDDLOperationProxy createDefaultLazyDDLOperationProxy(LazyDataSourceProperties sourceProperties,
                                                                           LazyOperationConfig lazyOperationConfig) {
        SqlInterceptorAdapter defaultSqlInterceptorAdapter = SqlInterceptorAdapterFactory.createDefaultSqlInterceptorAdapter();
        LazyOperationParameter lazyOperationParameter =
                new LazyOperationParameter()
                        .setLazyOperationConfig(lazyOperationConfig)
                        .setSqlInterceptorAdapter(defaultSqlInterceptorAdapter);
        List<LazyDDLOperationMethod> lazyOperationMethods = Arrays.asList(
                new LazyOperationMethodCreateTable(lazyOperationParameter),
//                new LazyOperationMethodExecute(),
//                new LazyOperationMethodExecuteOne(),
//                new LazyOperationMethodExecuteSQL(lazyOperationConfig),
//                new LazyOperationMethodExecuteSQLForBean(lazyOperationConfig),
                new LazyOperationMethodPerfect(lazyOperationParameter),
                new LazyOperationMethodUpdateTable(lazyOperationParameter),
                new LazyOperationMethodStringScriptRunner(lazyOperationParameter),
                new LazyOperationMethodScriptRunner(lazyOperationParameter),
                new LazyOperationMethodStringScriptRunner(lazyOperationParameter)
        );
        return createLazyDDLOperationProxy(null, sourceProperties, lazyOperationMethods);
    }


    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties     数据库链接配置
     * @param lazyOperationMethods 操作的方法
     * @return
     */
    public static LazyDDLOperationProxy createLazyDDLOperationProxy(LazyDataSourceProperties sourceProperties,
                                                                    List<LazyDDLOperationMethod> lazyOperationMethods) {
        return createLazyDDLOperationProxy(null, sourceProperties, lazyOperationMethods);
    }

    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties     数据库链接配置
     * @param lazyOperationMethods 操作的方法
     * @param dataSourceMap        上下文 数据源
     * @return
     */
    public static LazyDDLOperationProxy createLazyDDLOperationProxy(Map<String, DataSource> dataSourceMap,
                                                                    LazyDataSourceProperties sourceProperties,
                                                                    List<LazyDDLOperationMethod> lazyOperationMethods
    ) {
        LazyDynamicAdapter lazyDynamicAdapter = LazyDynamicAdapterFactory.createLazyDynamicAdapter(dataSourceMap, sourceProperties);
        LazyDDLOperationProxy lazyOperationProxy = new LazyDDLOperationProxy(lazyOperationMethods, lazyDynamicAdapter);
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
    public static LazyBaseDDLOperation createLazyDDLOperation(LazyDataSourceProperties lazyDataSourceProperties) {
        LazyDDLOperationProxy defaultLazyOperationProxy = createDefaultLazyDDLOperationProxy(lazyDataSourceProperties);
        return createLazyDDLOperation(defaultLazyOperationProxy);
    }

    /**
     * 创建对象操作
     *
     * @param lazyDDLOperationProxy 操作对象代理对象
     * @return
     */
    public static LazyBaseDDLOperation createLazyDDLOperation(LazyDDLOperationProxy lazyDDLOperationProxy) {
        return (LazyBaseDDLOperation) Proxy.newProxyInstance(LazyBaseDDLOperation.class.getClassLoader(), new Class[]{LazyBaseDDLOperation.class}, lazyDDLOperationProxy);
    }

    /**
     * 创建 LazySqlOperation 对象
     *
     * @param url      数据库地址
     * @param username 用户名称
     * @param password 用户密码
     * @return 自动填充数据对象
     * @return
     */
    public static LazyBaseDDLOperation createLazyDDLOperation(String url, String username, String password) {
        LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(url);
        lazyDataSourceProperties.setUsername(username);
        lazyDataSourceProperties.setPassword(password);
        return createLazyDDLOperation(lazyDataSourceProperties);
    }

    /**
     * 创建 LazySqlOperation 对象
     *
     * @param host     数据库IP
     * @param port     数据库端口
     * @param username 用户名称
     * @param password 用户密码
     * @return 自动填充数据对象
     * @return
     */
    public static LazyBaseDDLOperation createLazyDDLOperation(String host, int port, String schema, String username, String password) {
        String urlFormat = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
        String url = String.format(urlFormat, host, port, schema);

        return createLazyDDLOperation(url, username, password);
    }


}
