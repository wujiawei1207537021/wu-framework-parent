package com.wu.framework.play.platform.provider;

import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.play.platform.domain.VideoUo;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * describe : 视频
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/18 20:45
 */
@Tag(name = "娱乐-视频数据提供者")
//@EasyController("/play/video")
public class VideoProvider extends AbstractLazyCrudProvider<VideoUo,VideoUo, Long> {

    protected VideoProvider(LazyLambdaStream lazyLambdaStream) {
    }

}
