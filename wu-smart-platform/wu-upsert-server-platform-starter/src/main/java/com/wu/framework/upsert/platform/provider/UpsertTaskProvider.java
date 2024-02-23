package com.wu.framework.upsert.platform.provider;

import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.framework.upsert.platform.domain.UpsertTask;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * description upsert task 任务管理
 *
 * @author Jia wei Wu
 * @date 2022/2/8$ 9:41 下午$
 */
@Tag(name = "upsert task 任务管理 提供者")
@EasyController("/upsert/task")
public class UpsertTaskProvider extends AbstractLazyCrudProvider<UpsertTask, UpsertTask, Long> {

    protected UpsertTaskProvider(LazyLambdaStream lazyLambdaStream) {
    }


    /**
     * describe  新增
     *
     * @param model 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<UpsertTask> save(UpsertTask model) {
        final String taskName = model.getTaskName();
        final String config = model.getConfig();
        final String type = model.getType();


        return super.save(model);
    }
}

