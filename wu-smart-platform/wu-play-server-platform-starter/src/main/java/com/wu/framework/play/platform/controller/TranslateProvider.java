package com.wu.framework.play.platform.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.Resource;
import com.wu.framework.play.platform.domain.model.translate.Translate;
import com.wu.framework.play.platform.application.command.translate.TranslateRemoveCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateStoryCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateUpdateCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateQueryListCommand;
import com.wu.framework.play.platform.application.command.translate.TranslateQueryOneCommand;
import com.wu.framework.play.platform.application.TranslateApplication;
import com.wu.framework.play.platform.application.dto.TranslateDTO;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 翻译数据 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "翻译数据提供者")
@EasyController("/play/translate")
public class TranslateProvider  {

    @Resource
    private TranslateApplication translateApplication;

    /**
     * describe 新增翻译数据
     *
     * @param translateStoryCommand 新增翻译数据     
     * @return {@link Result<Translate>} 翻译数据新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "新增翻译数据")
    @PostMapping("/story")
    public Result<Translate> story(@RequestBody TranslateStoryCommand translateStoryCommand){
        return translateApplication.story(translateStoryCommand);
    }
    /**
     * describe 批量新增翻译数据
     *
     * @param translateStoryCommandList 批量新增翻译数据     
     * @return {@link Result<List<Translate>>} 翻译数据新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "批量新增翻译数据")
    @PostMapping("/batchStory")
    public Result<List<Translate>> batchStory(@RequestBody List<TranslateStoryCommand> translateStoryCommandList){
        return translateApplication.batchStory(translateStoryCommandList);
    }
    /**
     * describe 更新翻译数据
     *
     * @param translateUpdateCommand 更新翻译数据     
     * @return {@link Result<Translate>} 翻译数据领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "更新翻译数据")
    @PutMapping("/updateOne")
    public Result<Translate> updateOne(@RequestBody TranslateUpdateCommand translateUpdateCommand){
        return translateApplication.updateOne(translateUpdateCommand);
    }
    /**
     * describe 查询单个翻译数据
     *
     * @param translateQueryOneCommand 查询单个翻译数据     
     * @return {@link Result<TranslateDTO>} 翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "查询单个翻译数据")
    @GetMapping("/findOne")
    public Result<TranslateDTO> findOne(@ModelAttribute TranslateQueryOneCommand translateQueryOneCommand){
        return translateApplication.findOne(translateQueryOneCommand);
    }
    /**
     * describe 查询多个翻译数据
     *
     * @param translateQueryListCommand 查询多个翻译数据     
     * @return {@link Result<List<TranslateDTO>>} 翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "查询多个翻译数据")
    @GetMapping("/findList")
    public Result<List<TranslateDTO>> findList(@ModelAttribute TranslateQueryListCommand translateQueryListCommand){
        return translateApplication.findList(translateQueryListCommand);
    }
    /**
     * describe 分页查询多个翻译数据
     *
     * @param translateQueryListCommand 分页查询多个翻译数据     
     * @return {@link Result<LazyPage<TranslateDTO>>} 分页翻译数据DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "分页查询多个翻译数据")
    @GetMapping("/findPage")
    public Result<LazyPage<TranslateDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute TranslateQueryListCommand translateQueryListCommand){
        return translateApplication.findPage(size,current,translateQueryListCommand);
    }
    /**
     * describe 删除翻译数据
     *
     * @param translateRemoveCommand 删除翻译数据     
     * @return {@link Result<Translate>} 翻译数据     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "删除翻译数据")
    @DeleteMapping("/remove")
    public Result<Translate> remove(@ModelAttribute TranslateRemoveCommand translateRemoveCommand){
        return translateApplication.remove(translateRemoveCommand);
    }
}