package com.wu.framework.authorization.platform.provider;


import com.wu.framework.authorization.platform.domain.Role;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import io.swagger.annotations.Api;

/**
 * describe : 角色提供者
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 18:31
 */
@Api(tags = "角色提供者")
@EasyController("/role")
public class RoleProvider extends AbstractLazyCrudProvider<Role, Long> {
    protected RoleProvider(LazyLambdaStream lazyLambdaStream) {
        super(lazyLambdaStream);
    }
}
