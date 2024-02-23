package com.wu.smart.acw.server.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.command.AcwInstanceBackUpsCommand;
import com.wu.smart.acw.server.domain.model.database.instance.back.ups.DatabaseInstanceBackUps;

import java.util.List;

/**
 * describe 数据库备份信息 
 *
 * @author Jia wei Wu
 * @date 2023/07/09 11:24 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface AcwInstanceBackUpsApplication {


    /**
     * describe 新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result<DatabaseInstanceBackUps> story(AcwInstanceBackUpsCommand acwInstanceBackUpsCommand);

    /**
     * describe 更新数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result<DatabaseInstanceBackUps> updateOne(AcwInstanceBackUpsCommand acwInstanceBackUpsCommand);

    /**
     * describe 查询单个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result<DatabaseInstanceBackUps> findOne(AcwInstanceBackUpsCommand acwInstanceBackUpsCommand);

    /**
     * describe 查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result <List<DatabaseInstanceBackUps>> findList(AcwInstanceBackUpsCommand acwInstanceBackUpsCommand);

    /**
     * describe 分页查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result <LazyPage<DatabaseInstanceBackUps>> findPage(int size, int current, AcwInstanceBackUpsCommand acwInstanceBackUpsCommand);

    /**
     * describe 删除数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result<DatabaseInstanceBackUps> remove(AcwInstanceBackUpsCommand acwInstanceBackUpsCommand);

    /**
     * 数据库实例备份
     * @param instanceId
     */
    void backUps(String instanceId);
}