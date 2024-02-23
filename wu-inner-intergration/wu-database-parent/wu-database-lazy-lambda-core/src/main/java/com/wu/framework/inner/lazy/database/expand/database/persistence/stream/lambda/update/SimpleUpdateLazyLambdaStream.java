package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.update;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;


/**
 * describe :简单数据更新操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/9 11:29
 */
public interface SimpleUpdateLazyLambdaStream {


    /**
     * 数据更新
     *
     * @param <T>             实体对象
     * @param t               实体对象
     * @param whereComparison 条件
     * @return
     */
    <T> Integer update(T t, BasicComparison<T, ?, ?, ?> whereComparison);
}
