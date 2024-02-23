package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;

import java.util.Collection;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/20 21:52
 */
public abstract class AbstractSimpleSelectBuild<T> implements Build<T> {
    /**
     * 查询一条数据
     *
     * @param comparison
     * @return
     */
    @Override
    public T selectOne(BasicComparison<T, ?, ?, ?> comparison) {
        return select(comparison).collectOne();
    }

    /**
     * 查询一条数据 制定返回结果
     *
     * @param comparison
     * @param rClazz     返回结果范型
     * @return
     */
    @Override
    public <R> R selectOne(BasicComparison<T, ?, ?, ?> comparison, Class<R> rClazz) {
        return select(comparison).collectOne(rClazz);
    }

    /**
     * 查询数据
     *
     * @param comparison
     * @return
     */
    @Override
    public Collection<T> selectList(BasicComparison<T, ?, ?, ?> comparison) {
        return select(comparison).collection();
    }

    /**
     * 查询数据 制定返回结果
     *
     * @param comparison 查询条件
     * @param rClazz     返回结果范型
     * @return 结果集合
     */
    @Override
    public <R> Collection<R> selectList(BasicComparison<T, ?, ?, ?> comparison, Class<R> rClazz) {
        return select(comparison).collection(rClazz);
    }

    /**
     * 查询分页数据
     *
     * @param comparison 查询条件
     * @param lazyPage   分页参数
     * @return 返回分页数据
     */
    @Override
    public LazyPage<T> selectPage(BasicComparison<T, ?, ?, ?> comparison, LazyPage<T> lazyPage) {
        return select(comparison).page(lazyPage);
    }

    /**
     * 查询分页数据 制定返回结果
     *
     * @param comparison 查询参数
     * @param lazyPage   查询分页
     * @param rClazz     返回结果范型
     * @return 返回分页结果
     */
    @Override
    public <R> LazyPage<R> selectPage(BasicComparison<T, ?, ?, ?> comparison, LazyPage lazyPage, Class<R> rClazz) {
        return select(comparison).page(lazyPage, rClazz);
    }
}
