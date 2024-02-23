package com.wu.framework.authorization.platform.controller;

import com.wu.framework.authorization.platform.application.InterfaceApplication;
import com.wu.framework.authorization.platform.application.command.InterfaceCommand;
import com.wu.framework.authorization.platform.infrastructure.entity.InterfaceDO;
import com.wu.framework.authorization.platform.model.interface_.Interface;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe 接口
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "接口提供者")
@EasyController("/interface_")
public class InterfaceProvider extends AbstractLazyCrudProvider<InterfaceDO,InterfaceDO, Long> {

    @Autowired
    private InterfaceApplication interface_Application;

    /**
     * describe 新增接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @PostMapping("/story")
    public Result<Interface> story(@RequestBody InterfaceCommand interface_Command) {
        return interface_Application.save(interface_Command);
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

    @PutMapping("/updateOne")
    public Result<Interface> updateOne(@RequestBody InterfaceCommand interface_Command) {
        return interface_Application.update(interface_Command);
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

    @GetMapping("/findOne")
    public Result<Interface> findOne(@ModelAttribute InterfaceCommand interface_Command) {
        return interface_Application.findOne(interface_Command);
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

    @GetMapping("/findList")
    public Result<List<Interface>> findList(@ModelAttribute InterfaceCommand interface_Command) {
        return interface_Application.findList(interface_Command);
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

    @DeleteMapping("/delete")
    public Result<Interface> delete(@ModelAttribute InterfaceCommand interface_Command) {
        return interface_Application.delete(interface_Command);
    }
}