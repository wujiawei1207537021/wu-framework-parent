package com.wu.framework.inner.lazy.database.expand.database.persistence.factory;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.dynamic.LazyDynamicAdapter;
import com.wu.framework.inner.lazy.database.dynamic.factory.LazyDynamicAdapterFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.CureAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.CureAdapterFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.*;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.dml.*;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql.*;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.SqlInterceptorAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyOperationProxy;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.adapter.LazyTranslationAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.factory.LazyTranslationAdapterFactory;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * LazyOperationProxy 代理工厂
 */
public final class LazyOperationProxyFactory {


    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties 数据库链接配置
     * @return
     */
    public static LazyOperationProxy createDefaultLazyOperationProxy(LazyDataSourceProperties sourceProperties) {
        return createDefaultLazyOperationProxy(sourceProperties, null);
    }

    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties    数据库链接配置
     * @param lazyOperationConfig 操作配置
     * @return LazyOperationProxy
     */
    public static LazyOperationProxy createDefaultLazyOperationProxy(LazyDataSourceProperties sourceProperties,
                                                                     LazyOperationConfig lazyOperationConfig) {
        SqlInterceptorAdapter defaultSqlInterceptorAdapter = SqlInterceptorAdapterFactory.createDefaultSqlInterceptorAdapter();
        LazyOperationParameter lazyOperationParameter =
                new LazyOperationParameter()
                        .setLazyOperationConfig(lazyOperationConfig)
                        .setSqlInterceptorAdapter(defaultSqlInterceptorAdapter);
        List<LazyOperationMethod> lazyOperationMethods = Arrays.asList(
                new LazyOperationMethodCreateTable(lazyOperationParameter),
                new LazyOperationMethodExecute(lazyOperationParameter),
                new LazyOperationMethodExecutePage(lazyOperationParameter),
                new LazyOperationMethodExecuteOne(lazyOperationParameter),
                new LazyOperationMethodExecuteSQL(lazyOperationParameter),
                new LazyOperationMethodExecuteSQLForBean(lazyOperationParameter),
                new LazyOperationMethodInsert(lazyOperationParameter),
                new LazyOperationMethodSaveOrUpdate(lazyOperationParameter),
                new LazyOperationMethodPage(lazyOperationParameter),
                new LazyOperationMethodPerfect(lazyOperationParameter),
                new LazyOperationMethodUpdateTable(lazyOperationParameter),
                new LazyOperationMethodUpsert(lazyOperationParameter),
                new LazyOperationMethodUpsertRemoveNull(lazyOperationParameter),
                new LazyOperationMethodScriptRunner(lazyOperationParameter),
                new LazyOperationMethodStringScriptRunner(lazyOperationParameter)

        );
        return createLazyOperationProxy(null, sourceProperties, lazyOperationMethods);
    }


    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties     数据库链接配置
     * @param lazyOperationMethods 操作的方法
     * @return
     */
    public static LazyOperationProxy createLazyOperationProxy(LazyDataSourceProperties sourceProperties,
                                                              List<LazyOperationMethod> lazyOperationMethods) {
        return createLazyOperationProxy(null, sourceProperties, lazyOperationMethods);
    }

    /**
     * 创建默认的操作代理对象
     *
     * @param sourceProperties     数据库链接配置
     * @param lazyOperationMethods 操作的方法
     * @param dataSourceMap        上下文 数据源
     * @return
     */
    public static LazyOperationProxy createLazyOperationProxy(Map<String, DataSource> dataSourceMap,
                                                              LazyDataSourceProperties sourceProperties,
                                                              List<LazyOperationMethod> lazyOperationMethods
    ) {
        LazyDynamicAdapter lazyDynamicAdapter = LazyDynamicAdapterFactory.createLazyDynamicAdapter(dataSourceMap, sourceProperties);

        CureAdapter cureAdapter = CureAdapterFactory.createCureAdapter(sourceProperties);
        LazyTranslationAdapter lazyTranslationAdapter = LazyTranslationAdapterFactory.createLazyTranslationAdapter();
        LazyOperationProxy lazyOperationProxy = new LazyOperationProxy(lazyOperationMethods, lazyDynamicAdapter, cureAdapter, lazyTranslationAdapter);
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
    public static LazySqlOperation createLazyOperation(LazyDataSourceProperties lazyDataSourceProperties) {
        return createLazyOperation(lazyDataSourceProperties, null);
    }

    /**
     * 创建对象操作
     *
     * @param lazyDataSourceProperties 数据库链接配置
     * @return
     */
    public static LazySqlOperation createLazyOperation(LazyDataSourceProperties lazyDataSourceProperties, LazyOperationConfig lazyOperationConfig) {
        LazyOperationProxy defaultLazyOperationProxy = createDefaultLazyOperationProxy(lazyDataSourceProperties, lazyOperationConfig);
        return createLazyOperation(defaultLazyOperationProxy);
    }

    /**
     * 创建对象操作
     *
     * @param lazyOperationProxy 操作对象代理对象
     * @return
     */
    public static LazySqlOperation createLazyOperation(LazyOperationProxy lazyOperationProxy) {
        return (LazySqlOperation) Proxy.newProxyInstance(LazySqlOperation.class.getClassLoader(), new Class[]{LazySqlOperation.class}, lazyOperationProxy);
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
    public static LazySqlOperation createLazyOperation(String url, String username, String password) {
        LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(url);
        lazyDataSourceProperties.setUsername(username);
        lazyDataSourceProperties.setPassword(password);
        return createLazyOperation(lazyDataSourceProperties);
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
    public static LazySqlOperation createLazyOperation(String host, int port, String schema, String username, String password) {
        String urlFormat = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
        String url = String.format(urlFormat, host, port, schema);

        return createLazyOperation(url, username, password);
    }


}
