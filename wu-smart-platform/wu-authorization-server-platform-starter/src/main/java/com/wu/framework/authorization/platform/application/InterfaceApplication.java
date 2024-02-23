package com.wu.framework.authorization.platform.application;

import com.wu.framework.authorization.platform.application.command.InterfaceCommand;
import com.wu.framework.authorization.platform.model.interface_.Interface;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe 接口
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface InterfaceApplication {


    /**
     * describe 新增接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Interface> save(InterfaceCommand interface_Command);

    /**
     * describe 更新接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Interface> update(InterfaceCommand interface_Command);

    /**
     * describe 查询单个接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Interface> findOne(InterfaceCommand interface_Command);

    /**
     * describe 查询多个接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<Interface>> findList(InterfaceCommand interface_Command);

    /**
     * describe 删除接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Interface> delete(InterfaceCommand interface_Command);

}