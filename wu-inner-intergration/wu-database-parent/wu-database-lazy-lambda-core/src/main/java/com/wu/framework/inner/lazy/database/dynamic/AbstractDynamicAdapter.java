package com.wu.framework.inner.lazy.database.dynamic;


import com.wu.framework.inner.lazy.database.datasource.proxy.LazyProxyDataSource;
import com.wu.framework.inner.lazy.database.datasource.proxy.util.LazyDataSourceProxyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ObjectUtils;
import wu.framework.bean.wu2020.ReflexUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.HashMap;
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
    private Map<String, LazyProxyDataSource> dataSourceMap;
    private String primary = "master";

    protected AbstractDynamicAdapter(Map<String, LazyProxyDataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
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
     * @param key        数据源alias
     * @param dataSource 数据源
     * @throws Exception 异常信息
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
            mybatisDataSourceMap.forEach((mybatisKey, mybatisDataSource) -> dataSourceMap.put(mybatisKey, LazyDataSourceProxyUtils.proxy(mybatisDataSource)));

        } else {
            dataSourceMap.put(key, LazyDataSourceProxyUtils.proxy(dataSource));
        }
    }

    /**
     * 获取所有数据源
     *
     * @return 当前数据源map
     */
    public Map<String, LazyProxyDataSource> getDataSourceMap() {
        if (ObjectUtils.isEmpty(this.dataSourceMap)) {
            this.dataSourceMap = new HashMap<>();
        }
        return dataSourceMap;
    }

    /**
     * 根据数据源别名关闭数据源
     *
     * @param alias 数据源别名
     */
    public void closeDataSource(String alias) {
        if (this.dataSourceMap.containsKey(alias)) {
            LazyProxyDataSource lazyProxyDataSource = this.dataSourceMap.get(alias);
            lazyProxyDataSource.close();
        } else {
            throw new RuntimeException("无法通过数据源ID：【" + alias + "】获取数据源，关闭数据源失败");
        }
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }
}
