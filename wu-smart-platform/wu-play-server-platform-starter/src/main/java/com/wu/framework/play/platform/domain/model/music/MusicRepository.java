package com.wu.framework.play.platform.domain.model.music;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.play.platform.domain.model.music.Music;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 音乐 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface MusicRepository {


    /**
     * describe 新增音乐
     *
     * @param music 新增音乐     
     * @return {@link  Result<Music>} 音乐新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Music> story(Music music);

    /**
     * describe 批量新增音乐
     *
     * @param musicList 批量新增音乐     
     * @return {@link Result<List<Music>>} 音乐新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<Music>> batchStory(List<Music> musicList);

    /**
     * describe 查询单个音乐
     *
     * @param music 查询单个音乐     
     * @return {@link Result<Music>} 音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Music> findOne(Music music);

    /**
     * describe 查询多个音乐
     *
     * @param music 查询多个音乐     
     * @return {@link Result<List<Music>>} 音乐DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<Music>> findList(Music music);

    /**
     * describe 分页查询多个音乐
     *
     * @param size 当前页数
     * @param current 当前页
     * @param music 分页查询多个音乐     
     * @return {@link Result<LazyPage<Music>>} 分页音乐领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<LazyPage<Music>> findPage(int size,int current,Music music);

    /**
     * describe 删除音乐
     *
     * @param music 删除音乐     
     * @return {@link Result<Music>} 音乐     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Music> remove(Music music);

    /**
     * describe 是否存在音乐
     *
     * @param music 是否存在音乐     
     * @return {@link Result<Boolean>} 音乐是否存在     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Boolean> exists(Music music);

}