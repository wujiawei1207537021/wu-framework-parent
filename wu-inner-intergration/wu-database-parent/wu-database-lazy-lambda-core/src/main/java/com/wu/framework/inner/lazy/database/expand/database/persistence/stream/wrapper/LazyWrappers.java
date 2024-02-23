package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.LambdaBasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.LambdaJoinBasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.StringBasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.select.SelectBeanBasicComparison;

/**
 * describe : LazyWrappers 懒惰的包装
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/9 1:11 下午
 */
public class LazyWrappers {

    /**
     * describe lambdaWrapper
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/9 1:12 下午
     **/
    public static <T> LambdaBasicComparison<T> lambdaWrapper() {
        return new LambdaBasicComparison<T>() {
        };
    }

    /**
     * 链表查询使用
     *
     * @param <T1>
     * @param <T2>
     * @return
     */
    public static <T1, T2> LambdaJoinBasicComparison<T1, T2> lambdaWrapperJoin() {
        return new LambdaJoinBasicComparison<T1, T2>() {
        };
    }


    /**
     * 链表查询使用 where 条件
     *
     * @param <T1>
     * @param <T2>
     * @return
     */
    public static <T1, T2> LambdaJoinBasicComparison<T1, T2> lambdaWrapperWhere() {
        return new LambdaJoinBasicComparison<T1, T2>() {
        };
    }

    /**
     * 链表查询使用
     *
     * @param <T1>
     * @param <T2>
     * @return
     */
    public static <T1, T2> LambdaJoinBasicComparison<T1, T2> lambdaWrapperJoinOr() {
        return new LambdaJoinBasicComparison<T1, T2>() {
        };
    }


    /**
     * describe wrapper
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/9 1:12 下午
     **/
    public static <T> StringBasicComparison<T> wrapper() {
        return new StringBasicComparison<T>() {
        };
    }

    /**
     * describe wrapperBean
     *
     * @param bean 参数对象
     * @return
     * @author Jia wei Wu
     * @date 2022/1/9 1:12 下午
     **/
    public static <T> LambdaBasicComparison<T> lambdaWrapperBean(T bean) {
        return new SelectBeanBasicComparison<T>(bean) {
        };
    }


}
