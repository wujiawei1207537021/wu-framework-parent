package com.wu.smart.acw.server.domain.model.model.database.table.auto.stuffed.record;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 数据库表填充记录 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface DatabaseTableAutoStuffedRecordRepository {


    /**
     * describe 新增数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTableAutoStuffedRecord> story(DatabaseTableAutoStuffedRecord databaseTableAutoStuffedRecord);

    /**
     * describe 批量新增数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTableAutoStuffedRecord> batchStory(List<DatabaseTableAutoStuffedRecord> databaseTableAutoStuffedRecordList);

    /**
     * describe 查询单个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTableAutoStuffedRecord> findOne(DatabaseTableAutoStuffedRecord databaseTableAutoStuffedRecord);

    /**
     * describe 查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<DatabaseTableAutoStuffedRecord>> findList(DatabaseTableAutoStuffedRecord databaseTableAutoStuffedRecord);

    /**
     * describe 分页查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<DatabaseTableAutoStuffedRecord>> findPage(int size, int current, DatabaseTableAutoStuffedRecord databaseTableAutoStuffedRecord);

    /**
     * describe 删除数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<DatabaseTableAutoStuffedRecord> remove(DatabaseTableAutoStuffedRecord databaseTableAutoStuffedRecord);

}