package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.select.AbstractSelectBasicComparison;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/23 8:36 下午
 */
public class StringBasicComparison<T> extends AbstractSelectBasicComparison<T, String, Object> {

    /**
     * @return describe 获取条件集合
     * @author Jia wei Wu
     * @date 2021/8/21 7:57 下午
     **/
    @Override
    public SqlPart getSqlPart() {
        return sqlPart;
    }


}
