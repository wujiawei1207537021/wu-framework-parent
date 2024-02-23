package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.insert;

import com.wu.framework.inner.layer.data.LayerDataAnalyzeAdapter;

/**
 * description insert
 *
 * @author Jia wei Wu
 * @date 2022/12/30 6:01 下午
 */
public interface SimpleInsertLambdaStream extends LayerDataAnalyzeAdapter {


    /**
     * describe upsert
     *
     * @param save 存储对象
     * @author Jia wei Wu
     * @date 2022/1/20 8:44 下午
     **/
    <T> void upsert(T save);

    /**
     * describe 更新或者插入单个执行 去除空值
     *
     * @param save 操作的对象
     *             返回的数据 void
     * @author Jia wei Wu
     * @date 2022/1/20 8:44 下午
     **/
    <T> void upsertRemoveNull(T save);

    /**
     * 数据插入
     *
     * @param save 存储对象
     * @param <T>  存储对象范型
     */
    <T> void insert(T save);

    /**
     * 数据插入、更新
     *
     * @param save 存储对象
     * @param <T>  存储对象范型
     */
    <T> void saveOrUpdate(T save);

}
