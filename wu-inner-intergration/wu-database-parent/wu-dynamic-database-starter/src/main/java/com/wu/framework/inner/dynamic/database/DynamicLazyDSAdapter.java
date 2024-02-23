package com.wu.framework.inner.dynamic.database;

import com.wu.framework.inner.dynamic.database.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.stereotype.LazyDS;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Role;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/7/4 6:20 下午
 */
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConditionalOnBean(value = DataSource.class)
public class DynamicLazyDSAdapter implements DynamicLazyDS, ApplicationListener<ContextRefreshedEvent> {
    private final Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();
    private String primary;

    /**
     * @return describe 确定数据源
     * @author Jia wei Wu
     * @date 2021/7/4 6:19 下午
     **/
    @Override
    public DataSource determineDataSource() {
        LazyDS lazyDS = DynamicLazyDSContextHolder.peek();
        if (ObjectUtils.isEmpty(lazyDS)) {
            log.warn("当前方法没有设置默认值,将使用默认数据源master:{} ", primary);
        } else if (dataSourceMap.containsKey(lazyDS.name())) {
            return dataSourceMap.get(lazyDS.name());
        } else {
            log.warn("无法使用数据源{} 将使用默认数据源master:{} ", lazyDS, primary);
        }
        return dataSourceMap.get(primary);
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, DataSource> dataSourceMap = event.getApplicationContext().getBeansOfType(DataSource.class);
        this.primary = dataSourceMap.keySet().iterator().next();
        dataSourceMap.forEach((k, v) -> {
            try {
                mybatisDataSource(k, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * description mybatis数据源具体拆分
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/2/11 上午10:37
     */
    public void mybatisDataSource(String key, DataSource dataSource) throws Exception {
        if ("com.baomidou.dynamic.datasource.DynamicRoutingDataSource".equals(dataSource.getClass().getName())) {
            Field primaryDeclaredField = dataSource.getClass().getDeclaredField("primary");
            primaryDeclaredField.setAccessible(true);
            primary = (String) primaryDeclaredField.get(dataSource);
            Field dataSourceMapDeclaredField = dataSource.getClass().getDeclaredField("dataSourceMap");
            dataSourceMapDeclaredField.setAccessible(true);
            Map<String, DataSource> mybatisDataSourceMap = (Map<String, DataSource>) dataSourceMapDeclaredField.get(dataSource);
            dataSourceMap.putAll(mybatisDataSourceMap);
        } else {
            dataSourceMap.put(key, dataSource);
        }
    }
}
