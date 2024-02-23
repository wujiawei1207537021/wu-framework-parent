package com.wu.smart.acw.client.nocode.provider.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.framework.response.mark.ValidType;
import com.wu.smart.acw.client.nocode.config.constant.BaseUrlConstant;
import com.wu.smart.acw.client.nocode.provider.application.InterfaceInfoApplication;
import com.wu.smart.acw.client.nocode.provider.application.command.DerivativeCodeCommand;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoCommand;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoSaveCommand;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoSaveSQLCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.info.InterfaceInfo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe Dataway 中的API
 *
 * @author Jia wei Wu
 * {@code @date} 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@Tag(name = "Dataway 中的API提供者")
@EasyController("/lazy/interface_/info")
public class InterfaceInfoProvider {

    @Autowired
    private InterfaceInfoApplication interfaceInfoApplication;

    /**
     * describe 新增Dataway 中的API
     *
     * @param interfaceInfoSaveCommand 接口保存
     * @return Result<InterfaceInfo>
     * @author Jia wei Wu
     * &#064;date  2023/08/11 05:26 下午
     **/

    @PostMapping("/story")
    public Result<InterfaceInfo> story(@Validated(ValidType.Create.class) @RequestBody InterfaceInfoSaveCommand interfaceInfoSaveCommand) {
        String apiPath = interfaceInfoSaveCommand.getApiPath();
        if(apiPath.startsWith("/")){
            apiPath=apiPath.substring(1);
        }
        interfaceInfoSaveCommand.setApiPath(BaseUrlConstant.api_url + apiPath);
        return interfaceInfoApplication.story(interfaceInfoSaveCommand);
    }


    /**
     * 根据sql 创建 api
     *
     * @param interfaceInfoSaveSQLCommand 接口保存
     * @return
     */
    @PostMapping("/story-sql")
    public Result<InterfaceInfo> storyWithSql(@Validated(ValidType.Create.class) @RequestBody InterfaceInfoSaveSQLCommand interfaceInfoSaveSQLCommand) {
        String apiPath = interfaceInfoSaveSQLCommand.getApiPath();
        if(apiPath.startsWith("/")){
            apiPath=apiPath.substring(1);
        }
        interfaceInfoSaveSQLCommand.setApiPath(BaseUrlConstant.api_url + apiPath);
        return interfaceInfoApplication.storyWithSql(interfaceInfoSaveSQLCommand);
    }


    /**
     * describe 更新Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @PutMapping("/updateOne")
    public Result<InterfaceInfo> updateOne(@RequestBody InterfaceInfoCommand interfaceInfoCommand) {
        return interfaceInfoApplication.updateOne(interfaceInfoCommand);
    }

    /**
     * describe 查询单个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @GetMapping("/findOne")
    public Result<InterfaceInfo> findOne(@ModelAttribute InterfaceInfoCommand interfaceInfoCommand) {
        return interfaceInfoApplication.findOne(interfaceInfoCommand);
    }

    /**
     * describe 查询多个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @GetMapping("/findList")
    public Result<List<InterfaceInfo>> findList(@ModelAttribute InterfaceInfoCommand interfaceInfoCommand) {
        return interfaceInfoApplication.findList(interfaceInfoCommand);
    }

    /**
     * describe 分页查询多个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @GetMapping("/findPage")
    public Result<LazyPage<InterfaceInfo>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                    @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute InterfaceInfoCommand interfaceInfoCommand) {
        return interfaceInfoApplication.findPage(size, current, interfaceInfoCommand);
    }

    /**
     * describe 删除Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @DeleteMapping("/remove")
    public Result<InterfaceInfo> remove(@ModelAttribute InterfaceInfoCommand interfaceInfoCommand) {
        return interfaceInfoApplication.remove(interfaceInfoCommand);
    }

    /**
     * 代码衍生
     *
     * @param derivativeCodeCommand  代码衍生接口参数
     */
    @PutMapping("/derivativeCode")
    public Result derivativeCode(@RequestBody DerivativeCodeCommand derivativeCodeCommand) {
        return interfaceInfoApplication.derivativeCode(derivativeCodeCommand);
    }
}