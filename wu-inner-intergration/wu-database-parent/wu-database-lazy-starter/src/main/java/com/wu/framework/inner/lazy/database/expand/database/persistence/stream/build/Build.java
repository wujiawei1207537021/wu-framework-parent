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
     * @param comparison
     * @return
     */
    Execute<T> select(BasicComparison comparison);

    /**
     * 删除数据
     *
     * @param comparison
     * @return
     */
    Long delete(BasicComparison comparison);
}
