package com.wu.framework.inner.layer.data.lock.endpoint;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2023/01/04 11:39 上午
 */
@Data
public class LockEndPoint {
    /**
     * key
     */
    private String key;
    /**
     * 锁分组
     */
    private String lockGroup;
    /**
     * 锁时间
     */
    private long time;
    /**
     * 锁时间单位
     */
    private TimeUnit unit;
}
