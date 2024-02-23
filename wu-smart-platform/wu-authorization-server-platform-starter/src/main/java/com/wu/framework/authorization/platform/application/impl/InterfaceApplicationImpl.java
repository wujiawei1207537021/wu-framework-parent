package com.wu.framework.authorization.platform.application.impl;

import com.wu.framework.authorization.platform.application.InterfaceApplication;
import com.wu.framework.authorization.platform.application.assembler.InterfaceDTOAssembler;
import com.wu.framework.authorization.platform.application.command.InterfaceCommand;
import com.wu.framework.authorization.platform.model.interface_.Interface;
import com.wu.framework.authorization.platform.model.interface_.InterfaceRepository;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * describe 接口
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class InterfaceApplicationImpl implements InterfaceApplication {

    @Autowired
    InterfaceRepository interface_Repository;

    /**
     * describe 新增接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Interface> save(InterfaceCommand interface_Command) {
        Interface interface_ = InterfaceDTOAssembler.toInterface(interface_Command);
        return interface_Repository.story(interface_);
    }

    /**
     * describe 更新接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Interface> update(InterfaceCommand interface_Command) {
        Interface interface_ = InterfaceDTOAssembler.toInterface(interface_Command);
        return interface_Repository.story(interface_);
    }

    /**
     * describe 查询单个接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Interface> findOne(InterfaceCommand interface_Command) {
        Interface interface_ = InterfaceDTOAssembler.toInterface(interface_Command);
        return interface_Repository.findOne(interface_);
    }

    /**
     * describe 查询多个接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<List<Interface>> findList(InterfaceCommand interface_Command) {
        Interface interface_ = InterfaceDTOAssembler.toInterface(interface_Command);
        return interface_Repository.findList(interface_);
    }

    /**
     * describe 删除接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Interface> delete(InterfaceCommand interface_Command) {
        Interface interface_ = InterfaceDTOAssembler.toInterface(interface_Command);
        return interface_Repository.delete(interface_);
    }

}