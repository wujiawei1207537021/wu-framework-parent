package com.wu.framework.play.platform.application.assembler;

import com.wu.framework.play.platform.domain.model.music.Music;
import com.wu.framework.play.platform.application.command.music.MusicRemoveCommand;
import com.wu.framework.play.platform.application.command.music.MusicStoryCommand;
import com.wu.framework.play.platform.application.command.music.MusicUpdateCommand;
import com.wu.framework.play.platform.application.command.music.MusicQueryListCommand;
import com.wu.framework.play.platform.application.command.music.MusicQueryOneCommand;
import com.wu.framework.play.platform.application.dto.MusicDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe 音乐 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface MusicDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    MusicDTOAssembler INSTANCE = Mappers.getMapper(MusicDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param musicStoryCommand 保存音乐对象     
     * @return {@link Music} 音乐领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Music toMusic(MusicStoryCommand musicStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param musicUpdateCommand 更新音乐对象     
     * @return {@link Music} 音乐领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Music toMusic(MusicUpdateCommand musicUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param musicQueryOneCommand 查询单个音乐对象参数     
     * @return {@link Music} 音乐领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Music toMusic(MusicQueryOneCommand musicQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param musicQueryListCommand 查询集合音乐对象参数     
     * @return {@link Music} 音乐领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Music toMusic(MusicQueryListCommand musicQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param musicRemoveCommand 删除音乐对象参数     
     * @return {@link Music} 音乐领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Music toMusic(MusicRemoveCommand musicRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param music 音乐领域对象     
     * @return {@link MusicDTO} 音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     MusicDTO fromMusic(Music music);
}