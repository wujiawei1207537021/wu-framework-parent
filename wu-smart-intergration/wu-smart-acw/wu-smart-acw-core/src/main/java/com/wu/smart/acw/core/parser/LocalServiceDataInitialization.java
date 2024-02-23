package com.wu.smart.acw.core.parser;

import com.wu.framework.inner.lazy.config.LazyApplicationConfig;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;

/**
 * 本地服务数据初始化
 */
public class LocalServiceDataInitialization {

    private final LazyDataSourceProperties lazyDataSourceProperties;
    private final LazyApplicationConfig lazyApplicationConfig;

    public LocalServiceDataInitialization(LazyDataSourceProperties lazyDataSourceProperties, LazyApplicationConfig lazyApplicationConfig) {
        this.lazyDataSourceProperties = lazyDataSourceProperties;
        this.lazyApplicationConfig = lazyApplicationConfig;
    }
    // 初始化本地项目到acw

    public void InitializeLocalProjectToAcw() {
        AcwInstanceUo acwInstanceUo = new AcwInstanceUo();
        acwInstanceUo.setInstanceName(lazyApplicationConfig.getName());
        acwInstanceUo.setUrl(lazyDataSourceProperties.getUrl());
        acwInstanceUo.setDriverClassName(lazyDataSourceProperties.getDriverClassName());
        acwInstanceUo.setUsername(lazyDataSourceProperties.getUsername());
        acwInstanceUo.setPassword(lazyDataSourceProperties.getPassword());

    }

}
