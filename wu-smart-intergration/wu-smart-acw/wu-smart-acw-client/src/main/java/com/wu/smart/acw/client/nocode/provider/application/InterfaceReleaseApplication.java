package com.wu.smart.acw.client.nocode.provider.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceReleaseCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.release.InterfaceRelease;

import java.util.List;

/**
 * describe Dataway API 发布历史。 
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface InterfaceReleaseApplication {


    /**
     * describe 新增Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceRelease> story(InterfaceReleaseCommand interfaceReleaseCommand);

    /**
     * describe 更新Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceRelease> updateOne(InterfaceReleaseCommand interfaceReleaseCommand);

    /**
     * describe 查询单个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceRelease> findOne(InterfaceReleaseCommand interfaceReleaseCommand);

    /**
     * describe 查询多个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result <List<InterfaceRelease>> findList(InterfaceReleaseCommand interfaceReleaseCommand);

    /**
     * describe 分页查询多个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result <LazyPage<InterfaceRelease>> findPage(int size, int current, InterfaceReleaseCommand interfaceReleaseCommand);

    /**
     * describe 删除Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceRelease> remove(InterfaceReleaseCommand interfaceReleaseCommand);

}