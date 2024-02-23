package com.wu.framework.inner.lazy.database.dynamic;

import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.datasource.proxy.LazyProxyDataSource;
import com.wu.framework.inner.lazy.database.datasource.proxy.util.LazyDataSourceProxyUtils;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
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
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyDynamicAdapter extends AbstractDynamicAdapter {

    public LazyDynamicAdapter(Map<String, LazyProxyDataSource> dataSourceMap) {
        super(dataSourceMap);
    }

    /**
     * describe 确定数据源
     *
     * @return DataSource 返回数据源
     * @author Jia wei Wu
     * @date 2021/7/4 6:19 下午
     **/
    @Override
    public DataSource determineDataSource() {
        final String primary = getPrimary();
        final Map<String, LazyProxyDataSource> dataSourceMap = getDataSourceMap();
        LazyDynamicEndpoint ds = DynamicLazyDSContextHolder.peek();
        if (ObjectUtils.isEmpty(ds)) {
            if (dataSourceMap.keySet().size() > 1) {
                log.debug("当前方法没有设置默认值,将使用默认数据源master:{} ", primary);
            }

        } else if (!ObjectUtils.isEmpty(ds.getName()) && dataSourceMap.containsKey(ds.getName())) {
            return dataSourceMap.get(ds.getName());
        } else {
            log.debug("无法使用数据源{} 将使用默认数据源master:{} ", ds, primary);
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
        DataSource build = sourceProperties.initializeDataSourceBuilder().build();
        getDataSourceMap().put(sourceProperties.getAlias(), LazyDataSourceProxyUtils.proxy(build));
    }
}
