package com.wu.framework.inner.lazy.database.expand.database.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.config.LazyOperationProxyBeanAutoConfiguration;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationMethodUpdateById;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.*;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.dml.*;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql.*;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyOperationProxy;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.stereotype.proxy.ProxyLazyStrategicApproach;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 懒人数据库持久层操作合集
 * @date : 2020/7/3 下午8:48
 * @see LazyOperationProxyBeanAutoConfiguration#lazySqlOperation(LazyOperationProxy)
 */
public interface LazySqlOperation extends LazyOperation {

    /**
     * 批量更新或插入
     *
     * @param objects
     * @param <T>
     */
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodUpsert.class)
    <T> void upsert(Object... objects);

    /**
     * 插入 单个/list
     *
     * @param t
     * @param <T>
     */
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodInsert.class)
    <T> void insert(T t);

    /**
     * 更新或者存储（根据主键、唯一性索引）
     *
     * @param t   更新或保存数据对象
     * @param <T> 数据范型
     */
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodSaveOrUpdate.class)
    <T> void saveOrUpdate(T t);


    /**
     * 更新或者插入单个执行 去除空值
     * 多个数据性能会慢，不经常使用
     */
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodUpsertRemoveNull.class)
    Object upsertRemoveNull(Object... t);

    /**
     * 根据ID更新
     *
     * @param t
     * @param <T>
     */
    @Override
    @Deprecated
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodUpdateById.class)
    <T> void updateById(T t);


    /**
     * 分页查询
     *
     * @param <T>
     * @return
     */
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodPage.class)
    <T> LazyPage<T> page(@NonNull LazyPage lazyPage, @NonNull Class<T> returnType, String sql, Object... params);

    /**
     * @param sql
     * @param t
     * @param params
     * @param <T>
     * @return
     */
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodExecuteSQL.class)
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
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodExecuteSQLForBean.class)
    <T> T executeSQLForBean(String sql, Class<T> t, Object... params);

    /**
     * describe 完善表
     *
     * @param entityClasss class 对象数组
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 5:05 下午
     **/
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodPerfect.class)
    <T> T perfect(@NonNull Class<T>... entityClasss);

    /**
     * describe 完善表
     *
     * @param lazyTableEndpoints 对象数组
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/2 5:05 下午
     **/
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodPerfect.class)
    <T> T perfect(@NonNull LazyTableEndpoint... lazyTableEndpoints);

    /**
     * describe 创建表
     *
     * @param entityClasses class 对象数组
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/2 7:48 下午
     **/
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodCreateTable.class)
    <T> T createTable(@NonNull Class<T>... entityClasses);

    /**
     * describe 更新表
     *
     * @param entityClasses class 对象数组
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/2 7:49 下午
     **/
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodUpdateTable.class)
    <T> T updateTable(@NonNull Class<T>... entityClasses);

    /**
     * 执行操作
     *
     * @param persistenceRepository 预执行SQL需要的属性
     * @return List<T></>
     */
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodExecute.class)
    <T> List<T> execute(PersistenceRepository persistenceRepository);

    /**
     * 执行操作
     *
     * @param persistenceRepository 预执行SQL需要的属性
     * @param lazyPage              分页数据
     * @return LazyPage 分页数据
     */
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodExecutePage.class)
    @Override
    <T> LazyPage<T> executePage(PersistenceRepository persistenceRepository, LazyPage lazyPage);

    /**
     * 执行操作
     *
     * @param persistenceRepository 预执行SQL需要的属性
     * @return Object
     */
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodExecuteOne.class)
    Object executeOne(PersistenceRepository persistenceRepository);

    /**
     * 执行生sql 脚本
     *
     * @param resources sql脚本资源
     * @return Object
     */
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodScriptRunner.class)
    Object scriptRunner(Resource... resources);

    /**
     * 执行生sql 脚本
     *
     * @param scripts 脚本
     * @return Object
     */
    @Override
    @ProxyLazyStrategicApproach(proxyClass = LazyOperationMethodStringScriptRunner.class)
    Object stringScriptRunner(String... scripts);

    @Override
    void miss();

}
