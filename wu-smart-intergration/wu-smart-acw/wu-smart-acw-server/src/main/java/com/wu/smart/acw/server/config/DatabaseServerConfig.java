package com.wu.smart.acw.server.config;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.persistence.util.DataSourceUrlParsingUtil;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.server.application.AcwInstanceApplication;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * describe : 服务器信息配置并初始化多数据源
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/29 19:18
 */
@Configuration
public class DatabaseServerConfig implements InitializingBean {

    private final AcwInstanceApplication acwInstanceApplication;

    private final LazyDataSourceProperties lazyDataSourceProperties;

    public DatabaseServerConfig(AcwInstanceApplication acwInstanceApplication, LazyDataSourceProperties lazyDataSourceProperties) {
        this.acwInstanceApplication = acwInstanceApplication;
        this.lazyDataSourceProperties = lazyDataSourceProperties;
    }

    /**
     * 初始化当前连接数据源
     */
    public void initializeCurrentDataSource() {
        String url = lazyDataSourceProperties.getUrl();
        LazyDataSourceType lazyDataSourceType = SourceFactory.getLazyDataSourceType(url);
        String host = SourceFactory.getHost(url);
        int port = SourceFactory.getPort(url);
        AcwInstanceUo acwInstanceUo = new AcwInstanceUo();
        acwInstanceUo.setLazyDataSourceType(lazyDataSourceType);
        acwInstanceUo.setId(NormalUsedString.DEFAULT);
        acwInstanceUo.setCreateTime(LocalDateTime.now());
        acwInstanceUo.setDriverClassName(lazyDataSourceProperties.getDriverClassName());
        acwInstanceUo.setHost(host);
        acwInstanceUo.setInitializeToLocal(false);
        acwInstanceUo.setInstanceName("默认数据库");
        acwInstanceUo.setSort(-1);
        acwInstanceUo.setIsDeleted(false);
        acwInstanceUo.setPassword(lazyDataSourceProperties.getPassword());
        acwInstanceUo.setPort(port);
        acwInstanceUo.setUpdateTime(LocalDateTime.now());
        acwInstanceUo.setUrl(url);
        acwInstanceUo.setUsername(lazyDataSourceProperties.getUsername());
        acwInstanceApplication.save(acwInstanceUo);
    }

    @Override
    public void afterPropertiesSet() {
        try {
            initializeCurrentDataSource();
            acwInstanceApplication.loading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
