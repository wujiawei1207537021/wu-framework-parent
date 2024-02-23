package com.wu.framework.play.platform.infrastructure.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.play.platform.domain.model.music.Music;
import com.wu.framework.play.platform.domain.model.music.MusicRepository;
import com.wu.framework.play.platform.infrastructure.converter.MusicConverter;
import com.wu.framework.play.platform.infrastructure.entity.MusicDO;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 音乐
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class MusicRepositoryImpl implements MusicRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增音乐
     *
     * @param music 新增音乐
     * @return {@link Result<Music>} 音乐新增后领域对象
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Music> story(Music music) {
        MusicDO musicDO = MusicConverter.INSTANCE.fromMusic(music);
        lazyLambdaStream.upsert(musicDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增音乐
     *
     * @param musicList 批量新增音乐
     * @return {@link Result<List<Music>>} 音乐新增后领域对象集合
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<Music>> batchStory(List<Music> musicList) {
        List<MusicDO> musicDOList = musicList.stream().map(MusicConverter.INSTANCE::fromMusic).collect(Collectors.toList());
        lazyLambdaStream.upsert(musicDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个音乐
     *
     * @param music 查询单个音乐
     * @return {@link Result<Music>} 音乐领域对象
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Music> findOne(Music music) {
        MusicDO musicDO = MusicConverter.INSTANCE.fromMusic(music);
        Music musicOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(musicDO), Music.class);
        return ResultFactory.successOf(musicOne);
    }

    /**
     * describe 查询多个音乐
     *
     * @param music 查询多个音乐
     * @return {@link Result<List<Music>>} 音乐领域对象
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<Music>> findList(Music music) {
        MusicDO musicDO = MusicConverter.INSTANCE.fromMusic(music);
        List<Music> musicList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(musicDO).ignoreAs(MusicDO::getMusicData), Music.class);
        return ResultFactory.successOf(musicList);
    }

    /**
     * describe 分页查询多个音乐
     *
     * @param size    当前页数
     * @param current 当前页
     * @param music   分页查询多个音乐
     * @return {@link Result<LazyPage<Music>>} 分页音乐领域对象
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<LazyPage<Music>> findPage(int size, int current, Music music) {
        MusicDO musicDO = MusicConverter.INSTANCE.fromMusic(music);
        LazyPage<Music> lazyPage = new LazyPage<>(current, size);
        LazyPage<Music> musicLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(musicDO).ignoreAs(MusicDO::getMusicData), lazyPage, Music.class);
        return ResultFactory.successOf(musicLazyPage);
    }

    /**
     * describe 删除音乐
     *
     * @param music 删除音乐
     * @return {@link Result<Music>} 音乐
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Music> remove(Music music) {
        MusicDO musicDO = MusicConverter.INSTANCE.fromMusic(music);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(musicDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在音乐
     *
     * @param music 音乐领域对象
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Boolean> exists(Music music) {
        MusicDO musicDO = MusicConverter.INSTANCE.fromMusic(music);
        Boolean exists = lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(musicDO));
        return ResultFactory.successOf(exists);
    }

}