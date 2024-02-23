package com.wu.smart.acw.server.domain.model.model.acw.instance.back.ups;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 数据库实例备份信息 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwInstanceBackUpsRepository {


    /**
     * describe 新增数据库实例备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwInstanceBackUps> story(AcwInstanceBackUps acwInstanceBackUps);

    /**
     * describe 批量新增数据库实例备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwInstanceBackUps> batchStory(List<AcwInstanceBackUps> acwInstanceBackUpsList);

    /**
     * describe 查询单个数据库实例备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwInstanceBackUps> findOne(AcwInstanceBackUps acwInstanceBackUps);

    /**
     * describe 查询多个数据库实例备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwInstanceBackUps>> findList(AcwInstanceBackUps acwInstanceBackUps);

    /**
     * describe 分页查询多个数据库实例备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwInstanceBackUps>> findPage(int size, int current, AcwInstanceBackUps acwInstanceBackUps);

    /**
     * describe 删除数据库实例备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwInstanceBackUps> remove(AcwInstanceBackUps acwInstanceBackUps);

}