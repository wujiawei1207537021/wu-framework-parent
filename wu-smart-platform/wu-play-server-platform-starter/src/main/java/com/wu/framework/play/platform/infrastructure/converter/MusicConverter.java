package com.wu.framework.play.platform.infrastructure.converter;

import com.wu.framework.play.platform.domain.model.music.Music;
import com.wu.framework.play.platform.infrastructure.entity.MusicDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe 音乐 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface MusicConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    MusicConverter INSTANCE = Mappers.getMapper(MusicConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param musicDO 音乐实体对象     
     * @return {@link Music} 音乐领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    Music toMusic(MusicDO musicDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param music 音乐领域对象     
     * @return {@link MusicDO} 音乐实体对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     MusicDO fromMusic(Music music); 
}