package com.wu.smart.acw.client.nocode.provider.model.interface_.out.param;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 接口输出参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface InterfaceOutParamRepository {


    /**
     * describe 新增接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceOutParam> story(InterfaceOutParam interfaceOutParam);

    /**
     * describe 批量新增接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceOutParam> batchStory(List<InterfaceOutParam> interfaceOutParamList);

    /**
     * describe 查询单个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceOutParam> findOne(InterfaceOutParam interfaceOutParam);

    /**
     * describe 查询多个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<List<InterfaceOutParam>> findList(InterfaceOutParam interfaceOutParam);

    /**
     * describe 分页查询多个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<LazyPage<InterfaceOutParam>> findPage(int size,int current,InterfaceOutParam interfaceOutParam);

    /**
     * describe 删除接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    Result<InterfaceOutParam> remove(InterfaceOutParam interfaceOutParam);

}