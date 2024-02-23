package com.wu.smart.acw.client.nocode.provider.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.client.nocode.provider.application.command.DerivativeCodeCommand;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoCommand;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoSaveCommand;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoSaveSQLCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.info.InterfaceInfo;

import java.util.List;

/**
 * describe Dataway 中的API 
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface InterfaceInfoApplication {


    /**
     * describe 新增Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceInfo> story(InterfaceInfoSaveCommand interfaceInfoSaveCommand);

    /**
     * 根据sql 创建 api
     *
     * @param interfaceInfoSaveSQLCommand
     * @return
     */
    Result<InterfaceInfo> storyWithSql(InterfaceInfoSaveSQLCommand interfaceInfoSaveSQLCommand);
    /**
     * describe 更新Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceInfo> updateOne(InterfaceInfoCommand interfaceInfoCommand);

    /**
     * describe 查询单个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceInfo> findOne(InterfaceInfoCommand interfaceInfoCommand);

    /**
     * describe 查询多个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result <List<InterfaceInfo>> findList(InterfaceInfoCommand interfaceInfoCommand);

    /**
     * describe 分页查询多个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result <LazyPage<InterfaceInfo>> findPage(int size, int current, InterfaceInfoCommand interfaceInfoCommand);

    /**
     * describe 删除Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    Result<InterfaceInfo> remove(InterfaceInfoCommand interfaceInfoCommand);

    /**
     * 代码衍生
     *
     * @param derivativeCodeCommand 代码衍生参数
     * @return
     */
    Result derivativeCode(DerivativeCodeCommand derivativeCodeCommand);
}