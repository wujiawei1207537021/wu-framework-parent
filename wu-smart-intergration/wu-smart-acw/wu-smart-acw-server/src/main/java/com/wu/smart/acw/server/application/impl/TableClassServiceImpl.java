package com.wu.smart.acw.server.application.impl;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaClassType;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.smart.acw.core.domain.uo.AcwClassUo;
import com.wu.smart.acw.core.domain.uo.AcwFieldUo;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.core.domain.uo.AcwTableClassUo;
import com.wu.smart.acw.server.application.TableClassApplication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * describe : table about class
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 00:22
 */
@Service
public class TableClassServiceImpl implements TableClassApplication {

    private final LazyLambdaStream lazyLambdaStream;

    public TableClassServiceImpl(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * describe 初始化单表
     *
     * @param projectId          项目ID
     * @param instanceId 数据库服务器id
     * @param schema             数据库
     * @return
     * @author Jia wei Wu
     * @date 2022/1/30 00:26
     **/
    @Override
    public void singleTable(Long projectId, String instanceId, String schema) {
        final AcwInstanceUo acwInstanceUo =
                lazyLambdaStream.of(AcwInstanceUo.class)
                        .selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                                .eq(AcwInstanceUo::getId, instanceId));
        final ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);

        final Collection<LazyTableInfo> tableInfos = lazyLambdaStream.of(LazyTableInfo.class).
                selectList(LazyWrappers.<LazyTableInfo>lambdaWrapper().eq(LazyTableInfo::getTableSchema, schema));
        final List<String> tableNames = tableInfos.stream().map(LazyTableInfo::getTableName).collect(Collectors.toList());

        final Collection<LazyColumn> lazyColumns = lazyLambdaStream.of(LazyColumn.class).selectList(LazyWrappers.<LazyColumn>lambdaWrapper().
                eq(LazyColumn::getTableSchema, schema).
                inIgnoreEmpty(LazyColumn::getTableName, tableNames));
        final Map<String, List<LazyColumn>> stringListMap = lazyColumns.stream().collect(Collectors.groupingBy(LazyColumn::getTableName));


        DynamicLazyDSContextHolder.poll();

        stringListMap.forEach((tableName, tableLazyColumns) -> {
            final AcwClassUo acwClassUo = new AcwClassUo();
            acwClassUo.setProjectId(projectId).setName(CamelAndUnderLineConverter.lineToHumpClass(tableName)).setJavaClassType(JavaClassType.CLASS);
            lazyLambdaStream.upsert(acwClassUo);
            final Long classId = acwClassUo.getId();

            for (LazyColumn tableLazyColumn : tableLazyColumns) {
                final AcwFieldUo acwFieldUo = new AcwFieldUo();
                acwFieldUo.setClassId(classId).setName(CamelAndUnderLineConverter.lineToHumpField(tableLazyColumn.getColumnName()));
                lazyLambdaStream.upsert(acwFieldUo);
            }

            final AcwTableClassUo acwTableClassUo = new AcwTableClassUo();
            acwTableClassUo.setTableName(tableName).setClassId(classId).setInstanceId(instanceId).setSchema(schema).setProjectId(projectId);
            lazyLambdaStream.upsert(acwTableClassUo);
        });


    }
}
