package com.wu.framework.inner.redis.dynamic;


import com.wu.framework.inner.redis.config.LazyRedisProperties;
import com.wu.framework.inner.redis.dynamic.toolkit.DynamicLazyRedisContextHolder;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Role;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
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
public class LazyDynamicRedisAdapter extends AbstractDynamicRedisAdapter implements DisposableBean {

    public LazyDynamicRedisAdapter(Map<String, RedisClient> dataSourceMap) {
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
    public RedisClient determineRedisClient() {
        final String primary = getPrimary();
        final Map<String, RedisClient> dataSourceMap = getDataSourceMap();
        LazyDynamicRedisEndpoint ds = DynamicLazyRedisContextHolder.peek();
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
    public void loading(LazyRedisProperties sourceProperties) {

        String alias = sourceProperties.getAlias();
        // TODO  创建不同数据类型的数据源
        if (getDataSourceMap().containsKey(alias)) {
            return;
        }

        String host = sourceProperties.getHost();
        int port = sourceProperties.getPort();
        String password = sourceProperties.getPassword();
        int database = sourceProperties.getDatabase();
        RedisProperties.Lettuce lettuce = sourceProperties.getLettuce();
        RedisURI.Builder builder = RedisURI.builder()                // <1> 创建单机连接的连接信息
                .withHost(host)
                .withPort(port)
                .withDatabase(database)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS));
        if (!ObjectUtils.isEmpty(password)) {
            builder
                    .withPassword(password);
        }
        RedisURI redisUri = builder
                .build();
        RedisClient redisClient = RedisClient.create(redisUri);   //


        getDataSourceMap().put(alias, redisClient);
    }

    /**
     * Invoked by the containing {@code BeanFactory} on destruction of a bean.
     *
     * @throws Exception in case of shutdown errors. Exceptions will get logged
     *                   but not rethrown to allow other beans to release their resources as well.
     */
    @Override
    public void destroy() throws Exception {
        for (Map.Entry<String, RedisClient> stringRedisClientEntry : getDataSourceMap().entrySet()) {
            String key = stringRedisClientEntry.getKey();
            RedisClient value = stringRedisClientEntry.getValue();
            try {
                value.shutdown();
                log.info("close RedisClient with alias name :" + key);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
