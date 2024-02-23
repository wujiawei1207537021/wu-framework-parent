package com.wu.smart.acw.client.nocode.provider.model.interface_.info;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe Dataway 中的API 
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface InterfaceInfoRepository {


    /**
     * describe 新增Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceInfo> story(InterfaceInfo interfaceInfo);

    /**
     * describe 批量新增Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceInfo> batchStory(List<InterfaceInfo> interfaceInfoList);

    /**
     * describe 查询单个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceInfo> findOne(InterfaceInfo interfaceInfo);

    /**
     * describe 查询多个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<List<InterfaceInfo>> findList(InterfaceInfo interfaceInfo);

    /**
     * describe 分页查询多个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<LazyPage<InterfaceInfo>> findPage(int size, int current, InterfaceInfo interfaceInfo);

    /**
     * describe 删除Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceInfo> remove(InterfaceInfo interfaceInfo);

    /**
     * 根据api路径获取api信息
     *
     * @param apiPath
     * @return
     */
    InterfaceInfo findOneByApiPath(String apiPath, String apiMethod);
}