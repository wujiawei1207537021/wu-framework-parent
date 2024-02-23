package com.wu.smart.acw.client.nocode.provider.model.interface_.release;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe Dataway API 发布历史。
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository
 **/

public interface InterfaceReleaseRepository {


    /**
     * describe 新增Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceRelease> story(InterfaceRelease interfaceRelease);

    /**
     * describe 批量新增Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceRelease> batchStory(List<InterfaceRelease> interfaceReleaseList);

    /**
     * describe 查询单个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceRelease> findOne(InterfaceRelease interfaceRelease);

    /**
     * describe 查询多个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<List<InterfaceRelease>> findList(InterfaceRelease interfaceRelease);

    /**
     * describe 分页查询多个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<LazyPage<InterfaceRelease>> findPage(int size, int current, InterfaceRelease interfaceRelease);

    /**
     * describe 删除Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceRelease> remove(InterfaceRelease interfaceRelease);

}