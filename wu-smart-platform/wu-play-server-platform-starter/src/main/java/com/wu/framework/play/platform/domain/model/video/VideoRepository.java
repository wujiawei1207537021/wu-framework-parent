package com.wu.framework.play.platform.domain.model.video;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.play.platform.domain.model.video.Video;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe video 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface VideoRepository {


    /**
     * describe 新增
     *
     * @param video 新增     
     * @return {@link  Result<Video>} 新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Video> story(Video video);

    /**
     * describe 批量新增
     *
     * @param videoList 批量新增     
     * @return {@link Result<List<Video>>} 新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<Video>> batchStory(List<Video> videoList);

    /**
     * describe 查询单个
     *
     * @param video 查询单个     
     * @return {@link Result<Video>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Video> findOne(Video video);

    /**
     * describe 查询多个
     *
     * @param video 查询多个     
     * @return {@link Result<List<Video>>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<Video>> findList(Video video);

    /**
     * describe 分页查询多个
     *
     * @param size 当前页数
     * @param current 当前页
     * @param video 分页查询多个     
     * @return {@link Result<LazyPage<Video>>} 分页领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<LazyPage<Video>> findPage(int size,int current,Video video);

    /**
     * describe 删除
     *
     * @param video 删除     
     * @return {@link Result<Video>}      
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Video> remove(Video video);

    /**
     * describe 是否存在
     *
     * @param video 是否存在     
     * @return {@link Result<Boolean>} 是否存在     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Boolean> exists(Video video);

}