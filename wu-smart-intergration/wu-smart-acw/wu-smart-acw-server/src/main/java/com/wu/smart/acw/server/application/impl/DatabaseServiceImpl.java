package com.wu.smart.acw.server.application.impl;

import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.server.application.DatabaseApplication;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/29 22:58
 */
@Service
public class DatabaseServiceImpl implements DatabaseApplication {

    private final LazyLambdaStream lambdaStream;

    public DatabaseServiceImpl(LazyLambdaStream lambdaStream) {
        this.lambdaStream = lambdaStream;
    }

    /**
     * describe 查询数据库表
     *
     * @param instanceId 数据库服务器id
     * @param schema             数据库名称
     * @return
     * @author Jia wei Wu
     * @date 2022/1/29 22:57
     **/
    @Override
    public Result listTable(String instanceId, String schema) {
        final AcwInstanceUo acwInstanceUo =
                lambdaStream.of(AcwInstanceUo.class)
                        .selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                                .eq(AcwInstanceUo::getId, instanceId));
        final ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);

        final Collection collection = lambdaStream.of(LazyTableInfo.class).
                selectList(LazyWrappers.<LazyTableInfo>lambdaWrapper().eq(LazyTableInfo::getTableSchema, schema));
        DynamicLazyDSContextHolder.poll();
        return ResultFactory.successOf(collection);
    }

    /**
     * 查看表字段
     *
     * @param instanceId 数据库服务器id
     * @param schema             数据库名称
     * @param tableName          数据库表
     * @return
     */
    @Override
    public Result listLazyColumn(String instanceId, String schema, String tableName) {

        final AcwInstanceUo acwInstanceUo =
                lambdaStream.of(AcwInstanceUo.class)
                        .selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                                .eq(AcwInstanceUo::getId, instanceId));
        final ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);

        final Collection collection = lambdaStream.of(LazyColumn.class).selectList(LazyWrappers.<LazyColumn>lambdaWrapper()
                .eq(LazyColumn::getTableSchema, schema)
                .eq(LazyColumn::getTableName, tableName));
        DynamicLazyDSContextHolder.poll();
        return ResultFactory.successOf(collection);
    }
}
