package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.select;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.Execute;

import java.util.List;

/**
 * describe : 简单数据查询
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/22 21:08
 */
public interface SimpleSelectLazyLambdaStream {


    /**
     * 查询数据
     *
     * @param comparison 条件
     * @return Execute<T>
     */
    <T> Execute<T> select(BasicComparison<T, ?, ?, ?> comparison);

    /**
     * 查询数据
     *
     * @param sql        执行sql
     * @param returnType 返回结果类型
     * @return Execute<T>
     */
    <T> Execute<T> select(String sql, Class<T> returnType);

    /**
     * 查询一条数据
     *
     * @param comparison 查询条件
     * @return 查询结果
     */
    <T> T selectOne(BasicComparison<T, ?, ?, ?> comparison);

    /**
     * 查询一条数据 制定返回结果
     *
     * @param comparison 查询条件
     * @param rClazz     返回结果范型
     * @return 查询结果
     */
    <R, T> R selectOne(BasicComparison<T, ?, ?, ?> comparison, Class<R> rClazz);

    /**
     * 查询数据
     *
     * @param comparison 查询条件
     * @return 查询结果集合
     */
    <T> List<T> selectList(BasicComparison<T, ?, ?, ?> comparison);

    /**
     * 查询数据 制定返回结果
     *
     * @param comparison 查询条件
     * @param rClazz     返回结果范型
     * @return 查询结果集合
     */
    <T, R> List<R> selectList(BasicComparison<T, ?, ?, ?> comparison, Class<R> rClazz);

    /**
     * 查询分页数据
     *
     * @param comparison 查询条件
     * @param lazyPage   分页
     * @return 查询结果集合分页
     */
    <T> LazyPage<T> selectPage(BasicComparison<T, ?, ?, ?> comparison, LazyPage<T> lazyPage);


    /**
     * 查询分页数据 制定返回结果
     *
     * @param comparison 查询条件
     * @param rClazz     返回结果范型
     * @return 查询结果集合分页
     */
    <R, T> LazyPage<R> selectPage(BasicComparison<T, ?, ?, ?> comparison, LazyPage<R> lazyPage, Class<R> rClazz);

    /**
     * 分页查询
     *
     * @param lazyPage   分页参数
     * @param returnType 返回结果
     * @param sql        执行sql
     * @param params     参数
     * @return 查询结果集合分页
     */
    <T> LazyPage<T> selectPage(LazyPage<T> lazyPage, Class<T> returnType, String sql, Object... params);

    /**
     * 执行sql 返回数据
     *
     * @param sql    sql 模版
     * @param t      返回数据范型
     * @param params sql 模版参数
     * @param <T>    返回数据范型
     * @return T
     */
    <T> T executeSQLForBean(String sql, Class<T> t, Object... params);

    /**
     * 执行sql 返回数据
     *
     * @param sql    sql 模版
     * @param t      返回数据范型
     * @param params sql 模版参数
     * @param <T>    返回数据范型
     * @return List<T>
     */
    <T> List<T> executeSQL(String sql, Class<T> t, Object... params);

    /**
     * 执行操作
     *
     * @param persistenceRepository 参数
     * @return List<Object>
     */
    List<Object> execute(PersistenceRepository persistenceRepository);

    /**
     * 执行操作
     *
     * @param persistenceRepository
     * @return
     */
    Object executeOne(PersistenceRepository persistenceRepository);

    /**
     * 统计数量
     *
     * @return
     */
    <T> Long count(BasicComparison<T, ?, ?, ?> comparison);

    /**
     * 判断是否存在
     *
     * @param comparison 条件
     * @return boolean true存在 false 不存在
     */
    <T> boolean exists(BasicComparison<T, ?, ?, ?> comparison);

}
