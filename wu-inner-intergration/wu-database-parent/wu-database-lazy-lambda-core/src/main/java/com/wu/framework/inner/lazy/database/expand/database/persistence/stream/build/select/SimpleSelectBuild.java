package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.select;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.Execute;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;

import java.util.Collection;

/***
 *
 * 简易化 build
 * 可以用于二次开发 使用范型
 *
 * @author Jia wei Wu
 */
public interface SimpleSelectBuild<T> {


    /**
     * 查询数据
     *
     * @param snippets   查询的数据
     * @param comparison 条件
     * @return Execute<T>
     */
    Execute<T> select(BasicComparison comparison, Snippet<T, ?>... snippets);

    /**
     * 查询数据
     *
     * @param comparison 条件
     * @return Execute<T>
     */
    Execute<T> select(BasicComparison<T, ?, ?, ?> comparison);

    /**
     * 查询数据
     *
     * @param sql        条件
     * @param returnType
     * @return Execute<T>
     */
    Execute<T> select(String sql, Class<T> returnType);


    /**
     * 查询一条数据
     *
     * @param comparison
     * @return
     */
    T selectOne(BasicComparison<T, ?, ?, ?> comparison);

    /**
     * 查询一条数据 制定返回结果
     *
     * @param comparison
     * @param rClazz     返回结果范型
     * @return
     */
    <R> R selectOne(BasicComparison<T, ?, ?, ?> comparison, Class<R> rClazz);

    /**
     * 查询数据
     *
     * @param comparison
     * @return
     */
    Collection<T> selectList(BasicComparison<T, ?, ?, ?> comparison);

    /**
     * 查询数据 制定返回结果
     *
     * @param comparison
     * @param rClazz     返回结果范型
     * @return
     */
    <R> Collection<R> selectList(BasicComparison<T, ?, ?, ?> comparison, Class<R> rClazz);

    /**
     * 查询分页数据
     *
     * @param comparison
     * @param lazyPage
     * @return
     */
    LazyPage<T> selectPage(BasicComparison<T, ?, ?, ?> comparison, LazyPage<T> lazyPage);

    /**
     * 查询分页数据 制定返回结果
     *
     * @param comparison
     * @param rClazz     返回结果范型
     * @return
     */
    @Deprecated
    <R> LazyPage<R> selectPage(BasicComparison<T, ?, ?, ?> comparison, LazyPage lazyPage, Class<R> rClazz);

}
