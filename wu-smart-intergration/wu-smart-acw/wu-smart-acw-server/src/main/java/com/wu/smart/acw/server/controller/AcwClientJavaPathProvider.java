package com.wu.smart.acw.server.controller;

import com.wu.smart.acw.server.application.command.acw.client.java.path.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.Resource;
import com.wu.smart.acw.server.domain.model.acw.client.java.path.AcwClientJavaPath;
import com.wu.smart.acw.server.application.AcwClientJavaPathApplication;
import com.wu.smart.acw.server.application.dto.AcwClientJavaPathDTO;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 客户端使用创建Java代码常用路径
 *
 * @author Jia wei Wu
 * @date 2023/12/08 05:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "客户端使用创建Java代码常用路径提供者")
@EasyController("/acw/client/java/path")
public class AcwClientJavaPathProvider  {

    @Resource
    private AcwClientJavaPathApplication acwClientJavaPathApplication;

    /**
     * describe 新增客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathStoryCommand 新增客户端使用创建Java代码常用路径
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径新增后领域对象

     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    @Operation(summary = "新增客户端使用创建Java代码常用路径")
    @PostMapping("/story")
    public Result<AcwClientJavaPath> story(@RequestBody AcwClientJavaPathStoryCommand acwClientJavaPathStoryCommand){
        return acwClientJavaPathApplication.story(acwClientJavaPathStoryCommand);
    }
    /**
     * describe 批量新增客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathStoryCommandList 批量新增客户端使用创建Java代码常用路径
     * @return {@link Result<List<AcwClientJavaPath>>} 客户端使用创建Java代码常用路径新增后领域对象集合

     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    @Operation(summary = "批量新增客户端使用创建Java代码常用路径")
    @PostMapping("/batchStory")
    public Result<List<AcwClientJavaPath>> batchStory(@RequestBody List<AcwClientJavaPathStoryCommand> acwClientJavaPathStoryCommandList){
        return acwClientJavaPathApplication.batchStory(acwClientJavaPathStoryCommandList);
    }
    /**
     * describe 更新客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathUpdateCommand 更新客户端使用创建Java代码常用路径
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径领域对象

     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    @Operation(summary = "更新客户端使用创建Java代码常用路径")
    @PutMapping("/updateOne")
    public Result<AcwClientJavaPath> updateOne(@RequestBody AcwClientJavaPathUpdateCommand acwClientJavaPathUpdateCommand){
        return acwClientJavaPathApplication.updateOne(acwClientJavaPathUpdateCommand);
    }
    /**
     * describe 查询单个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathQueryOneCommand 查询单个客户端使用创建Java代码常用路径
     * @return {@link Result<AcwClientJavaPathDTO>} 客户端使用创建Java代码常用路径DTO对象

     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    @Operation(summary = "查询单个客户端使用创建Java代码常用路径")
    @GetMapping("/findOne")
    public Result<AcwClientJavaPathDTO> findOne(@ModelAttribute AcwClientJavaPathQueryOneCommand acwClientJavaPathQueryOneCommand){
        return acwClientJavaPathApplication.findOne(acwClientJavaPathQueryOneCommand);
    }
    /**
     * describe 查询多个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathQueryListCommand 查询多个客户端使用创建Java代码常用路径
     * @return {@link Result<List<AcwClientJavaPathDTO>>} 客户端使用创建Java代码常用路径DTO对象

     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    @Operation(summary = "查询多个客户端使用创建Java代码常用路径")
    @GetMapping("/findList")
    public Result<List<AcwClientJavaPathDTO>> findList(@ModelAttribute AcwClientJavaPathQueryListCommand acwClientJavaPathQueryListCommand){
        return acwClientJavaPathApplication.findList(acwClientJavaPathQueryListCommand);
    }
    /**
     * describe 分页查询多个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathQueryListCommand 分页查询多个客户端使用创建Java代码常用路径
     * @return {@link Result<LazyPage<AcwClientJavaPathDTO>>} 分页客户端使用创建Java代码常用路径DTO对象

     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    @Operation(summary = "分页查询多个客户端使用创建Java代码常用路径")
    @GetMapping("/findPage")
    public Result<LazyPage<AcwClientJavaPathDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute AcwClientJavaPathQueryListCommand acwClientJavaPathQueryListCommand){
        return acwClientJavaPathApplication.findPage(size,current,acwClientJavaPathQueryListCommand);
    }
    /**
     * describe 删除客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathRemoveCommand 删除客户端使用创建Java代码常用路径
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径

     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    @Operation(summary = "删除客户端使用创建Java代码常用路径")
    @DeleteMapping("/remove")
    public Result<AcwClientJavaPath> remove(@ModelAttribute AcwClientJavaPathRemoveCommand acwClientJavaPathRemoveCommand){
        return acwClientJavaPathApplication.remove(acwClientJavaPathRemoveCommand);
    }
    /**
     * 根据表生成本地Java对应代码
     *
     * @return 返回结果
     */
    @Operation(summary="根据表生成本地Java对应代码")
    @PostMapping("/generate/local/java")
    public Result<?> clientGenerateLocalJava(@RequestBody AcwClientGenerateLocalJavaCommand acwClientGenerateLocalJavaCommand) {
        return acwClientJavaPathApplication.clientGenerateLocalJava(acwClientGenerateLocalJavaCommand);
    }
}