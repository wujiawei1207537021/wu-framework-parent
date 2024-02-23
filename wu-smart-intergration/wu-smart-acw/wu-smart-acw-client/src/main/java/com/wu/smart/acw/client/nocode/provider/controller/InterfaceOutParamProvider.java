package com.wu.smart.acw.client.nocode.provider.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.smart.acw.client.nocode.provider.application.InterfaceOutParamApplication;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceOutParamCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.out.param.InterfaceOutParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * describe 接口输出参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@Tag(name = "接口输出参数提供者")
@EasyController("/lazy/interface_/out/param")
public class InterfaceOutParamProvider  {

    @Autowired
    private InterfaceOutParamApplication interfaceOutParamApplication;

    /**
     * describe 新增接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @PostMapping("/story")
    public Result<InterfaceOutParam> story(@RequestBody InterfaceOutParamCommand interfaceOutParamCommand){    return interfaceOutParamApplication.story(interfaceOutParamCommand);    }
    /**
     * describe 更新接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @PutMapping("/updateOne")
    public Result<InterfaceOutParam> updateOne(@RequestBody InterfaceOutParamCommand interfaceOutParamCommand){    return interfaceOutParamApplication.updateOne(interfaceOutParamCommand);    }
    /**
     * describe 查询单个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @GetMapping("/findOne")
    public Result<InterfaceOutParam> findOne(@ModelAttribute InterfaceOutParamCommand interfaceOutParamCommand){    return interfaceOutParamApplication.findOne(interfaceOutParamCommand);    }
    /**
     * describe 查询多个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @GetMapping("/findList")
    public Result<List<InterfaceOutParam>> findList(@ModelAttribute InterfaceOutParamCommand interfaceOutParamCommand){    return interfaceOutParamApplication.findList(interfaceOutParamCommand);    }
    /**
     * describe 分页查询多个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @GetMapping("/findPage")
    public Result<LazyPage<InterfaceOutParam>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                        @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute InterfaceOutParamCommand interfaceOutParamCommand){    return interfaceOutParamApplication.findPage(size,current,interfaceOutParamCommand);    }
    /**
     * describe 删除接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @DeleteMapping("/remove")
    public Result<InterfaceOutParam> remove(@ModelAttribute InterfaceOutParamCommand interfaceOutParamCommand){    return interfaceOutParamApplication.remove(interfaceOutParamCommand);    }
}