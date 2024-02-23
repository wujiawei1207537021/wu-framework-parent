package com.wu.framework.inner.lazy.source.adapter;

import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.source.advanced.SourceAdvanced;
import com.wu.framework.inner.lazy.source.clickhouse.ClickHouseSourceAdvancedTarget;
import com.wu.framework.inner.lazy.source.h2.H2SourceAdvancedTarget;
import com.wu.framework.inner.lazy.source.mysql.MysqlSourceAdvancedTarget;
import com.wu.framework.inner.lazy.source.postgresql.PostgreSQLSourceAdvancedTarget;
import com.wu.framework.inner.lazy.source.sqlite.SqliteSourceAdvancedTarget;
import com.wu.framework.inner.lazy.toolkit.DynamicLazyDataSourceTypeHolder;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * 数据源工厂处理器
 *
 * @see DynamicLazyDataSourceTypeHolder
 * @see MysqlSourceAdvancedTarget
 * @see ClickHouseSourceAdvancedTarget
 * @see PostgreSQLSourceAdvancedTarget
 * @see H2SourceAdvancedTarget
 * @see SqliteSourceAdvancedTarget
 */
public class SourceFactory {

    /**
     * 默认数据源，项目加载后会自动确定当前数据源
     */
    public static LazyDataSourceType defaultLazyDataSourceType = null;

    private static final List<SourceAdvanced> sourceAdvancedList = List.of(new MysqlSourceAdvancedTarget(), new ClickHouseSourceAdvancedTarget(), new PostgreSQLSourceAdvancedTarget(), new H2SourceAdvancedTarget(), new SqliteSourceAdvancedTarget());


    /**
     * 获取url
     *
     * @param host getHost
     * @param port 端口
     */
    public static String getUrl(LazyDataSourceType lazyDataSourceType, String host, int port) {
        for (SourceAdvanced sourceAdvanced : sourceAdvancedList) {
            if (sourceAdvanced.support(lazyDataSourceType)) {
                return sourceAdvanced.getUrl(host, port);
            }
        }
        return null;
    }

    /**
     * 通过url获取 url中的 host 有可能是空
     *
     * @param url url
     */
    public static String getHost(String url) {
        LazyDataSourceType lazyDataSourceType = getLazyDataSourceType(url);
        for (SourceAdvanced sourceAdvanced : sourceAdvancedList) {
            if (sourceAdvanced.support(lazyDataSourceType)) {
                return sourceAdvanced.getHost(url);
            }
        }
        return null;
    }

    /**
     * 通过url获取 url中的 port 有可能是空
     *
     * @param url url
     */
    public static int getPort(String url) {
        LazyDataSourceType lazyDataSourceType = getLazyDataSourceType(url);
        for (SourceAdvanced sourceAdvanced : sourceAdvancedList) {
            if (sourceAdvanced.support(lazyDataSourceType)) {
                return sourceAdvanced.getPort(url);
            }
        }
        return 0;
    }

    /**
     * 通过url获取 url中的schema
     *
     * @param url url
     * @return url中的schema
     */
    public static String getUrlSchema(String url) {
        LazyDataSourceType dataSourceType = getLazyDataSourceType(url);
        for (SourceAdvanced sourceAdvanced : sourceAdvancedList) {
            if (sourceAdvanced.support(dataSourceType)) {
                return sourceAdvanced.getUrlSchema(url);
            }
        }
        throw new IllegalArgumentException("无法解析url中的schema:[" + url + "]");
    }

    /**
     * 通过url 获取 数据源类型
     *
     * @param url 数据库连接url
     * @return 数据源类型
     */
    public static LazyDataSourceType getLazyDataSourceType(String url) {
        for (SourceAdvanced sourceAdvanced : sourceAdvancedList) {
            LazyDataSourceType dataSourceType = sourceAdvanced.getLazyDataSourceType(url);
            if (null != dataSourceType) {
                return dataSourceType;
            }
        }
        throw new IllegalArgumentException("无法解析url为指定类型的数据源类型:[" + url + "]");
    }

    /**
     * 获取 information_schema
     *
     * @param url 连接url
     * @return 返回 information_schema 的url连接地址
     */
    public static String getDefaultInformationSchemaUrl(String url) {
        LazyDataSourceType lazyDataSourceType = getLazyDataSourceType(url);
        for (SourceAdvanced sourceAdvanced : sourceAdvancedList) {
            if (sourceAdvanced.support(lazyDataSourceType)) {
                return sourceAdvanced.getDefaultInformationSchemaUrl(url);
            }
        }
        throw new IllegalArgumentException("无法从url中解析出information_schema:[" + url + "]");
    }


//    #########################################################################################################################################################################################
//    Java class 解析成表结构 开始
//    #########################################################################################################################################################################################

    /**
     * 获取表结构
     *
     * @param lazyDataSourceType 数据源类型
     * @param clazz              需要解析的class
     * @return 表结构
     */
    public static LazyTableEndpoint analyzeLazyTableFromClass(LazyDataSourceType lazyDataSourceType, Class<?> clazz) {
        for (SourceAdvanced sourceAdvanced : sourceAdvancedList) {
            if (sourceAdvanced.support(lazyDataSourceType)) {
                return sourceAdvanced.analyzeLazyTableFromClass(clazz);
            }
        }
        throw new IllegalArgumentException("不支持数据源类型:" + lazyDataSourceType);
    }

    /**
     * 根据当前上下文获取数据源类型而后解析出表结构
     *
     * @param clazz 需要解析的class
     * @return 表结构
     */
    public static LazyTableEndpoint defaultAnalyzeLazyTableFromClass(Class<?> clazz) {
        // 上下文获取 数据源类型
        LazyDataSourceType currentLazyDataSourceType = DynamicLazyDataSourceTypeHolder.peek() == null ? defaultLazyDataSourceType : DynamicLazyDataSourceTypeHolder.peek();
        if (currentLazyDataSourceType == null) {
            throw new IllegalArgumentException("无法解析当前数据源类型");
        }
        return analyzeLazyTableFromClass(currentLazyDataSourceType, clazz);

    }

    /**
     * 获取当前数据类型缓存的表结构
     *
     * @param lazyDataSourceType 数据源类型
     * @return 当前数据类型缓存的表结构
     */
    public static ConcurrentMap<Class<?>, LazyTableEndpoint> getTableCache(LazyDataSourceType lazyDataSourceType) {
        for (SourceAdvanced sourceAdvanced : sourceAdvancedList) {
            if (sourceAdvanced.support(lazyDataSourceType)) {
                return sourceAdvanced.getTableCache();
            }
        }
        throw new IllegalArgumentException("不支持数据源类型:" + lazyDataSourceType);
    }

    /**
     * 获取当前数据类型缓存的表结构
     *
     * @return 当前数据类型缓存的表结构
     */
    public static ConcurrentMap<Class<?>, LazyTableEndpoint> getTableCache() {
        // 上下文获取 数据源类型
        LazyDataSourceType currentLazyDataSourceType = DynamicLazyDataSourceTypeHolder.peek() == null ? defaultLazyDataSourceType : DynamicLazyDataSourceTypeHolder.peek();
        if (currentLazyDataSourceType == null) {
            throw new IllegalArgumentException("无法解析当前数据源类型");
        }
        return getTableCache(currentLazyDataSourceType);
    }

//    #########################################################################################################################################################################################
//    Java class 解析成表结构 结束
//    #########################################################################################################################################################################################


}
