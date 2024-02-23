package com.wu.framework.play.platform.controller;

import com.wu.framework.inner.layer.util.ByteSizeUtil;
import com.wu.framework.inner.layer.util.HttpFileUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import jakarta.annotation.Resource;
import com.wu.framework.play.platform.domain.model.music.Music;
import com.wu.framework.play.platform.application.command.music.MusicRemoveCommand;
import com.wu.framework.play.platform.application.command.music.MusicStoryCommand;
import com.wu.framework.play.platform.application.command.music.MusicUpdateCommand;
import com.wu.framework.play.platform.application.command.music.MusicQueryListCommand;
import com.wu.framework.play.platform.application.command.music.MusicQueryOneCommand;
import com.wu.framework.play.platform.application.MusicApplication;
import com.wu.framework.play.platform.application.dto.MusicDTO;

import java.io.IOException;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import org.springframework.web.multipart.MultipartFile;

/**
 * describe 音乐 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "音乐提供者")
@EasyController("/play/music")
public class MusicProvider  {

    @Resource
    private MusicApplication musicApplication;

    /**
     * describe 新增音乐
     *
     * @param musicData 新增音乐
     * @return {@link Result<Music>} 音乐新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "新增音乐")
    @PostMapping("/story")
    public Result<Music> story(@RequestBody MultipartFile musicData,
                               String name,
                               Long id,
                               String musicUrl,
                               String singer,
                               String album,
                               String duration) throws IOException {
        String contentType = musicData.getContentType();
        byte[] musicDataBytes = musicData.getBytes();
        MusicStoryCommand musicStoryCommand = new MusicStoryCommand();

        musicStoryCommand.setId(id);
        musicStoryCommand.setName(name);
        musicStoryCommand.setMusicUrl(musicUrl);
        musicStoryCommand.setSinger(singer);
        musicStoryCommand.setAlbum(album);
        musicStoryCommand.setDuration(duration);
        musicStoryCommand.setContentType(contentType);
        musicStoryCommand.setRoughlySize(ByteSizeUtil.convertSize(musicDataBytes.length));
        if (ObjectUtils.isEmpty(musicUrl)) {
            // 设置内网地址
            musicStoryCommand.setMusicUrl("/play/music/findOne/");
            musicStoryCommand.setMusicData(musicDataBytes);
        } else {
            byte[] bytes = HttpFileUtil.readStream(musicUrl);
            musicStoryCommand.setMusicData(bytes);
        }

        return musicApplication.story(musicStoryCommand);
    }
    /**
     * describe 批量新增音乐
     *
     * @param musicStoryCommandList 批量新增音乐     
     * @return {@link Result<List<Music>>} 音乐新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "批量新增音乐")
    @PostMapping("/batchStory")
    public Result<List<Music>> batchStory(@RequestBody List<MusicStoryCommand> musicStoryCommandList){
        return musicApplication.batchStory(musicStoryCommandList);
    }
    /**
     * describe 更新音乐
     *
     * @param musicUpdateCommand 更新音乐     
     * @return {@link Result<Music>} 音乐领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "更新音乐")
    @PutMapping("/updateOne")
    public Result<Music> updateOne(@RequestBody MusicUpdateCommand musicUpdateCommand){
        return musicApplication.updateOne(musicUpdateCommand);
    }
    /**
     * describe 查询单个音乐
     *
     * @param musicQueryOneCommand 查询单个音乐     
     * @return {@link Result<MusicDTO>} 音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "查询单个音乐")
    @GetMapping("/findOne")
    public Result<MusicDTO> findOne(@ModelAttribute MusicQueryOneCommand musicQueryOneCommand){
        return musicApplication.findOne(musicQueryOneCommand);
    }

    /**
     * describe 查询单个音乐
     *
     * @param id 查询单个音乐数据
     * @return {@link Result<MusicDTO>} 音乐DTO对象

     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "查询单个音乐数据")
    @GetMapping("/findOne/{id}")
    public byte[] findOne(@PathVariable Long id){
        MusicQueryOneCommand musicQueryOneCommand = new MusicQueryOneCommand();
        musicQueryOneCommand.setId(id);
        return musicApplication.findOne(musicQueryOneCommand).applyOther(MusicDTO::getMusicData);
    }
    /**
     * describe 查询多个音乐
     *
     * @param musicQueryListCommand 查询多个音乐     
     * @return {@link Result<List<MusicDTO>>} 音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "查询多个音乐")
    @GetMapping("/findList")
    public Result<List<MusicDTO>> findList(@ModelAttribute MusicQueryListCommand musicQueryListCommand){
        return musicApplication.findList(musicQueryListCommand);
    }
    /**
     * describe 分页查询多个音乐
     *
     * @param musicQueryListCommand 分页查询多个音乐     
     * @return {@link Result<LazyPage<MusicDTO>>} 分页音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "分页查询多个音乐")
    @GetMapping("/findPage")
    public Result<LazyPage<MusicDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute MusicQueryListCommand musicQueryListCommand){
        return musicApplication.findPage(size,current,musicQueryListCommand);
    }
    /**
     * describe 删除音乐
     *
     * @param musicRemoveCommand 删除音乐     
     * @return {@link Result<Music>} 音乐     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "删除音乐")
    @DeleteMapping("/remove")
    public Result<Music> remove(@ModelAttribute MusicRemoveCommand musicRemoveCommand){
        return musicApplication.remove(musicRemoveCommand);
    }
}