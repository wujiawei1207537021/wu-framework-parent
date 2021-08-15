package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import java.util.Collection;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 集合数据收集器
 * @date : 2021/8/8 11:25 上午
 */
public interface LambdaStreamCollector<T,R> extends LambdaSplicing<T, R> {

    /**
     * @param
     * @return
     * @describe 返回指定类型的数据
     * 收集集合
     * @author Jia wei Wu
     * @date 2021/8/8 12:03 下午
     **/
    <R> Collection<R> collection(Class<R> rClass);

    /**
     * @param
     * @return
     * @describe 收集集合
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    Collection<T> collection();


    /**
     * @param
     * @return
     * @describe 返回指定类型的数据
     * 收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    <R> T collectOne(Class<R> rClass);

    /**
     * @param
     * @return
     * @describe 收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    T collectOne();

}
