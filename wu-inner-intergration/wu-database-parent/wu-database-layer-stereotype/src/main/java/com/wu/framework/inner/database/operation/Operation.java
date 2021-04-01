package com.wu.framework.inner.database.operation;

import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2021/4/1 下午3:49
 */
public interface Operation {

    <T> List<T> executeSQL(String sql, Class<T> t);

    /**
     * description 执行SQL 返回指定类型
     *
     * @param
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2020/12/29 下午1:44
     */
    <T> T executeSQLForBean(String sql, Class<T> t);


    void miss();
}
