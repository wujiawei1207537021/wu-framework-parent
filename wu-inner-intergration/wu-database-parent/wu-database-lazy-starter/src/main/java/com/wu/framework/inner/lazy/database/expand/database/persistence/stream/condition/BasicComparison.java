package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import org.springframework.util.ObjectUtils;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 基本比较
 * @date : 2021/8/21 6:38 下午
 */
public interface BasicComparison<T, R, V, C extends BasicComparison<T, R, V, C>> {


    /**
     * describe 等于条件
     *
     * @param condition 是否
     * @param row       行
     * @param var       行数据
     * @return C 返回数据
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    C eq(boolean condition, R row, V var);

    default C eq(R row, V var) {
        return eq(true, row, var);
    }

    /**
     * 忽略空数据
     *
     * @param row
     * @param var
     * @return
     */
    default C eqIgnoreEmpty(R row, V var) {
        return eq(!ObjectUtils.isEmpty(var), row, var);
    }

    /**
     * @param
     * @return describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    C gt(boolean condition, R row, V var);

    default C gt(R row, V var) {
        return gt(true, row, var);
    }

    /**
     * @param
     * @return describe 小于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    C lt(boolean condition, R row, V var);

    default C lt(R row, V var) {
        return lt(true, row, var);
    }

    /**
     * @param
     * @return describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    C like(boolean condition, R row, V var);

    default C like(R row, V var) {
        return like(true, row, var);
    }

    /**
     * @param
     * @param condition
     * @param row
     * @param rightVar
     * @return describe 区间
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    C between(boolean condition, R row, Object leftVar, Object rightVar);

    default C between(R row, Object leftVar, Object rightVar) {
        return between(true, row, leftVar, rightVar);
    }

    /**
     * @param
     * @return describe 获取条件集合
     * @author Jia wei Wu
     * @date 2021/8/21 7:57 下午
     **/
    ConditionList getConditionList();

}
