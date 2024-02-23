package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.delete;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;

/**
 * describe : 删除数据
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/9 22:09
 */
public interface SimpleDeleteLazyLambdaStream {


    /**
     * 数据删除
     *
     * @param <T>        实体对象
     * @param comparison 条件
     * @return
     */
    <T> Integer delete(BasicComparison<T, ?, ?, ?> comparison);
}
