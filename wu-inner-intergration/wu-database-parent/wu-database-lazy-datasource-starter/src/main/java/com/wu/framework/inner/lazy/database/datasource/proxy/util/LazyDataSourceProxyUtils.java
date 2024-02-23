package com.wu.framework.inner.lazy.database.datasource.proxy.util;

import com.wu.framework.inner.lazy.database.datasource.proxy.LazyProxyDataSource;

import javax.sql.DataSource;

/**
 * description 代理数据源对象
 *
 * @author 吴佳伟
 * @date 2023/06/15 21:12
 */
public class LazyDataSourceProxyUtils {

    /**
     * 创建代理lazy 数据源
     *
     * @param dataSource
     * @return
     */
    public static LazyProxyDataSource proxy(DataSource dataSource) {
        if (!(dataSource instanceof LazyProxyDataSource)) {
            dataSource = new LazyProxyDataSource(dataSource);
        }
        return (LazyProxyDataSource) dataSource;
    }

}
