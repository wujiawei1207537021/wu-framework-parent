package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.delete;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/20 22:01
 */
public interface SimpleDeleteBuild<T> {
    /**
     * 数据删除
     * 实体对象
     *
     * @param comparison 条件
     * @return 影响行数
     */
    Integer delete(BasicComparison<T, ?, ?, ?> comparison);
}
