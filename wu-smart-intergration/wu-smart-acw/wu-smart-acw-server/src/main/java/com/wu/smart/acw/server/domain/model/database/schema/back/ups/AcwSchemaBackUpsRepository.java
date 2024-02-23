package com.wu.smart.acw.server.domain.model.database.schema.back.ups;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 数据库备份信息 
 *
 * @author Jia wei Wu
 * @date 2023/07/09 03:49 下午
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
     * @date 2023/07/09 03:49 下午
     **/

    Result<DatabaseSchemaBackUps> story(DatabaseSchemaBackUps databaseSchemaBackUps);

    /**
     * describe 批量新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    Result<DatabaseSchemaBackUps> batchStory(List<DatabaseSchemaBackUps> databaseSchemaBackUpsList);

    /**
     * describe 查询单个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    Result<DatabaseSchemaBackUps> findOne(DatabaseSchemaBackUps databaseSchemaBackUps);

    /**
     * describe 查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    Result<List<DatabaseSchemaBackUps>> findList(DatabaseSchemaBackUps databaseSchemaBackUps);

    /**
     * describe 分页查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    Result<LazyPage<DatabaseSchemaBackUps>> findPage(int size,int current,DatabaseSchemaBackUps databaseSchemaBackUps);

    /**
     * describe 删除数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    Result<DatabaseSchemaBackUps> remove(DatabaseSchemaBackUps databaseSchemaBackUps);

}