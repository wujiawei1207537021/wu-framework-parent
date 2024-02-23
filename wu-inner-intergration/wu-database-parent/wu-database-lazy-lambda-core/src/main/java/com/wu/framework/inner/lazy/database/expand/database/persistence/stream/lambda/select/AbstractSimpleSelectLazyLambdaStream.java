package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.select;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.persistence.util.SqlMessageFormatUtil;

import java.util.List;

/**
 * describe : 简单查询抽象类
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/22 21:08
 */
public abstract class AbstractSimpleSelectLazyLambdaStream implements SimpleSelectLazyLambdaStream {


    /**
     * 查询一条数据
     *
     * @param comparison 查询条件
     * @return 查询结果
     */
    @Override
    public <T> T selectOne(BasicComparison<T, ?, ?, ?> comparison) {
        return select(comparison).collectOne();
    }

    /**
     * 查询一条数据 制定返回结果
     *
     * @param comparison 查询条件
     * @param rClazz     返回结果范型
     * @return 查询结果
     */
    @Override
    public <R, T> R selectOne(BasicComparison<T, ?, ?, ?> comparison, Class<R> rClazz) {
        return select(comparison).collectOne(rClazz);
    }

    /**
     * 查询数据
     *
     * @param comparison 查询条件
     * @return 查询结果集合
     */
    @Override
    public <T> List<T> selectList(BasicComparison<T, ?, ?, ?> comparison) {
        return select(comparison).collection();
    }

    /**
     * 查询数据 制定返回结果
     *
     * @param comparison 查询条件
     * @param rClazz     返回结果范型
     * @return 查询结果集合
     */
    @Override
    public <T, R> List<R> selectList(BasicComparison<T, ?, ?, ?> comparison, Class<R> rClazz) {
        return select(comparison).collection(rClazz);
    }

    /**
     * 查询分页数据
     *
     * @param comparison 查询条件
     * @param lazyPage   分页参数
     * @return 查询结果集合分页
     */
    @Override
    public <T> LazyPage<T> selectPage(BasicComparison<T, ?, ?, ?> comparison, LazyPage<T> lazyPage) {
        return select(comparison).page(lazyPage);
    }

    /**
     * 查询分页数据 制定返回结果
     *
     * @param comparison 查询条件
     * @param lazyPage   分页参数
     * @param rClazz     返回结果范型
     * @return 查询结果集合分页
     */
    @Override
    public <R, T> LazyPage<R> selectPage(BasicComparison<T, ?, ?, ?> comparison, LazyPage<R> lazyPage, Class<R> rClazz) {
        return select(comparison).page(lazyPage, rClazz);
    }

    /**
     * 分页查询
     *
     * @param lazyPage   分页参数
     * @param returnType 返回结果
     * @param sql        执行sql
     * @param params     参数
     * @return 查询结果集合分页
     */
    @Override
    public <T> LazyPage<T> selectPage(LazyPage<T> lazyPage, Class<T> returnType, String sql, Object... params) {
        String sqlFormat = String.format(sql, params);
        sqlFormat = SqlMessageFormatUtil.format(sql, params);
        return select(sqlFormat, returnType).page(lazyPage);
    }

}
