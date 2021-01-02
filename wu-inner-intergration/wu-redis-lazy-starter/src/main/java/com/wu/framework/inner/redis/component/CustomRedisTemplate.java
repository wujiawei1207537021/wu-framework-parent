package com.wu.framework.inner.redis.component;


import com.wu.framework.inner.redis.CustomRedis;
import com.wu.framework.inner.redis.CustomRedisDBEnum;
import com.wu.framework.inner.redis.annotation.DynamicRedisDB;
import com.wu.framework.inner.redis.enums.DefaultCustomRedisDBEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @ Description   :  获取redis不同数据库的连接对象
 * @ Author        :  wujiawei
 * @ CreateDate    :  2019/11/14 0014 10:14
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2019/11/14 0014 10:14
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */
@Slf4j
@DynamicRedisDB
public class CustomRedisTemplate extends StringRedisTemplate implements CustomRedis {


    public CustomRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {

        if (DefaultCustomRedisDBEnum.usedDB(lettuceConnectionFactory.getDatabase())) {
            lettuceConnectionFactory.setDatabase(DefaultCustomRedisDBEnum.SYS_DEFAULT.getDb());
            log.info("Redis数据库被占用" + lettuceConnectionFactory.getDatabase() + "使用默认Redis数据库" + DefaultCustomRedisDBEnum.SYS_DEFAULT.getDb());
        }
        lettuceConnectionFactory.setShareNativeConnection(false);
        setConnectionFactory(lettuceConnectionFactory);
        afterPropertiesSet();
    }

    /**
     * 通过枚举索引值指定数据库
     *
     * @param db
     * @return
     */
    private StringRedisTemplate getCustomRedisTemplate(int db) {
        LettuceConnectionFactory factory = (LettuceConnectionFactory) this.getConnectionFactory();
        factory.setShareNativeConnection(false);
        factory.setDatabase(db);
        factory.resetConnection();
        this.setConnectionFactory(factory);
        log.info("使用redis数据库" + factory.getDatabase());
        return this;
    }


    public StringRedisTemplate getCustomRedisTemplate(CustomRedisDBEnum customRedisDBEnum) {
        return getCustomRedisTemplate(customRedisDBEnum.getDb());
    }

    /**
     * 使用默认数据库
     *
     * @return
     */
    public StringRedisTemplate getCustomRedisTemplate() {
        return getCustomRedisTemplate(DefaultCustomRedisDBEnum.SYS_DEFAULT);
    }


}
