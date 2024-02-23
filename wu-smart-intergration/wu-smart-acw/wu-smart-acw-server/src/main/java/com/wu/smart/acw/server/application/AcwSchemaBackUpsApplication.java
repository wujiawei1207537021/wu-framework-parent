package com.wu.smart.acw.server.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.command.AcwSchemaBackUpsCommand;
import com.wu.smart.acw.server.application.dto.AcwSchemaBackUpsDTO;
import com.wu.smart.acw.server.domain.model.database.schema.back.ups.DatabaseSchemaBackUps;

import java.util.List;

/**
 * describe 数据库备份信息
 *
 * @author Jia wei Wu
 * @date 2023/07/09 03:48 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface AcwSchemaBackUpsApplication {


    /**
     * describe 新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    Result<DatabaseSchemaBackUps> story(AcwSchemaBackUpsCommand acwSchemaBackUpsCommand);

    /**
     * describe 更新数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    Result<DatabaseSchemaBackUps> updateOne(AcwSchemaBackUpsCommand acwSchemaBackUpsCommand);

    /**
     * describe 查询单个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    Result<DatabaseSchemaBackUps> findOne(AcwSchemaBackUpsCommand acwSchemaBackUpsCommand);

    /**
     * describe 查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    Result<List<DatabaseSchemaBackUps>> findList(AcwSchemaBackUpsCommand acwSchemaBackUpsCommand);

    /**
     * describe 分页查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    Result<LazyPage<AcwSchemaBackUpsDTO>> findPage(int size, int current, AcwSchemaBackUpsCommand acwSchemaBackUpsCommand);

    /**
     * describe 删除数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    Result<DatabaseSchemaBackUps> remove(AcwSchemaBackUpsCommand acwSchemaBackUpsCommand);

    /**
     * 数据库备份
     *
     * @param databaseInstanceBackUpsCommand
     */
    void backUps(AcwSchemaBackUpsCommand databaseInstanceBackUpsCommand);
}