package com.wu.framework.inner.lazy.database.expand.database.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 惰性基础操作
 * @date : 2020/7/3 下午8:48
 */
public interface LazyBaseOperation extends
        LazyBaseDCLOperation,
        LazyBaseDDLOperation,
        LazyBaseDMLOperation,
        LazyBaseDQLOperation {


    /**
     * describe 执行sql 返回数据
     *
     * @param sql    sql 模版
     * @param t      返回数据范型
     * @param params sql 模版参数
     * @return
     */
    <T> List<T> executeSQL(String sql, Class<T> t, Object... params);

    /**
     * describe 执行sql 返回数据
     *
     * @param sql    sql 模版
     * @param t      返回数据范型
     * @param params sql 模版参数
     * @author Jiawei Wu
     * @date 2020/12/29 下午1:44
     */
    <T> T executeSQLForBean(String sql, Class<T> t, Object... params);


    /**
     * 执行操作
     *
     * @param persistenceRepository
     * @return
     */
    <T> List<T> execute(PersistenceRepository persistenceRepository);

    /**
     * 执行操作
     *
     * @param persistenceRepository 预执行SQL需要的属性
     * @param lazyPage              分页数据
     * @return LazyPage 分页数据
     */
    <T> LazyPage<T> executePage(PersistenceRepository persistenceRepository, LazyPage lazyPage);

    /**
     * 执行操作
     *
     * @param persistenceRepository
     * @return
     */
    Object executeOne(PersistenceRepository persistenceRepository);

    void miss();


}
