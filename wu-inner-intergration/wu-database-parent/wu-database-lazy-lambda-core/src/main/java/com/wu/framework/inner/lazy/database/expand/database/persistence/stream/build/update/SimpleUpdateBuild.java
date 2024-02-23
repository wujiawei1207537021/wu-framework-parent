package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.update;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;

/***
 *
 * 简易化 build
 *
 * @author Jia wei Wu
 */
public interface SimpleUpdateBuild<T> {


    /**
     * 查询数据
     *
     * @param t          实体对象
     * @param comparison 条件
     * @return Execute<T>
     */
    Integer update(T t, BasicComparison<T, ?, ?, ?> comparison);


}
