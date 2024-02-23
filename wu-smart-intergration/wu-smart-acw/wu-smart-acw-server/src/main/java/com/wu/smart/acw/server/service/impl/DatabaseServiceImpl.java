package com.wu.smart.acw.server.service.impl;

import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.DatabaseServerUo;
import com.wu.smart.acw.server.service.DatabaseService;
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
public class DatabaseServiceImpl implements DatabaseService {

    private final LazyLambdaStream lambdaStream;

    public DatabaseServiceImpl(LazyLambdaStream lambdaStream) {
        this.lambdaStream = lambdaStream;
    }

    /**
     * describe 查询数据库表
     *
     * @param databaseServerId 数据库服务器id
     * @param schema           数据库名称
     * @return
     * @author Jia wei Wu
     * @date 2022/1/29 22:57
     **/
    @Override
    public Result listTable(Long databaseServerId, String schema) {
        final DatabaseServerUo databaseServerUo =
                lambdaStream.of(DatabaseServerUo.class)
                        .select(LazyWrappers.<DatabaseServerUo>lambdaWrapper()
                                .eq(DatabaseServerUo::getId, databaseServerId)).collectOne();
        final ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(databaseServerUo.getName());
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);

        final Collection collection = lambdaStream.of(LazyTableInfo.class).
                select(LazyWrappers.<LazyTableInfo>lambdaWrapper().eq(LazyTableInfo::getTableSchema, schema)).collection();
        DynamicLazyDSContextHolder.poll();
        return ResultFactory.successOf(collection);
    }

    /**
     * 查看表字段
     *
     * @param databaseServerId 数据库服务器id
     * @param schema           数据库名称
     * @param tableName        数据库表
     * @return
     */
    @Override
    public Result listLazyColumn(Long databaseServerId, String schema, String tableName) {

        final DatabaseServerUo databaseServerUo =
                lambdaStream.of(DatabaseServerUo.class)
                        .select(LazyWrappers.<DatabaseServerUo>lambdaWrapper()
                                .eq(DatabaseServerUo::getId, databaseServerId)).collectOne();
        final ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(databaseServerUo.getName());
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);

        final Collection collection = lambdaStream.of(LazyColumn.class).select(LazyWrappers.<LazyColumn>lambdaWrapper().
                        eq(LazyColumn::getTableSchema, schema).
                        eq(LazyColumn::getTableName, tableName)).
                collection();
        DynamicLazyDSContextHolder.poll();
        return ResultFactory.successOf(collection);
    }
}
