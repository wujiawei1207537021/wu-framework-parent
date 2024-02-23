package com.wu.framework.inner.lazy.database.datasource.proxy.sql;

import lombok.Data;

/**
 * sql上下文
 */
@Data
public class LazySQLContext {
    /**
     * 执行sql
     */
    private String sql;
    /**
     * 执行时间
     */
    private Long beginTime;
    /**
     * 执行结束时间
     */
    private Long endTime;

}
