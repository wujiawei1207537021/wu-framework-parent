package com.wu.framework.inner.lazy.hbase.expland.constant;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 常量
 * @date : 2021/3/29 7:34 下午
 */
public class HBaseOperationMethodCounts {


    public static final String MISS = "miss";

    /**
     * {@link com.wu.framework.inner.lazy.hbase.expland.persistence.HBaseOperation#insert(Object)}
     */
    public static final String INSERT = "insert";// 数据插入

    /**
     * {@link com.wu.framework.inner.lazy.hbase.expland.persistence.HBaseOperation#insert(Object)}
     */
    public static final String INSERT_LIST="insertList";// 数据批量插入

    public static final String UPSERT = "upsert"; // 数据更新

}
