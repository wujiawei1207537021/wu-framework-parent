package com.wu.smart.acw.server.domain.model.model.acw.table.auto.stuffed.record;

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

public interface AcwTableAutoStuffedRecordRepository {


    /**
     * describe 新增数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTableAutoStuffedRecord> story(AcwTableAutoStuffedRecord acwTableAutoStuffedRecord);

    /**
     * describe 批量新增数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwTableAutoStuffedRecord>> batchStory(List<AcwTableAutoStuffedRecord> acwTableAutoStuffedRecordList);

    /**
     * describe 查询单个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTableAutoStuffedRecord> findOne(AcwTableAutoStuffedRecord acwTableAutoStuffedRecord);

    /**
     * describe 查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwTableAutoStuffedRecord>> findList(AcwTableAutoStuffedRecord acwTableAutoStuffedRecord);

    /**
     * describe 分页查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwTableAutoStuffedRecord>> findPage(int size, int current, AcwTableAutoStuffedRecord acwTableAutoStuffedRecord);

    /**
     * describe 删除数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTableAutoStuffedRecord> remove(AcwTableAutoStuffedRecord acwTableAutoStuffedRecord);

    /**
     * 数据批量删除
     *
     * @param acwTableAutoStuffedRecordList 删除出数据对象
     */
    void batchRemove(List<AcwTableAutoStuffedRecord> acwTableAutoStuffedRecordList);
}