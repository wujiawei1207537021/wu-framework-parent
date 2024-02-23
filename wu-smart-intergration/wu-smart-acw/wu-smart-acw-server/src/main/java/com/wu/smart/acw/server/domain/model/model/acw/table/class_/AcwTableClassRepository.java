package com.wu.smart.acw.server.domain.model.model.acw.table.class_;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 表和class的关联表 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwTableClassRepository {


    /**
     * describe 新增表和class的关联表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTableClass> story(AcwTableClass acwTableClass);

    /**
     * describe 批量新增表和class的关联表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTableClass> batchStory(List<AcwTableClass> acwTableClassList);

    /**
     * describe 查询单个表和class的关联表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTableClass> findOne(AcwTableClass acwTableClass);

    /**
     * describe 查询多个表和class的关联表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwTableClass>> findList(AcwTableClass acwTableClass);

    /**
     * describe 分页查询多个表和class的关联表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwTableClass>> findPage(int size, int current, AcwTableClass acwTableClass);

    /**
     * describe 删除表和class的关联表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTableClass> remove(AcwTableClass acwTableClass);

}