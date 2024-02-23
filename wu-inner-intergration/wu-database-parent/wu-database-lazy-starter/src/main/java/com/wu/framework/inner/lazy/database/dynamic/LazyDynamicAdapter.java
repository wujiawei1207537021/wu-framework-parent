package com.wu.framework.inner.lazy.database.dynamic;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import com.wu.framework.inner.lazy.stereotype.LazyDS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * describe : lazy 动态数据源适配器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/29 20:28
 */
@Slf4j
public class LazyDynamicAdapter extends AbstractDynamicAdapter {

    public LazyDynamicAdapter(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    /**
     * describe 确定数据源
     *
     * @return
     * @author Jia wei Wu
     * @date 2021/7/4 6:19 下午
     **/
    @Override
    public DataSource determineDataSource() {
        final String primary = getPrimary();
        final Map<String, DataSource> dataSourceMap = getDataSourceMap();
        LazyDynamicEndpoint ds = DynamicLazyDSContextHolder.peek();
        if (ObjectUtils.isEmpty(ds)) {
            log.warn("当前方法没有设置默认值,将使用默认数据源master:{} ", primary);
        } else if (dataSourceMap.containsKey(ds.getName())) {
            return dataSourceMap.get(ds.getName());
        } else {
            log.warn("无法使用数据源{} 将使用默认数据源master:{} ", ds, primary);
        }
        return dataSourceMap.get(primary);
    }

    /**
     * 数据源载入
     *
     * @param sourceProperties 数据源参数
     */
    @Override
    public void loading(LazyDataSourceProperties sourceProperties) {
        MysqlDataSource build = DataSourceBuilder.create().type(MysqlDataSource.class).build();
        build.setUrl(sourceProperties.getUrl());
        build.setUser(sourceProperties.getUsername());
        build.setPassword(sourceProperties.getPassword());
        getDataSourceMap().put(sourceProperties.getAlias(), build);
    }
}
