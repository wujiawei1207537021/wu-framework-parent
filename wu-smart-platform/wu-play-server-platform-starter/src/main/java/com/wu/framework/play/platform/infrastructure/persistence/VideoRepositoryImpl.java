package com.wu.framework.play.platform.infrastructure.persistence;

import com.wu.framework.play.platform.infrastructure.entity.VideoDO;
import com.wu.framework.play.platform.infrastructure.converter.VideoConverter;
import com.wu.framework.play.platform.infrastructure.mapper.VideoMapper;
import com.wu.framework.play.platform.domain.model.video.VideoRepository;
import org.springframework.stereotype.Repository;
import java.util.stream.Collectors;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import com.wu.framework.play.platform.domain.model.video.Video;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe video 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class VideoRepositoryImpl  implements  VideoRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增
     *
     * @param video 新增     
     * @return {@link Result<Video>} 新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Video> story(Video video) {
        VideoDO videoDO = VideoConverter.INSTANCE.fromVideo(video);
        lazyLambdaStream.upsert(videoDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增
     *
     * @param videoList 批量新增     
     * @return {@link Result<List<Video>>} 新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<Video>> batchStory(List<Video> videoList) {
        List<VideoDO> videoDOList = videoList.stream().map(VideoConverter.INSTANCE::fromVideo).collect(Collectors.toList());
        lazyLambdaStream.upsert(videoDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个
     *
     * @param video 查询单个     
     * @return {@link Result<Video>} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Video> findOne(Video video) {
        VideoDO videoDO = VideoConverter.INSTANCE.fromVideo(video);
        Video videoOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(videoDO), Video.class);
        return ResultFactory.successOf(videoOne);
    }

    /**
     * describe 查询多个
     *
     * @param video 查询多个     
     * @return {@link Result<List<Video>>} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<Video>> findList(Video video) {
        VideoDO videoDO = VideoConverter.INSTANCE.fromVideo(video);
        List<Video> videoList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(videoDO), Video.class);
        return ResultFactory.successOf(videoList);
    }

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

    @Override
    public Result<LazyPage<Video>> findPage(int size,int current,Video video) {
        VideoDO videoDO = VideoConverter.INSTANCE.fromVideo(video);
        LazyPage<Video> lazyPage = new LazyPage<>(current,size);
        LazyPage<Video> videoLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(videoDO),lazyPage, Video.class);
        return ResultFactory.successOf(videoLazyPage);
    }

    /**
     * describe 删除
     *
     * @param video 删除     
     * @return {@link Result<Video>}      
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Video> remove(Video video) {
        VideoDO videoDO = VideoConverter.INSTANCE.fromVideo(video);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(videoDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在
     *
     * @param video 领域对象     
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Boolean> exists(Video video) {
        VideoDO videoDO = VideoConverter.INSTANCE.fromVideo(video);
        Boolean exists=lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(videoDO));
        return ResultFactory.successOf(exists);
    }

}