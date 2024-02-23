package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.Execute;

/***
 *
 * build
 *
 */
public interface Build<T> {

    /**
     * 查询数据
     *
     * @param comparison 条件
     * @return Execute<T>
     */
    Execute<T> select(BasicComparison comparison);

    /**
     * 删除数据
     *
     * @param comparison 条件
     * @return Execute<T>
     */
    Integer delete(BasicComparison comparison);

    /**
     * 判断是否存在
     *
     * @param comparison 条件
     * @return boolean true存在 false 不存在
     */
    boolean exists(BasicComparison comparison);

    /**
     * 统计表中的数据
     *
     * @param comparison 条件
     * @return boolean true存在 false 不存在
     */
    Long count(BasicComparison comparison);
}
