package com.wu.smart.acw.client.nocode.provider.model.interface_.in.param;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 接口输入参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface InterfaceInParamRepository {


    /**
     * describe 新增接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceInParam> story(InterfaceInParam interfaceInParam);

    /**
     * describe 批量新增接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceInParam> batchStory(List<InterfaceInParam> interfaceInParamList);

    /**
     * describe 查询单个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceInParam> findOne(InterfaceInParam interfaceInParam);

    /**
     * describe 查询多个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<List<InterfaceInParam>> findList(InterfaceInParam interfaceInParam);

    /**
     * describe 分页查询多个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<LazyPage<InterfaceInParam>> findPage(int size,int current,InterfaceInParam interfaceInParam);

    /**
     * describe 删除接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceInParam> remove(InterfaceInParam interfaceInParam);

}