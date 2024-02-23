package com.wu.framework.authorization.platform.provider;


import com.wu.framework.authorization.platform.domain.DictionaryData;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import io.swagger.annotations.Api;

/**
 * describe: 字典数据提供者
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/30 18:31
 */
@Api(tags = "字典数据提供者")
@EasyController("/dictionary/data")
public class DictionaryDataProvider extends AbstractLazyCrudProvider<DictionaryData, Long> {
    protected DictionaryDataProvider(LazyLambdaStream lazyLambdaStream) {
        super(lazyLambdaStream);
    }


}
