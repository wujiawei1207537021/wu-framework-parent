package com.wu.smart.acw.client.nocode.provider.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.smart.acw.client.nocode.provider.application.InterfaceReleaseApplication;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceReleaseCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.release.InterfaceRelease;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe Dataway API 发布历史。 
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@Tag(name = "Dataway API 发布历史。提供者")
@EasyController("/lazy/interface_/release")
public class InterfaceReleaseProvider  {

    @Autowired
    private InterfaceReleaseApplication interfaceReleaseApplication;

    /**
     * describe 新增Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @PostMapping("/story")
    public Result<InterfaceRelease> story(@RequestBody InterfaceReleaseCommand interfaceReleaseCommand){    return interfaceReleaseApplication.story(interfaceReleaseCommand);    }
    /**
     * describe 更新Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @PutMapping("/updateOne")
    public Result<InterfaceRelease> updateOne(@RequestBody InterfaceReleaseCommand interfaceReleaseCommand){    return interfaceReleaseApplication.updateOne(interfaceReleaseCommand);    }
    /**
     * describe 查询单个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @GetMapping("/findOne")
    public Result<InterfaceRelease> findOne(@ModelAttribute InterfaceReleaseCommand interfaceReleaseCommand){    return interfaceReleaseApplication.findOne(interfaceReleaseCommand);    }
    /**
     * describe 查询多个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @GetMapping("/findList")
    public Result<List<InterfaceRelease>> findList(@ModelAttribute InterfaceReleaseCommand interfaceReleaseCommand){    return interfaceReleaseApplication.findList(interfaceReleaseCommand);    }
    /**
     * describe 分页查询多个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @GetMapping("/findPage")
    public Result<LazyPage<InterfaceRelease>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                       @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute InterfaceReleaseCommand interfaceReleaseCommand){    return interfaceReleaseApplication.findPage(size,current,interfaceReleaseCommand);    }
    /**
     * describe 删除Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @DeleteMapping("/remove")
    public Result<InterfaceRelease> remove(@ModelAttribute InterfaceReleaseCommand interfaceReleaseCommand){    return interfaceReleaseApplication.remove(interfaceReleaseCommand);    }
}