package com.wu.smart.acw.server.domain.model.model.application.api.param;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe api参数 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface ApplicationApiParamRepository {


    /**
     * describe 新增api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<ApplicationApiParam> story(ApplicationApiParam applicationApiParam);

    /**
     * describe 批量新增api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<ApplicationApiParam> batchStory(List<ApplicationApiParam> applicationApiParamList);

    /**
     * describe 查询单个api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<ApplicationApiParam> findOne(ApplicationApiParam applicationApiParam);

    /**
     * describe 查询多个api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<ApplicationApiParam>> findList(ApplicationApiParam applicationApiParam);

    /**
     * describe 分页查询多个api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<ApplicationApiParam>> findPage(int size, int current, ApplicationApiParam applicationApiParam);

    /**
     * describe 删除api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<ApplicationApiParam> remove(ApplicationApiParam applicationApiParam);

}