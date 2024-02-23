package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

import java.util.List;
import java.util.stream.Stream;

/**
 * 执行操作
 * execute operation
 *
 * @author Jia wei Wu
 */
public interface Execute<T> {
    /**
     * describe 返回指定类型的数据 收集集合
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2021/8/8 12:03 下午
     **/
    <R> List<R> collection(Class<R> rClass);

    /**
     * describe Stream
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/7 23:08
     **/
    default <R> Stream<R> stream(Class<R> rClass) {
        return collection(rClass).stream();
    }


    /**
     * describe 收集集合
     *
     * @param
     * @return Collection<T> 收集集合
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    List<T> collection();

    /**
     * describe Stream
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/7 23:08
     **/
    default Stream<T> stream() {
        return collection().stream();
    }

    /**
     * describe 分页
     *
     * @param lazyPage 分页参数
     * @return LazyPage<T> 分页结果
     * @author Jia wei Wu
     * @date 2022/2/1 15:13
     **/
    <R> LazyPage<R> page(LazyPage<R> lazyPage);

    /**
     * describe 分页
     *
     * @param lazyPage 分页参数
     * @return LazyPage<T> 分页结果
     * @author Jia wei Wu
     * @date 2022/6/1 21:29
     **/
    <R> LazyPage<R> page(LazyPage<R> lazyPage, Class<R> rClass);

    /**
     * describe 返回指定类型的数据
     *
     * @param
     * @return 收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    <R> R collectOne(Class<R> rClass);

    /**
     * describe 返回数据
     *
     * @param
     * @return 收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    T collectOne();

}
