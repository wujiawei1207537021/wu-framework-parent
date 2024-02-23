package com.wu.smart.acw.client.nocode.provider.controller;


import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.smart.acw.client.nocode.provider.application.InterfaceInParamApplication;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInParamCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.in.param.InterfaceInParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe 接口输入参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@Tag(name = "接口输入参数提供者")
@EasyController("/lazy/interface_/in/param")
public class InterfaceInParamProvider  {

    @Autowired
    private InterfaceInParamApplication interfaceInParamApplication;

    /**
     * describe 新增接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @PostMapping("/story")
    public Result<InterfaceInParam> story(@RequestBody InterfaceInParamCommand interfaceInParamCommand){    return interfaceInParamApplication.story(interfaceInParamCommand);    }
    /**
     * describe 更新接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @PutMapping("/updateOne")
    public Result<InterfaceInParam> updateOne(@RequestBody InterfaceInParamCommand interfaceInParamCommand){    return interfaceInParamApplication.updateOne(interfaceInParamCommand);    }
    /**
     * describe 查询单个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @GetMapping("/findOne")
    public Result<InterfaceInParam> findOne(@ModelAttribute InterfaceInParamCommand interfaceInParamCommand){    return interfaceInParamApplication.findOne(interfaceInParamCommand);    }
    /**
     * describe 查询多个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @GetMapping("/findList")
    public Result<List<InterfaceInParam>> findList(@ModelAttribute InterfaceInParamCommand interfaceInParamCommand){    return interfaceInParamApplication.findList(interfaceInParamCommand);    }
    /**
     * describe 分页查询多个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @GetMapping("/findPage")
    public Result<LazyPage<InterfaceInParam>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                       @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute InterfaceInParamCommand interfaceInParamCommand){    return interfaceInParamApplication.findPage(size,current,interfaceInParamCommand);    }
    /**
     * describe 删除接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @DeleteMapping("/remove")
    public Result<InterfaceInParam> remove(@ModelAttribute InterfaceInParamCommand interfaceInParamCommand){    return interfaceInParamApplication.remove(interfaceInParamCommand);    }
}