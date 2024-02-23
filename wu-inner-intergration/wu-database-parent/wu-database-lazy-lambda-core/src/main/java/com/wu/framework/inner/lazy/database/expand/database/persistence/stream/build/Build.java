package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.select.SimpleSelectBuild;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;

/***
 *
 * build
 *
 */
public interface Build<T> extends SimpleSelectBuild<T> {

    /**
     * 删除数据
     *
     * @param comparison 条件
     * @return Execute<T>
     */
    Integer delete(BasicComparison<T, ?, ?, ?> comparison);

    /**
     * 判断是否存在
     *
     * @param comparison 条件
     * @return boolean true存在 false 不存在
     */
    boolean exists(BasicComparison<T, ?, ?, ?> comparison);

    /**
     * 统计表中的数据
     *
     * @param comparison 条件
     * @return boolean true存在 false 不存在
     */
    Long count(BasicComparison<T, ?, ?, ?> comparison);
}
