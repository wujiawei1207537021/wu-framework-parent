package com.wu.framework.inner.lazy.database.dynamic.factory;

import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.datasource.proxy.LazyProxyDataSource;
import com.wu.framework.inner.lazy.database.datasource.proxy.util.LazyDataSourceProxyUtils;
import com.wu.framework.inner.lazy.database.dynamic.LazyDynamicAdapter;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源适配器工厂
 */
public final class LazyDynamicAdapterFactory {

    /**
     * 创建 动态数据源适配器
     *
     * @param sourceProperties 数据库连接地址
     * @return LazyDynamicAdapter
     */
    public static LazyDynamicAdapter createLazyDynamicAdapter(LazyDataSourceProperties sourceProperties) {
        return createLazyDynamicAdapter(null, sourceProperties);
    }

    /**
     * 动态数据源适配器
     *
     * @param dataSourceMap    上下文数据源
     * @param sourceProperties 数据库连接地址
     * @return LazyDynamicAdapter
     */
    public static LazyDynamicAdapter createLazyDynamicAdapter(Map<String, DataSource> dataSourceMap, LazyDataSourceProperties sourceProperties) {
        Map<String, LazyProxyDataSource> lazyProxyDataSourceMap = new ConcurrentHashMap<>();
        if (!ObjectUtils.isEmpty(dataSourceMap)) {
            dataSourceMap.forEach((key, dataSource) -> lazyProxyDataSourceMap.put(key, LazyDataSourceProxyUtils.proxy(dataSource)));
        }
        LazyDynamicAdapter lazyDynamicAdapter = new LazyDynamicAdapter(lazyProxyDataSourceMap);
        if (null != sourceProperties) {
            lazyDynamicAdapter.loading(sourceProperties);
        }
        return lazyDynamicAdapter;
    }

}
