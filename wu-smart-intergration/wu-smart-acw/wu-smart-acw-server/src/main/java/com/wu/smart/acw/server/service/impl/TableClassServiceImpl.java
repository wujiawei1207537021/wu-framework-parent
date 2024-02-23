package com.wu.smart.acw.server.service.impl;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaClassType;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.smart.acw.core.domain.uo.*;
import com.wu.smart.acw.server.service.TableClassService;
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
public class TableClassServiceImpl implements TableClassService {

    private final LazyLambdaStream lazyLambdaStream;

    public TableClassServiceImpl(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * describe 初始化单表
     *
     * @param projectId        项目ID
     * @param databaseServerId 数据库服务器id
     * @param schema           数据库
     * @return
     * @author Jia wei Wu
     * @date 2022/1/30 00:26
     **/
    @Override
    public void singleTable(Long projectId, Long databaseServerId, String schema) {
        final DatabaseServerUo databaseServerUo =
                lazyLambdaStream.of(DatabaseServerUo.class)
                        .select(LazyWrappers.<DatabaseServerUo>lambdaWrapper()
                                .eq(DatabaseServerUo::getId, databaseServerId)).collectOne();
        final ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(databaseServerUo.getName());
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);

        final Collection<LazyTableInfo> tableInfos = lazyLambdaStream.of(LazyTableInfo.class).
                select(LazyWrappers.<LazyTableInfo>lambdaWrapper().eq(LazyTableInfo::getTableSchema, schema)).collection();
        final List<String> tableNames = tableInfos.stream().map(LazyTableInfo::getTableName).collect(Collectors.toList());

        final Collection<LazyColumn> lazyColumns = lazyLambdaStream.of(LazyColumn.class).select(LazyWrappers.<LazyColumn>lambdaWrapper().
                        eq(LazyColumn::getTableSchema, schema).
                        inIgnoreEmpty(LazyColumn::getTableName, tableNames)).
                collection();
        final Map<String, List<LazyColumn>> stringListMap = lazyColumns.stream().collect(Collectors.groupingBy(LazyColumn::getTableName));


        DynamicLazyDSContextHolder.poll();

        stringListMap.forEach((tableName, tableLazyColumns) -> {
            final ClassUo classUo = new ClassUo();
            classUo.setProjectId(projectId).setName(CamelAndUnderLineConverter.lineToHumpClass(tableName)).setJavaClassType(JavaClassType.CLASS);
            lazyLambdaStream.smartUpsert(classUo);
            final Long classId = classUo.getId();

            for (LazyColumn tableLazyColumn : tableLazyColumns) {
                final FieldUo fieldUo = new FieldUo();
                fieldUo.setClassId(classId).setName(CamelAndUnderLineConverter.lineToHumpField(tableLazyColumn.getColumnName()));
                lazyLambdaStream.smartUpsert(fieldUo);
            }

            final TableClassUo tableClassUo = new TableClassUo();
            tableClassUo.setTableName(tableName).setClassId(classId).setDatabaseServerId(databaseServerId).setSchema(schema).setProjectId(projectId);
            lazyLambdaStream.smartUpsert(tableClassUo);
        });


    }
}
