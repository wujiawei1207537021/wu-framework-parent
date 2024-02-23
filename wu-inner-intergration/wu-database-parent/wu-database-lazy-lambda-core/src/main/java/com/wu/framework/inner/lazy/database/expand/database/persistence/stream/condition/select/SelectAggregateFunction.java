package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.select;

/**
 * description mysql 聚合函数
 *
 * @author Jia wei Wu
 * @date 2022/12/27 10:43 上午
 */
public interface SelectAggregateFunction<T, R, C extends SelectAggregateFunction<T, R, C>> {

    C count(R row);

    C sum(R row);

    C max(R row);

    C mini(R row);
}
