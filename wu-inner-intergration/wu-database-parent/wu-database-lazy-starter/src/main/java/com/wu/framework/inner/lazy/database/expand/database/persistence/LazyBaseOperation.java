package com.wu.framework.inner.lazy.database.expand.database.persistence;

import com.wu.framework.inner.layer.stereotype.proxy.ProxyStrategicApproach;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.*;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 惰性基础操作
 * @date : 2020/7/3 下午8:48
 */
public interface LazyBaseOperation {

    /**
     * 批量更新或插入
     *
     * @param objects
     * @param <T>
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodUpsert.class)
    <T> void upsert(Object... objects);
    /**
     * 插入 单个/list
     *
     * @param t
     * @param <T>
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodInsert.class)
    <T> void insert(T t);

    /**
     * 更新或者插入单个 去除空值
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodActiveInsert.class)
    <T> void activeUpsert(T t);

    /**
     * 根据ID更新
     *
     * @param t
     * @param <T>
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodUpdateById.class)
    <T> void updateById(T t);

    /**
     * 根据主键ids更新list
     *
     * @param list
     * @param <T>
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodUpdateAllById.class)
    <T> void updateAllByIdList(List<T> list);


    /**
     * 批量删除
     *
     * @param list
     * @param <T>
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodDeleteByIdList.class)
    <T> void deleteByIdList(List<T> list);

    /**
     * 删除 Serialization
     *
     * @param t
     * @param <T>
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodDeleteById.class)
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
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodSelectOne.class)
    <T> T selectOne(T t);

    /**
     * 查询所有
     *
     * @param t
     * @param <T>
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodSelectList.class)
    <T> List<T> selectAll(T t);

    /**
     * 分页查询
     *
     * @param <T>
     * @return
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodPage.class)
    <T> Page<T> page(@NonNull Page page, @NonNull Class<T> returnType, String sql, Object... params);

    /**
     * 执行sql
     *
     * @param sql
     * @param t
     * @param <T>
     * @return
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodExecuteSQL.class)
    <T> List<T> executeSQL(String sql, Class<T> t);

    /**
     * description 执行SQL 返回指定类型
     *
     * @param
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2020/12/29 下午1:44
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodExecuteSQLForBean.class)
    <T> T executeSQLForBean(String sql, Class<T> t);

    void miss();
}
