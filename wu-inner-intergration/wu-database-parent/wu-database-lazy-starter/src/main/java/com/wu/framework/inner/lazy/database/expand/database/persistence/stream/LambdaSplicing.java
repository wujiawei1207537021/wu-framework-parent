package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;


import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;

import java.util.function.Predicate;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 拼接参数
 * @date : 2021/7/17 12:24 下午
 */
public interface LambdaSplicing<T, R> extends LambdaTable<T, R> {


    /**
     * @param
     * @return
     * describe 添加where 条件
     * @author Jia wei Wu
     * @date 2021/8/8 12:33 下午
     **/
    LambdaTable<T, R> where();

    /**
     * description 参数过滤
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:46 下午
     */
    LambdaTable<T, R> filter(Predicate<? super T> predicate);

    /**
     * @param
     * @param comparison
     * @return
     * describe and条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:43 下午
     **/
    LambdaSplicing<T, R> and(BasicComparison comparison);

    /**
     * @param
     * @param comparison
     * @return
     * describe or 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:43 下午
     **/
    LambdaSplicing<T, R> or(BasicComparison comparison);

    /**
     * @param
     * @param condition
     * @param row
     * @param var
     * @return
     * describe 等于条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    LambdaSplicing<T, R> eq(boolean condition, R row, Object var);

    default LambdaSplicing<T, R> eq(R row, Object var) {
        return eq(true, row, var);
    }

    /**
     * @param
     * @return
     * describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    LambdaSplicing<T, R> gt(boolean condition, R row, Object var);

    default LambdaSplicing<T, R> gt(R row, Object var) {
        return gt(true, row, var);
    }


    /**
     * @param
     * @return
     * describe 小于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    LambdaSplicing<T, R> lt(boolean condition, R row, Object var);

    default LambdaSplicing<T, R> lt(R row, Object var) {
        return lt(true, row, var);
    }

    /**
     * @param
     * @return
     * describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    LambdaSplicing<T, R> like(boolean condition, R row, Object var);

    default LambdaSplicing<T, R> like(R row, Object var) {
        return like(true, row, var);
    }

    /**
     * @param
     * @param var
     * @param condition
     * @param row
     * @return
     * describe 区间
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    LambdaSplicing<T, R> between(boolean condition, R row, Object leftVar, Object rightVar);

    default LambdaSplicing<T, R> between(R row, Object leftVar, Object rightVar) {
        return between(true, row, leftVar, rightVar);
    }


}
