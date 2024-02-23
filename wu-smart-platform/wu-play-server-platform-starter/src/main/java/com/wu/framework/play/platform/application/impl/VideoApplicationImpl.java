package com.wu.framework.play.platform.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.play.platform.application.VideoApplication;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.play.platform.domain.model.video.Video;
import com.wu.framework.play.platform.application.command.video.VideoRemoveCommand;
import com.wu.framework.play.platform.application.command.video.VideoStoryCommand;
import com.wu.framework.play.platform.application.command.video.VideoUpdateCommand;
import com.wu.framework.play.platform.application.command.video.VideoQueryListCommand;
import com.wu.framework.play.platform.application.command.video.VideoQueryOneCommand;
import com.wu.framework.play.platform.application.assembler.VideoDTOAssembler;
import com.wu.framework.play.platform.application.dto.VideoDTO;
import java.util.stream.Collectors;
import jakarta.annotation.Resource;
import com.wu.framework.play.platform.domain.model.video.VideoRepository;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe video 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class VideoApplicationImpl implements VideoApplication {

    @Resource
    VideoRepository videoRepository;
    /**
     * describe 新增
     *
     * @param videoStoryCommand 新增     
     * @return {@link Result<Video>} 新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Video> story(VideoStoryCommand videoStoryCommand) {
        Video video = VideoDTOAssembler.INSTANCE.toVideo(videoStoryCommand);
        return videoRepository.story(video);
    }
    /**
     * describe 批量新增
     *
     * @param videoStoryCommandList 批量新增     
     * @return {@link Result<List<Video>>} 新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<Video>> batchStory(List<VideoStoryCommand> videoStoryCommandList) {
        List<Video> videoList = videoStoryCommandList.stream().map( VideoDTOAssembler.INSTANCE::toVideo).collect(Collectors.toList());
        return videoRepository.batchStory(videoList);
    }
    /**
     * describe 更新
     *
     * @param videoUpdateCommand 更新     
     * @return {@link Result<Video>} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Video> updateOne(VideoUpdateCommand videoUpdateCommand) {
        Video video = VideoDTOAssembler.INSTANCE.toVideo(videoUpdateCommand);
        return videoRepository.story(video);
    }

    /**
     * describe 查询单个
     *
     * @param videoQueryOneCommand 查询单个     
     * @return {@link Result<VideoDTO>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<VideoDTO> findOne(VideoQueryOneCommand videoQueryOneCommand) {
        Video video = VideoDTOAssembler.INSTANCE.toVideo(videoQueryOneCommand);
        return videoRepository.findOne(video).convert(VideoDTOAssembler.INSTANCE::fromVideo);
    }

    /**
     * describe 查询多个
     *
     * @param videoQueryListCommand 查询多个     
     * @return {@link Result<List<VideoDTO>>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<VideoDTO>> findList(VideoQueryListCommand videoQueryListCommand) {
        Video video = VideoDTOAssembler.INSTANCE.toVideo(videoQueryListCommand);
        return videoRepository.findList(video)        .convert(videos -> videos.stream().map(VideoDTOAssembler.INSTANCE::fromVideo).collect(Collectors.toList())) ;
    }

    /**
     * describe 分页查询多个
     *
     * @param videoQueryListCommand 分页查询多个     
     * @return {@link Result<LazyPage<VideoDTO>>} 分页DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<LazyPage<VideoDTO>> findPage(int size,int current,VideoQueryListCommand videoQueryListCommand) {
        Video video = VideoDTOAssembler.INSTANCE.toVideo(videoQueryListCommand);
        return videoRepository.findPage(size,current,video)        .convert(page -> page.convert(VideoDTOAssembler.INSTANCE::fromVideo))            ;
    }

    /**
     * describe 删除
     *
     * @param videoRemoveCommand 删除     
     * @return {@link Result<Video>}      
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Video> remove(VideoRemoveCommand videoRemoveCommand) {
     Video video = VideoDTOAssembler.INSTANCE.toVideo(videoRemoveCommand);
     return videoRepository.remove(video);
    }

}