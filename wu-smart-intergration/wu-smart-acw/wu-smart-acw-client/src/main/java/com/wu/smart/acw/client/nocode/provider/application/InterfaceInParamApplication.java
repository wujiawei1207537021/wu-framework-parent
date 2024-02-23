package com.wu.smart.acw.client.nocode.provider.application;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInParamCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.in.param.InterfaceInParam;

import java.util.List;

/**
 * describe 接口输入参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface InterfaceInParamApplication {


    /**
     * describe 新增接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceInParam> story(InterfaceInParamCommand interfaceInParamCommand);

    /**
     * describe 更新接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceInParam> updateOne(InterfaceInParamCommand interfaceInParamCommand);

    /**
     * describe 查询单个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceInParam> findOne(InterfaceInParamCommand interfaceInParamCommand);

    /**
     * describe 查询多个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result <List<InterfaceInParam>> findList(InterfaceInParamCommand interfaceInParamCommand);

    /**
     * describe 分页查询多个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result <LazyPage<InterfaceInParam>> findPage(int size, int current, InterfaceInParamCommand interfaceInParamCommand);

    /**
     * describe 删除接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceInParam> remove(InterfaceInParamCommand interfaceInParamCommand);

}