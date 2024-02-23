package com.wu.smart.acw.server.domain.model.model.database.table;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 数据库表信息 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface DatabaseTableRepository {


    /**
     * describe 新增数据库表信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTable> story(DatabaseTable databaseTable);

    /**
     * describe 批量新增数据库表信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTable> batchStory(List<DatabaseTable> databaseTableList);

    /**
     * describe 查询单个数据库表信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTable> findOne(DatabaseTable databaseTable);

    /**
     * describe 查询多个数据库表信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<DatabaseTable>> findList(DatabaseTable databaseTable);

    /**
     * describe 分页查询多个数据库表信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<DatabaseTable>> findPage(int size, int current, DatabaseTable databaseTable);

    /**
     * describe 删除数据库表信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTable> remove(DatabaseTable databaseTable);

}