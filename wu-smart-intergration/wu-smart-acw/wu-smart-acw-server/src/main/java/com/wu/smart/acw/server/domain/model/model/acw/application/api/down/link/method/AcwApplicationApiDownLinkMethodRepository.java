package com.wu.smart.acw.server.domain.model.model.acw.application.api.down.link.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe ACW API 下联 Method 表 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwApplicationApiDownLinkMethodRepository {


    /**
     * describe 新增ACW API 下联 Method 表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiDownLinkMethod> story(AcwApplicationApiDownLinkMethod acwApplicationApiDownLinkMethod);

    /**
     * describe 批量新增ACW API 下联 Method 表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiDownLinkMethod> batchStory(List<AcwApplicationApiDownLinkMethod> acwApplicationApiDownLinkMethodList);

    /**
     * describe 查询单个ACW API 下联 Method 表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiDownLinkMethod> findOne(AcwApplicationApiDownLinkMethod acwApplicationApiDownLinkMethod);

    /**
     * describe 查询多个ACW API 下联 Method 表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwApplicationApiDownLinkMethod>> findList(AcwApplicationApiDownLinkMethod acwApplicationApiDownLinkMethod);

    /**
     * describe 分页查询多个ACW API 下联 Method 表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwApplicationApiDownLinkMethod>> findPage(int size, int current, AcwApplicationApiDownLinkMethod acwApplicationApiDownLinkMethod);

    /**
     * describe 删除ACW API 下联 Method 表
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiDownLinkMethod> remove(AcwApplicationApiDownLinkMethod acwApplicationApiDownLinkMethod);

}