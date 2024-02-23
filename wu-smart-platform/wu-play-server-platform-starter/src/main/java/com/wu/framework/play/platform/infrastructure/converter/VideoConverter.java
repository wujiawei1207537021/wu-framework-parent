package com.wu.framework.play.platform.infrastructure.converter;

import com.wu.framework.play.platform.domain.model.video.Video;
import com.wu.framework.play.platform.infrastructure.entity.VideoDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe video 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface VideoConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    VideoConverter INSTANCE = Mappers.getMapper(VideoConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param videoDO 实体对象     
     * @return {@link Video} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    Video toVideo(VideoDO videoDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param video 领域对象     
     * @return {@link VideoDO} 实体对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     VideoDO fromVideo(Video video); 
}