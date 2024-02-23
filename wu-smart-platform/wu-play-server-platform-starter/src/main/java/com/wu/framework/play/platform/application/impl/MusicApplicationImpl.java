package com.wu.framework.play.platform.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.play.platform.application.MusicApplication;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.play.platform.domain.model.music.Music;
import com.wu.framework.play.platform.application.command.music.MusicRemoveCommand;
import com.wu.framework.play.platform.application.command.music.MusicStoryCommand;
import com.wu.framework.play.platform.application.command.music.MusicUpdateCommand;
import com.wu.framework.play.platform.application.command.music.MusicQueryListCommand;
import com.wu.framework.play.platform.application.command.music.MusicQueryOneCommand;
import com.wu.framework.play.platform.application.assembler.MusicDTOAssembler;
import com.wu.framework.play.platform.application.dto.MusicDTO;
import java.util.stream.Collectors;
import jakarta.annotation.Resource;
import com.wu.framework.play.platform.domain.model.music.MusicRepository;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 音乐 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class MusicApplicationImpl implements MusicApplication {

    @Resource
    MusicRepository musicRepository;
    /**
     * describe 新增音乐
     *
     * @param musicStoryCommand 新增音乐     
     * @return {@link Result<Music>} 音乐新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Music> story(MusicStoryCommand musicStoryCommand) {
        Music music = MusicDTOAssembler.INSTANCE.toMusic(musicStoryCommand);
        return musicRepository.story(music);
    }
    /**
     * describe 批量新增音乐
     *
     * @param musicStoryCommandList 批量新增音乐     
     * @return {@link Result<List<Music>>} 音乐新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<Music>> batchStory(List<MusicStoryCommand> musicStoryCommandList) {
        List<Music> musicList = musicStoryCommandList.stream().map( MusicDTOAssembler.INSTANCE::toMusic).collect(Collectors.toList());
        return musicRepository.batchStory(musicList);
    }
    /**
     * describe 更新音乐
     *
     * @param musicUpdateCommand 更新音乐     
     * @return {@link Result<Music>} 音乐领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Music> updateOne(MusicUpdateCommand musicUpdateCommand) {
        Music music = MusicDTOAssembler.INSTANCE.toMusic(musicUpdateCommand);
        return musicRepository.story(music);
    }

    /**
     * describe 查询单个音乐
     *
     * @param musicQueryOneCommand 查询单个音乐     
     * @return {@link Result<MusicDTO>} 音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<MusicDTO> findOne(MusicQueryOneCommand musicQueryOneCommand) {
        Music music = MusicDTOAssembler.INSTANCE.toMusic(musicQueryOneCommand);
        return musicRepository.findOne(music).convert(MusicDTOAssembler.INSTANCE::fromMusic);
    }

    /**
     * describe 查询多个音乐
     *
     * @param musicQueryListCommand 查询多个音乐     
     * @return {@link Result<List<MusicDTO>>} 音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<MusicDTO>> findList(MusicQueryListCommand musicQueryListCommand) {
        Music music = MusicDTOAssembler.INSTANCE.toMusic(musicQueryListCommand);
        return musicRepository.findList(music)        .convert(musics -> musics.stream().map(MusicDTOAssembler.INSTANCE::fromMusic).collect(Collectors.toList())) ;
    }

    /**
     * describe 分页查询多个音乐
     *
     * @param musicQueryListCommand 分页查询多个音乐     
     * @return {@link Result<LazyPage<MusicDTO>>} 分页音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<LazyPage<MusicDTO>> findPage(int size,int current,MusicQueryListCommand musicQueryListCommand) {
        Music music = MusicDTOAssembler.INSTANCE.toMusic(musicQueryListCommand);
        return musicRepository.findPage(size,current,music)        .convert(page -> page.convert(MusicDTOAssembler.INSTANCE::fromMusic))            ;
    }

    /**
     * describe 删除音乐
     *
     * @param musicRemoveCommand 删除音乐     
     * @return {@link Result<Music>} 音乐     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Music> remove(MusicRemoveCommand musicRemoveCommand) {
     Music music = MusicDTOAssembler.INSTANCE.toMusic(musicRemoveCommand);
     return musicRepository.remove(music);
    }

}