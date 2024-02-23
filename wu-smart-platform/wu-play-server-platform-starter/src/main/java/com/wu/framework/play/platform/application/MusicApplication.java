package com.wu.framework.play.platform.application;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.play.platform.domain.model.music.Music;
import com.wu.framework.play.platform.application.command.music.MusicRemoveCommand;
import com.wu.framework.play.platform.application.command.music.MusicStoryCommand;
import com.wu.framework.play.platform.application.command.music.MusicUpdateCommand;
import com.wu.framework.play.platform.application.command.music.MusicQueryListCommand;
import com.wu.framework.play.platform.application.command.music.MusicQueryOneCommand;
import com.wu.framework.play.platform.application.dto.MusicDTO;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 音乐 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface MusicApplication {


    /**
     * describe 新增音乐
     *
     * @param musicStoryCommand 新增音乐     
     * @return {@link Result<Music>} 音乐新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Music> story(MusicStoryCommand musicStoryCommand);

    /**
     * describe 批量新增音乐
     *
     * @param musicStoryCommandList 批量新增音乐     
     * @return {@link Result<List<Music>>} 音乐新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<Music>> batchStory(List<MusicStoryCommand> musicStoryCommandList);

    /**
     * describe 更新音乐
     *
     * @param musicUpdateCommand 更新音乐     
     * @return {@link Result<Music>} 音乐领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Music> updateOne(MusicUpdateCommand musicUpdateCommand);

    /**
     * describe 查询单个音乐
     *
     * @param musicQueryOneCommand 查询单个音乐     
     * @return {@link Result<MusicDTO>} 音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<MusicDTO> findOne(MusicQueryOneCommand musicQueryOneCommand);

    /**
     * describe 查询多个音乐
     *
     * @param musicQueryListCommand 查询多个音乐     
     * @return {@link Result <List<MusicDTO>>} 音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result <List<MusicDTO>> findList(MusicQueryListCommand musicQueryListCommand);

    /**
     * describe 分页查询多个音乐
     *
     * @param musicQueryListCommand 分页查询多个音乐     
     * @return {@link Result <LazyPage<MusicDTO>>} 分页音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result <LazyPage<MusicDTO>> findPage(int size,int current,MusicQueryListCommand musicQueryListCommand);

    /**
     * describe 删除音乐
     *
     * @param musicRemoveCommand 删除音乐     
     * @return {@link Result<Music>} 音乐     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Music> remove(MusicRemoveCommand musicRemoveCommand);

}