package com.wu.framework.play.platform.application;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.play.platform.domain.model.video.Video;
import com.wu.framework.play.platform.application.command.video.VideoRemoveCommand;
import com.wu.framework.play.platform.application.command.video.VideoStoryCommand;
import com.wu.framework.play.platform.application.command.video.VideoUpdateCommand;
import com.wu.framework.play.platform.application.command.video.VideoQueryListCommand;
import com.wu.framework.play.platform.application.command.video.VideoQueryOneCommand;
import com.wu.framework.play.platform.application.dto.VideoDTO;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe video 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface VideoApplication {


    /**
     * describe 新增
     *
     * @param videoStoryCommand 新增     
     * @return {@link Result<Video>} 新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Video> story(VideoStoryCommand videoStoryCommand);

    /**
     * describe 批量新增
     *
     * @param videoStoryCommandList 批量新增     
     * @return {@link Result<List<Video>>} 新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<Video>> batchStory(List<VideoStoryCommand> videoStoryCommandList);

    /**
     * describe 更新
     *
     * @param videoUpdateCommand 更新     
     * @return {@link Result<Video>} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Video> updateOne(VideoUpdateCommand videoUpdateCommand);

    /**
     * describe 查询单个
     *
     * @param videoQueryOneCommand 查询单个     
     * @return {@link Result<VideoDTO>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<VideoDTO> findOne(VideoQueryOneCommand videoQueryOneCommand);

    /**
     * describe 查询多个
     *
     * @param videoQueryListCommand 查询多个     
     * @return {@link Result <List<VideoDTO>>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result <List<VideoDTO>> findList(VideoQueryListCommand videoQueryListCommand);

    /**
     * describe 分页查询多个
     *
     * @param videoQueryListCommand 分页查询多个     
     * @return {@link Result <LazyPage<VideoDTO>>} 分页DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result <LazyPage<VideoDTO>> findPage(int size,int current,VideoQueryListCommand videoQueryListCommand);

    /**
     * describe 删除
     *
     * @param videoRemoveCommand 删除     
     * @return {@link Result<Video>}      
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Video> remove(VideoRemoveCommand videoRemoveCommand);

}