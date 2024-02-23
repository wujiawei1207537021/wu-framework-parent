package com.wu.framework.inner.lazy.database.datasource.proxy.factory;

import com.wu.framework.inner.lazy.database.datasource.proxy.LazyProxyDataSource;

public class LazyProxyDataSourceFactory {


    /**
     * 创建实例
     *
     * @return LazyDataSource
     */
    public LazyProxyDataSource createLazyDataSource() {
        // 注册连接驱动
        // 根据配置信息创建datasource
        // 创建代理对象
        return new LazyProxyDataSource(null);
    }
}
