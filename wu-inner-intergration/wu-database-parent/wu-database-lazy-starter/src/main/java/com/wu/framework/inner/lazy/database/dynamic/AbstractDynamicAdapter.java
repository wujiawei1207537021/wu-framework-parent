package com.wu.framework.inner.lazy.database.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据源适配器 抽象类
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/29 20:27
 */
@Slf4j
public abstract class AbstractDynamicAdapter implements DynamicAdapter, InitializingBean {
    private final Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();
    private String primary;

    private final ApplicationContext applicationContext;

    protected AbstractDynamicAdapter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, DataSource> dataSourceMap = applicationContext.getBeansOfType(DataSource.class);
        this.primary = dataSourceMap.keySet().iterator().next();
        dataSourceMap.forEach((alias, v) -> {
            try {
                mybatisDataSource(alias, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("初始化数据源:【{}】", alias);
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

    public Map<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }
}
