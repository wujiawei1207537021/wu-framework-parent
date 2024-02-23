package com.wu.framework.authorization.platform.provider;


import com.wu.framework.authorization.model.User;

import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.mark.LazyRepositoryOperation;
import io.swagger.annotations.Api;

/**
 * 用户提供者
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 18:30
 */
@Api(tags = "用户提供者")
@EasyController("/user")
public class UserProvider extends AbstractLazyCrudProvider<User, Long> {
    protected UserProvider(LazyLambdaStream lazyLambdaStream) {
        super(lazyLambdaStream);
    }
}
