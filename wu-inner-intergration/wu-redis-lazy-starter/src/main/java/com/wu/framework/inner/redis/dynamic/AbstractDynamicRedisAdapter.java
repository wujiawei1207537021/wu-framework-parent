package com.wu.framework.inner.redis.dynamic;


import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ObjectUtils;

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
public abstract class AbstractDynamicRedisAdapter implements DynamicRedisAdapter, InitializingBean {
    private Map<String, RedisClient> dataSourceMap;
    private String primary = "master";

    protected AbstractDynamicRedisAdapter(Map<String, RedisClient> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (ObjectUtils.isEmpty(dataSourceMap)) {

        } else {
            this.primary = dataSourceMap.keySet().iterator().next();
            dataSourceMap.forEach((alias, v) -> {
                dataSourceMap.put(alias, v);
                log.info("初始化Redis数据源:【{}】", alias);
            });
        }

    }


    public Map<String, RedisClient> getDataSourceMap() {
        if (ObjectUtils.isEmpty(this.dataSourceMap)) {
            this.dataSourceMap = new HashMap<>();
        }
        return dataSourceMap;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }
}
