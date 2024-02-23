package com.wu.framework.inner.lazy.database.expand.database.persistence;

import com.wu.framework.inner.layer.stereotype.proxy.ProxyStrategicApproach;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.*;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 惰性基础操作
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
     * 更新或者插入单个 去除空值、对比表
     * 多个数据性能会慢，不经常使用
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodSmartUpsert.class)
    Object smartUpsert(Object... t);

    /**
     * 根据ID更新
     *
     * @param t
     * @param <T>
     */
    @Deprecated
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodUpdateById.class)
    <T> void updateById(T t);


    /**
     * 分页查询
     *
     * @param <T>
     * @return
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodPage.class)
    <T> Page<T> page(@NonNull Page page, @NonNull Class<T> returnType, String sql, Object... params);

    /**
     * @param sql
     * @param t
     * @param params
     * @param <T>
     * @return
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodExecuteSQL.class)
    <T> List<T> executeSQL(String sql, Class<T> t, Object... params);

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
    <T> T executeSQLForBean(String sql, Class<T> t, Object... params);

    /**
     * describe 完善表
     *
     * @param entityClasss class 对象数组
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 5:05 下午
     **/
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodPerfect.class)
    <T> T perfect(@NonNull Class<T>... entityClasss);

    /**
     * describe 创建表
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 7:48 下午
     **/
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodCreate.class)
    <T> T createTable(@NonNull Class<T>... entityClasss);

    /**
     * describe 更新表
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 7:49 下午
     **/
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodUpdate.class)
    <T> T updateTable(@NonNull Class<T>... entityClasss);

    /**
     * 执行操作
     *
     * @param persistenceRepository
     * @return
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodExecute.class)
    List<Object> execute(PersistenceRepository persistenceRepository);

    /**
     * 执行操作
     *
     * @param persistenceRepository
     * @return
     */
    @ProxyStrategicApproach(proxyClass = LazyOperationMethodExecuteOne.class)
    Object executeOne(PersistenceRepository persistenceRepository);

    void miss();


}
