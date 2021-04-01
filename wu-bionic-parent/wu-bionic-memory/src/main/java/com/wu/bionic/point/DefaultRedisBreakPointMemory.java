package com.wu.bionic.point;

import com.wu.bionic.point.config.ApplicationConfig;
import com.wu.bionic.point.so.DefaultBreakPointSo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * description 默认redis 记忆处理
 *
 * @author Jia wei Wu
 * @date 2021/2/2 下午8:09
 */
@Component
public class DefaultRedisBreakPointMemory extends BreakPointMemoryAbstract {
    private final RedisTemplate<String, DefaultBreakPointSo> redisTemplate;
    // 大类区分服务
    private String KEY = "BREAK_POINT_MEMORY:";

    public DefaultRedisBreakPointMemory(RedisTemplate<String, DefaultBreakPointSo> redisTemplate, ApplicationConfig applicationConfig) {
        this.redisTemplate = redisTemplate;
        KEY += applicationConfig.getName().toUpperCase();
    }

    /**
     * description 断点记忆存储
     *
     * @param defaultBreakPointSo
     * @author Jia wei Wu
     * @date 2021/2/2 下午6:55
     */
    @Override
    public void storage(DefaultBreakPointSo defaultBreakPointSo) {
        // 具体服务
        Long add = redisTemplate.opsForSet().add(KEY, defaultBreakPointSo);
        System.out.println(String.format("断点数据新增:%s个", add));
    }

    /**
     * description 记忆清除
     *
     * @param defaultBreakPointSo@return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/2/2 下午6:56
     */
    @Override
    public void clear(DefaultBreakPointSo defaultBreakPointSo) {
        Long remove = redisTemplate.opsForSet().remove(KEY, defaultBreakPointSo);
        System.out.println(String.format("断点数据删除:%s个", remove));
    }

    /**
     * description 记忆获取
     *
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/2/2 下午6:57
     */
    @Override
    public Collection<DefaultBreakPointSo> acquisition() {
        Set<DefaultBreakPointSo> members = redisTemplate.opsForSet().members(KEY);
        System.out.println("记忆获取:" + members);
        System.out.println(String.format("记忆碎片🧩【%s】个", members.size()));
        return members;
    }
}
