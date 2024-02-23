package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute;


import com.wu.framework.inner.lazy.database.domain.Page;

import java.util.Collection;

/**
 * 执行操作
 * execute operation
 *
 * @author wujiawei
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
    <R> Collection<R> collection(Class<R> rClass);

    /**
     * describe 收集集合
     *
     * @param
     * @return Collection<T> 收集集合
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    Collection<T> collection();

    /**
     * describe 分叶
     *
     * @param page 分页参数
     * @return Page<T> 分页结果
     * @author Jia wei Wu
     * @date 2022/2/1 15:13
     **/
    Page<T> page(Page page);


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
