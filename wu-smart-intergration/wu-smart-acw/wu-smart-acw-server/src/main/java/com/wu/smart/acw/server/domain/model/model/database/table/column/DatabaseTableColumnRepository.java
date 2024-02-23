package com.wu.smart.acw.server.domain.model.model.database.table.column;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 数据库表字段 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface DatabaseTableColumnRepository {


    /**
     * describe 新增数据库表字段
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTableColumn> story(DatabaseTableColumn databaseTableColumn);

    /**
     * describe 批量新增数据库表字段
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTableColumn> batchStory(List<DatabaseTableColumn> databaseTableColumnList);

    /**
     * describe 查询单个数据库表字段
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTableColumn> findOne(DatabaseTableColumn databaseTableColumn);

    /**
     * describe 查询多个数据库表字段
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<DatabaseTableColumn>> findList(DatabaseTableColumn databaseTableColumn);

    /**
     * describe 分页查询多个数据库表字段
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<DatabaseTableColumn>> findPage(int size, int current, DatabaseTableColumn databaseTableColumn);

    /**
     * describe 删除数据库表字段
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTableColumn> remove(DatabaseTableColumn databaseTableColumn);

}