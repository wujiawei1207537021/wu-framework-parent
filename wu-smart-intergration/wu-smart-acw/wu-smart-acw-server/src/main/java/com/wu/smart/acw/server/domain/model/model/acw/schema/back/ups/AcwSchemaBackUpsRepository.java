package com.wu.smart.acw.server.domain.model.model.acw.schema.back.ups;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 数据库备份信息 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwSchemaBackUpsRepository {


    /**
     * describe 新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwSchemaBackUps> story(AcwSchemaBackUps acwSchemaBackUps);

    /**
     * describe 批量新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwSchemaBackUps> batchStory(List<AcwSchemaBackUps> acwSchemaBackUpsList);

    /**
     * describe 查询单个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwSchemaBackUps> findOne(AcwSchemaBackUps acwSchemaBackUps);

    /**
     * describe 查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwSchemaBackUps>> findList(AcwSchemaBackUps acwSchemaBackUps);

    /**
     * describe 分页查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwSchemaBackUps>> findPage(int size, int current, AcwSchemaBackUps acwSchemaBackUps);

    /**
     * describe 删除数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwSchemaBackUps> remove(AcwSchemaBackUps acwSchemaBackUps);

}