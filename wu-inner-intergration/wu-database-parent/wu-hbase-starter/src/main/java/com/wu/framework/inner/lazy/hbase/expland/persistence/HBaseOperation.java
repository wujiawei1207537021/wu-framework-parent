package com.wu.framework.inner.lazy.hbase.expland.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.Operation;

/**
 * description HBaseOperation
 * HBase 操作使用
 *
 * @author 吴佳伟
 * @date 2021/3/26 下午5:10
 */
public interface HBaseOperation extends Operation {


    /**
     * @param <T>
     * @return t
     * @describe 新增数据
     * @author Jia wei Wu
     * @date 2021/3/27 9:21 下午
     **/
    <T> T insert(T t);


    /**
     * @param <T>
     * @return t
     * @describe 更新或者插入数据
     * @author Jia wei Wu
     * @date 2021/3/27 9:21 下午
     **/
    <T> T upsert(T t);


}
