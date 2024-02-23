package com.wu.smart.acw.server.domain.model.model.acw.table.column;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 数据库表字段（即将弃用） 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwTableColumnRepository {


    /**
     * describe 新增数据库表字段（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTableColumn> story(AcwTableColumn acwTableColumn);

    /**
     * describe 批量新增数据库表字段（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTableColumn> batchStory(List<AcwTableColumn> acwTableColumnList);

    /**
     * describe 查询单个数据库表字段（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTableColumn> findOne(AcwTableColumn acwTableColumn);

    /**
     * describe 查询多个数据库表字段（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwTableColumn>> findList(AcwTableColumn acwTableColumn);

    /**
     * describe 分页查询多个数据库表字段（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwTableColumn>> findPage(int size, int current, AcwTableColumn acwTableColumn);

    /**
     * describe 删除数据库表字段（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTableColumn> remove(AcwTableColumn acwTableColumn);

}