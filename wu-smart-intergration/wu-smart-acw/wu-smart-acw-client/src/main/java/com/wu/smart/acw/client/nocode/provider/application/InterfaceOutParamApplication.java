package com.wu.smart.acw.client.nocode.provider.application;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceOutParamCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.out.param.InterfaceOutParam;

import java.util.List;

/**
 * describe 接口输出参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface InterfaceOutParamApplication {


    /**
     * describe 新增接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceOutParam> story(InterfaceOutParamCommand interfaceOutParamCommand);

    /**
     * describe 更新接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceOutParam> updateOne(InterfaceOutParamCommand interfaceOutParamCommand);

    /**
     * describe 查询单个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceOutParam> findOne(InterfaceOutParamCommand interfaceOutParamCommand);

    /**
     * describe 查询多个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result <List<InterfaceOutParam>> findList(InterfaceOutParamCommand interfaceOutParamCommand);

    /**
     * describe 分页查询多个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result <LazyPage<InterfaceOutParam>> findPage(int size, int current, InterfaceOutParamCommand interfaceOutParamCommand);

    /**
     * describe 删除接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceOutParam> remove(InterfaceOutParamCommand interfaceOutParamCommand);

}