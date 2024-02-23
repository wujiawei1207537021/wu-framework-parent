package com.wu.smart.acw.server.domain.model.database.instance.back.ups;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe 数据库备份信息 
 *
 * @author Jia wei Wu
 * @date 2023/07/09 11:24 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwInstanceBackUpsRepository {


    /**
     * describe 新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result<DatabaseInstanceBackUps> story(DatabaseInstanceBackUps databaseInstanceBackUps);

    /**
     * describe 批量新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result<DatabaseInstanceBackUps> batchStory(List<DatabaseInstanceBackUps> databaseInstanceBackUpsList);

    /**
     * describe 查询单个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result<DatabaseInstanceBackUps> findOne(DatabaseInstanceBackUps databaseInstanceBackUps);

    /**
     * describe 查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result<List<DatabaseInstanceBackUps>> findList(DatabaseInstanceBackUps databaseInstanceBackUps);

    /**
     * describe 分页查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result<LazyPage<DatabaseInstanceBackUps>> findPage(int size,int current,DatabaseInstanceBackUps databaseInstanceBackUps);

    /**
     * describe 删除数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    Result<DatabaseInstanceBackUps> remove(DatabaseInstanceBackUps databaseInstanceBackUps);

}