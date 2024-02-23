package com.wu.smart.acw.server.service.impl;

import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.dynamic.DynamicAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.DatabaseServerUo;
import com.wu.smart.acw.server.service.DatabaseServerService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/29 22:21
 */
@Service
public class DatabaseServerServiceImpl implements DatabaseServerService {
    private final LazyLambdaStream lazyLambdaStream;
    private final DynamicAdapter dynamicAdapter;

    public DatabaseServerServiceImpl(LazyLambdaStream lazyLambdaStream, DynamicAdapter dynamicAdapter) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.dynamicAdapter = dynamicAdapter;
    }

    /**
     * 查询出所有的数据服务器
     *
     * @param databaseServerUo
     * @return
     */
    @Override
    public Result<List<DatabaseServerUo>> list(DatabaseServerUo databaseServerUo) {
        final Collection<DatabaseServerUo> databaseServerUos = lazyLambdaStream.of(DatabaseServerUo.class).select(LazyWrappers.lambdaWrapper()).collection();
        return ResultFactory.successOf(databaseServerUos);
    }

    @Override
    public Result save(DatabaseServerUo databaseServerUo) {
        lazyLambdaStream.smartUpsert(databaseServerUo);
        final LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(databaseServerUo.getUrl());
        lazyDataSourceProperties.setUsername(databaseServerUo.getUsername());
        lazyDataSourceProperties.setPassword(databaseServerUo.getPassword());
        lazyDataSourceProperties.setAlias(databaseServerUo.getName());
        dynamicAdapter.loading(lazyDataSourceProperties);
        return ResultFactory.successOf();
    }

    @Override
    public Result delete(Long id) {
        Integer count = lazyLambdaStream.of(DatabaseServerUo.class).delete(LazyWrappers.<DatabaseServerUo>lambdaWrapper().eq(DatabaseServerUo::getId, id));
        return ResultFactory.successOf(count);
    }

    /**
     * 重新加载数据源
     *
     * @return
     */
    @Override
    public Result loading() {
        Collection<DatabaseServerUo> databaseServerUos = lazyLambdaStream.of(DatabaseServerUo.class).select(null).collection();
        for (DatabaseServerUo databaseServerUo : databaseServerUos) {
            final LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
            lazyDataSourceProperties.setUrl(databaseServerUo.getUrl());
            lazyDataSourceProperties.setUsername(databaseServerUo.getUsername());
            lazyDataSourceProperties.setPassword(databaseServerUo.getPassword());
            lazyDataSourceProperties.setAlias(databaseServerUo.getName());
            dynamicAdapter.loading(lazyDataSourceProperties);
        }
        return ResultFactory.successOf();
    }


}
