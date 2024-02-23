package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.dml;

import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import lombok.NonNull;


/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/18 19:08
 */
public interface SimpleDMLLambdaStream {

    /**
     * describe 完善表
     *
     * @param entityClasses class 对象数组
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/2 5:05 下午
     **/
    <T> T perfect(@NonNull Class<T>... entityClasses);

    /**
     * describe 完善表
     *
     * @param tableEndpoints 表schema
     * @return T
     * @author Jia wei Wu
     * @date 2022/10/6 14:44
     **/
    <T> T perfect(@NonNull LazyTableEndpoint... tableEndpoints);

    /**
     * describe 创建表
     *
     * @param entityClasses class 对象数组
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/2 7:48 下午
     **/
    <T> T createTable(@NonNull Class<T>... entityClasses);

    /**
     * describe 更新表
     *
     * @param entityClasses 实体
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/2 7:49 下午
     **/
    <T> T updateTable(@NonNull Class<T>... entityClasses);
}
