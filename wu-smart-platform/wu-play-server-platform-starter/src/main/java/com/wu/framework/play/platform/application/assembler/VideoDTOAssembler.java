package com.wu.framework.play.platform.application.assembler;

import com.wu.framework.play.platform.domain.model.video.Video;
import com.wu.framework.play.platform.application.command.video.VideoRemoveCommand;
import com.wu.framework.play.platform.application.command.video.VideoStoryCommand;
import com.wu.framework.play.platform.application.command.video.VideoUpdateCommand;
import com.wu.framework.play.platform.application.command.video.VideoQueryListCommand;
import com.wu.framework.play.platform.application.command.video.VideoQueryOneCommand;
import com.wu.framework.play.platform.application.dto.VideoDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe video 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface VideoDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    VideoDTOAssembler INSTANCE = Mappers.getMapper(VideoDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param videoStoryCommand 保存对象     
     * @return {@link Video} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Video toVideo(VideoStoryCommand videoStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param videoUpdateCommand 更新对象     
     * @return {@link Video} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Video toVideo(VideoUpdateCommand videoUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param videoQueryOneCommand 查询单个对象参数     
     * @return {@link Video} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Video toVideo(VideoQueryOneCommand videoQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param videoQueryListCommand 查询集合对象参数     
     * @return {@link Video} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Video toVideo(VideoQueryListCommand videoQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param videoRemoveCommand 删除对象参数     
     * @return {@link Video} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     Video toVideo(VideoRemoveCommand videoRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param video 领域对象     
     * @return {@link VideoDTO} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     VideoDTO fromVideo(Video video);
}