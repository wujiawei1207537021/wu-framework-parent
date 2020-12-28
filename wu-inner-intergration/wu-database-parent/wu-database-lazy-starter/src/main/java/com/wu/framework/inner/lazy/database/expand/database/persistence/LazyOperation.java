package com.wu.framework.inner.lazy.database.expand.database.persistence;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 自定义数据库持久层操作合集
 * @date : 2020/7/3 下午8:48
 */
public interface LazyOperation {

    /**
     * 批量更新或插入
     *
     * @param list
     * @param <T>
     */
    <T> void upsertList(List<T> list);

    /**
     * 插入list
     *
     * @param list
     * @param <T>
     */
    <T> void insertList(List<T> list);

    /**
     * 插入 单个
     *
     * @param t
     * @param <T>
     */
    <T> void insert(T t);

    /**
     * 更新或者插入单个 去除空值
     */
    <T> void activeUpsert(T t);

    /**
     * 根据ID更新
     *
     * @param t
     * @param <T>
     */
    <T> void updateById(T t);

    /**
     * 根据主键ids更新list
     *
     * @param list
     * @param <T>
     */
    <T> void updateAllByIdList(List<T> list);


    /**
     * 批量删除
     *
     * @param list
     * @param <T>
     */
    <T> void deleteByIdList(List<T> list);

    /**
     * 删除 Serialization
     *
     * @param t
     * @param <T>
     */
    <T> void deleteById(T t);

    /**
     * 删除所有
     *
     * @param t
     * @param <T>
     */
    @Deprecated
    <T> void deleteAll(T t);

    /**
     * 查询
     *
     * @param t
     * @param <T>
     */
    <T> T selectOne(T t);

    /**
     * 查询所有
     *
     * @param t
     * @param <T>
     */
    <T> List<T> selectAll(T t);

    /**
     * 执行sql
     *
     * @param sql
     * @param t
     * @param <T>
     * @return
     */
    <T> List<T> executeSQL(String sql, Class<T> t);

    <T> T executeSQLForBean(String sql, Class<T> t);

    void miss();
}
