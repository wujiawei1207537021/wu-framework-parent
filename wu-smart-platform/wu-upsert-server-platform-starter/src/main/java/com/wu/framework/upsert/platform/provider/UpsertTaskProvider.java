package com.wu.framework.upsert.platform.provider;

import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.framework.upsert.platform.domain.UpsertTask;
import io.swagger.annotations.Api;

/**
 * description upsert task 任务管理
 *
 * @author Jia wei Wu
 * @date 2022/2/8$ 9:41 下午$
 */
@Api(tags = "upsert task 任务管理 提供者")
@EasyController("/upsert/task")
public class UpsertTaskProvider extends AbstractLazyCrudProvider<UpsertTask, Long> {

    protected UpsertTaskProvider(LazyLambdaStream lazyLambdaStream) {
        super(lazyLambdaStream);
    }


    /**
     * describe  新增
     *
     * @param s 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public <S extends UpsertTask> Result<S> save(S s) {
        final String taskName = s.getTaskName();
        final String config = s.getConfig();
        final String type = s.getType();



        return super.save(s);
    }
}

