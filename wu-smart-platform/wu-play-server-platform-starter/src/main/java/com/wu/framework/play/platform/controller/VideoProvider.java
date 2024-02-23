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
import com.wu.framework.play.platform.domain.model.video.Video;
import com.wu.framework.play.platform.application.command.video.VideoRemoveCommand;
import com.wu.framework.play.platform.application.command.video.VideoStoryCommand;
import com.wu.framework.play.platform.application.command.video.VideoUpdateCommand;
import com.wu.framework.play.platform.application.command.video.VideoQueryListCommand;
import com.wu.framework.play.platform.application.command.video.VideoQueryOneCommand;
import com.wu.framework.play.platform.application.VideoApplication;
import com.wu.framework.play.platform.application.dto.VideoDTO;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe video 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "提供者")
@EasyController("/play/video")
public class VideoProvider  {

    @Resource
    private VideoApplication videoApplication;

    /**
     * describe 新增
     *
     * @param videoStoryCommand 新增     
     * @return {@link Result<Video>} 新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "新增")
    @PostMapping("/story")
    public Result<Video> story(@RequestBody VideoStoryCommand videoStoryCommand){
        return videoApplication.story(videoStoryCommand);
    }
    /**
     * describe 批量新增
     *
     * @param videoStoryCommandList 批量新增     
     * @return {@link Result<List<Video>>} 新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "批量新增")
    @PostMapping("/batchStory")
    public Result<List<Video>> batchStory(@RequestBody List<VideoStoryCommand> videoStoryCommandList){
        return videoApplication.batchStory(videoStoryCommandList);
    }
    /**
     * describe 更新
     *
     * @param videoUpdateCommand 更新     
     * @return {@link Result<Video>} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "更新")
    @PutMapping("/updateOne")
    public Result<Video> updateOne(@RequestBody VideoUpdateCommand videoUpdateCommand){
        return videoApplication.updateOne(videoUpdateCommand);
    }
    /**
     * describe 查询单个
     *
     * @param videoQueryOneCommand 查询单个     
     * @return {@link Result<VideoDTO>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "查询单个")
    @GetMapping("/findOne")
    public Result<VideoDTO> findOne(@ModelAttribute VideoQueryOneCommand videoQueryOneCommand){
        return videoApplication.findOne(videoQueryOneCommand);
    }
    /**
     * describe 查询多个
     *
     * @param videoQueryListCommand 查询多个     
     * @return {@link Result<List<VideoDTO>>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "查询多个")
    @GetMapping("/findList")
    public Result<List<VideoDTO>> findList(@ModelAttribute VideoQueryListCommand videoQueryListCommand){
        return videoApplication.findList(videoQueryListCommand);
    }
    /**
     * describe 分页查询多个
     *
     * @param videoQueryListCommand 分页查询多个     
     * @return {@link Result<LazyPage<VideoDTO>>} 分页DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "分页查询多个")
    @GetMapping("/findPage")
    public Result<LazyPage<VideoDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute VideoQueryListCommand videoQueryListCommand){
        return videoApplication.findPage(size,current,videoQueryListCommand);
    }
    /**
     * describe 删除
     *
     * @param videoRemoveCommand 删除     
     * @return {@link Result<Video>}      
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "删除")
    @DeleteMapping("/remove")
    public Result<Video> remove(@ModelAttribute VideoRemoveCommand videoRemoveCommand){
        return videoApplication.remove(videoRemoveCommand);
    }
}