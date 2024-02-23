package com.wu.framework.authorization.platform.provider;


import com.wu.framework.authorization.platform.domain.Menu;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import io.swagger.annotations.Api;

/**
 * describe: 菜单提供者
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/30 18:31
 */
@Api(tags = "菜单提供者")
@EasyController("/menu")
public class MenuProvider extends AbstractLazyCrudProvider<Menu, Long> {
    protected MenuProvider(LazyLambdaStream lazyLambdaStream) {
        super(lazyLambdaStream);
    }


}
