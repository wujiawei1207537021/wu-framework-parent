package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;


import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 拼接参数
 * @date : 2021/7/17 12:24 下午
 */
public interface LambdaSplicing<T, R> extends LambdaTable<T, R> {

    /**
     * description 左关联
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:47 下午
     */
    LambdaTable<T, R> leftJoin(Consumer consumer);

    /**
     * description 右关联
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:48 下午
     */
    LambdaTable<T, R> rightJoin(Consumer consumer);

    /**
     * @param
     * @return
     * @describe 添加where 条件
     * @author Jia wei Wu
     * @date 2021/8/8 12:33 下午
     **/
    LambdaSplicing<T, R> where();

    /**
     * description 参数过滤
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:46 下午
     */
    LambdaSplicing<T, R> filter(Predicate<? super T> predicate);

    /**
     * @param
     * @return
     * @describe and条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:43 下午
     **/
    LambdaStreamCollector<T, R> and(boolean condition, R row, Object var);

    /**
     * @param
     * @param condition
     * @param row
     * @param var
     * @return
     * @describe or 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:43 下午
     **/
    LambdaStreamCollector<T, R> or(boolean condition, R row, Object var);

    /**
     * @param
     * @param condition
     * @param row
     * @param var
     * @return
     * @describe 等于条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    LambdaStreamCollector<T, R> eq(boolean condition, R row, Object var);

    default LambdaStreamCollector<T, R> eq(R row, Object var) {
        return eq(true, row, var);
    }

    /**
     * @param
     * @return
     * @describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    LambdaStreamCollector<T, R> gt(boolean condition, R row, Object var);

    default LambdaStreamCollector<T, R> gt(R row, Object var) {
        return gt(true, row, var);
    }


    /**
     * @param
     * @return
     * @describe 小于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    LambdaStreamCollector<T, R> lt(boolean condition, R row, Object var);

    default LambdaStreamCollector<T, R> lt(R row, Object var) {
        return lt(true, row, var);
    }

    /**
     * @param
     * @return
     * @describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    LambdaStreamCollector<T, R> like(boolean condition, R row, Object var);

    default LambdaStreamCollector<T, R> like(R row, Object var) {
        return like(true, row, var);
    }

    /**
     * @param
     * @param condition
     * @param row
     * @param var
     * @return
     * @describe 区间
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    LambdaStreamCollector<T, R> between(boolean condition, R row, Object leftVar, Object rightVar);

    default LambdaStreamCollector<T, R> between(R row, Object leftVar, Object rightVar) {
        return between(true, row, leftVar, rightVar);
    }


    /**
     * @param
     * @return
     * @describe 获取执行的sql语句
     * @author Jia wei Wu
     * @date 2021/8/8 2:28 下午
     **/
    String getSqlStatement();


}
