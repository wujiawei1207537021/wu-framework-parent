package com.wu.smart.acw.server.domain.model.model.acw.application.api;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 应用API 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwApplicationApiRepository {


    /**
     * describe 新增应用API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApi> story(AcwApplicationApi acwApplicationApi);

    /**
     * describe 批量新增应用API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApi> batchStory(List<AcwApplicationApi> acwApplicationApiList);

    /**
     * describe 查询单个应用API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApi> findOne(AcwApplicationApi acwApplicationApi);

    /**
     * describe 查询多个应用API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwApplicationApi>> findList(AcwApplicationApi acwApplicationApi);

    /**
     * describe 分页查询多个应用API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwApplicationApi>> findPage(int size, int current, AcwApplicationApi acwApplicationApi);

    /**
     * describe 删除应用API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApi> remove(AcwApplicationApi acwApplicationApi);

}