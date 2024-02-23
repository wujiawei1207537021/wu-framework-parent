package com.wu.smart.acw.server.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.persistence.map.DbMap;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.factory.LazyTableUpsertConverterFactory;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.util.LazyTableFieldUtil;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.exceptions.RuntimeExceptionFactory;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.server.application.AcwTableConsoleApplication;
import com.wu.smart.acw.server.application.command.AcwTableColumnConsoleCommand;
import com.wu.smart.acw.server.application.command.AcwTableConsoleCommand;
import com.wu.smart.acw.server.application.command.AcwTableRowBatchConsoleCommand;
import com.wu.smart.acw.server.application.command.AcwTableRowConsoleCommand;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LazyApplication
public class AcwTableConsoleApplicationImpl implements AcwTableConsoleApplication {
    private final LazyLambdaStream lazyLambdaStream;

    public AcwTableConsoleApplicationImpl(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /***
     * 执行sql语句
     * @param acwTableConsoleCommand
     * @return
     */
    @Override
    public Result<LazyPage<DbMap>> tableSqlConsole(AcwTableConsoleCommand acwTableConsoleCommand) {
        String instanceId = acwTableConsoleCommand.getInstanceId();
        String schemaName = acwTableConsoleCommand.getSchemaName();
        Assert.notNull(instanceId, "数据库实例ID不能为空");
        Assert.notNull(schemaName, "数据库不能为空");


        AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper().eq(AcwInstanceUo::getIsDeleted, false).eq(AcwInstanceUo::getId, instanceId).limit(1));
        Assert.notNull(acwInstanceUo, "数据库实例不存在");
        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        lazyDynamicEndpoint.setName(acwInstanceUo.getId());
        lazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        try {
            String sql;
            if (ObjectUtils.isEmpty(acwTableConsoleCommand.getSelectColumnList())) {
                sql = "select * from " + acwTableConsoleCommand.getTableName();
            } else {
                sql = "select " + acwTableConsoleCommand.getSelectColumnList().stream().map(LazyTableFieldUtil::cleanSpecialColumn).collect(Collectors.joining(NormalUsedString.COMMA)) + " from " + acwTableConsoleCommand.getTableName();
            }

            if (!ObjectUtils.isEmpty(acwTableConsoleCommand.getQueryCriteriaColumnList())) {
                String criteriaColumnSql = acwTableConsoleCommand.getSql();
                if (!ObjectUtils.isEmpty(criteriaColumnSql)) {
                    sql = sql + " where " + criteriaColumnSql;
                }
            }
            int current = acwTableConsoleCommand.getCurrent();
            int size = acwTableConsoleCommand.getSize();
            LazyPage<DbMap> lazyPage = new LazyPage<>(current, size);
            lazyPage.setAscs(acwTableConsoleCommand.getAscs());
            lazyPage.setDescs(acwTableConsoleCommand.getDescs());
            LazyPage<DbMap> easyHashMapLazyPage = lazyLambdaStream.selectPage(lazyPage, DbMap.class, sql);
            // 查询表结构
            List<LazyColumn> lazyColumnList = lazyLambdaStream.selectList(LazyWrappers.<LazyColumn>lambdaWrapper().eq(LazyColumn::getTableName, acwTableConsoleCommand.getTableName()).eq(LazyColumn::getTableSchema, schemaName));
            return ResultFactory.successOf(easyHashMapLazyPage, Map.of("tableHeader", lazyColumnList, "sql", sql));
        } catch (Exception e) {
            e.printStackTrace();
            RuntimeExceptionFactory.of(e.getMessage());
            return null;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /**
     * 导出执行sql语句
     *
     * @param acwTableConsoleCommand
     * @return
     */
    @Override
    public String tableSqlConsoleUpsertExport(AcwTableConsoleCommand acwTableConsoleCommand) {
        List<DbMap> dbMaps = tableSqlConsoleFind(acwTableConsoleCommand);
        return LazyTableUpsertConverterFactory.upsert(dbMaps.stream().map(dbMap -> {
            EasyHashMap easyHashMap = new EasyHashMap();
            easyHashMap.putAll(dbMap);
            easyHashMap.setUniqueLabel(acwTableConsoleCommand.getTableName());
            return easyHashMap;
        }).toList());
    }

    /**
     * 通过控制台 查询所有数据
     *
     * @return 所有数据
     */
    private List<DbMap> tableSqlConsoleFind(AcwTableConsoleCommand acwTableConsoleCommand) {
        String instanceId = acwTableConsoleCommand.getInstanceId();
        String schemaName = acwTableConsoleCommand.getSchemaName();
        String tableName = acwTableConsoleCommand.getTableName();


        AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper().eq(AcwInstanceUo::getIsDeleted, false).eq(AcwInstanceUo::getId, instanceId).limit(1));
        Assert.notNull(acwInstanceUo, "数据库实例不存在");
        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        lazyDynamicEndpoint.setName(acwInstanceUo.getId());
        lazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        try {
            String sql;
            if (ObjectUtils.isEmpty(acwTableConsoleCommand.getSelectColumnList())) {
                sql = "select * from " + acwTableConsoleCommand.getTableName();
            } else {
                sql = "select " + acwTableConsoleCommand.getSelectColumnList().stream().map(LazyTableFieldUtil::cleanSpecialColumn).collect(Collectors.joining(NormalUsedString.COMMA)) + " from " + acwTableConsoleCommand.getTableName();
            }
            if (!ObjectUtils.isEmpty(acwTableConsoleCommand.getQueryCriteriaColumnList())) {
                String criteriaColumnSql = acwTableConsoleCommand.getSql();
                if (!ObjectUtils.isEmpty(criteriaColumnSql)) {
                    sql = sql + " where " + criteriaColumnSql;
                }
            }
            List<DbMap> easyHashMaps = lazyLambdaStream.executeSQL(sql, DbMap.class);
            return easyHashMaps;
        } catch (Exception e) {
            e.printStackTrace();
            RuntimeExceptionFactory.of(e.getMessage());
            return null;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /**
     * 存储表行数据
     *
     * @param acwTableRowConsoleCommand
     * @return
     */
    @Override
    public Result tableRowStory(AcwTableRowConsoleCommand acwTableRowConsoleCommand) {
        String instanceId = acwTableRowConsoleCommand.getInstanceId();
        String schemaName = acwTableRowConsoleCommand.getSchemaName();
        String tableName = acwTableRowConsoleCommand.getTableName();


        AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper().eq(AcwInstanceUo::getIsDeleted, false).eq(AcwInstanceUo::getId, instanceId).limit(1));
        Assert.notNull(acwInstanceUo, "数据库实例不存在");
        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        lazyDynamicEndpoint.setName(acwInstanceUo.getId());
        lazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        try {

            EasyHashMap<String, Object> tableRow = acwTableRowConsoleCommand.getTableRow();
            tableRow.setUniqueLabel(tableName);
            lazyLambdaStream.upsertRemoveNull(tableRow);
            return ResultFactory.successOf();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            RuntimeExceptionFactory.of(throwable.getCause().getMessage());
            return null;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /**
     * 删除表行数据
     *
     * @param acwTableRowConsoleCommand 表行更新、删除 参数
     * @return
     */
    @Override
    public Result tableRowDelete(AcwTableRowConsoleCommand acwTableRowConsoleCommand) {

        AcwTableRowBatchConsoleCommand acwTableRowBatchConsoleCommand = new AcwTableRowBatchConsoleCommand();
        acwTableRowBatchConsoleCommand.setInstanceId(acwTableRowConsoleCommand.getInstanceId());
        acwTableRowBatchConsoleCommand.setSchemaName(acwTableRowConsoleCommand.getSchemaName());
        acwTableRowBatchConsoleCommand.setTableName(acwTableRowConsoleCommand.getTableName());
        acwTableRowBatchConsoleCommand.setTableRowList(Collections.singletonList(acwTableRowConsoleCommand.getTableRow()));
        return tableRowBatchDelete(acwTableRowBatchConsoleCommand);
    }

    /**
     * describe 批量删除表行数据
     *
     * @param acwTableRowBatchConsoleCommand 表行批量 更新、删除 参数
     * @return
     */
    @Override
    public Result tableRowBatchDelete(AcwTableRowBatchConsoleCommand acwTableRowBatchConsoleCommand) {
        String instanceId = acwTableRowBatchConsoleCommand.getInstanceId();
        String schemaName = acwTableRowBatchConsoleCommand.getSchemaName();
        String tableName = acwTableRowBatchConsoleCommand.getTableName();
        List<EasyHashMap<String, Object>> tableRowList = acwTableRowBatchConsoleCommand.getTableRowList();

        AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper().eq(AcwInstanceUo::getIsDeleted, false).eq(AcwInstanceUo::getId, instanceId).limit(1));
        Assert.notNull(acwInstanceUo, "数据库实例不存在");
        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        lazyDynamicEndpoint.setName(acwInstanceUo.getId());
        lazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        try {
            String multiSql = tableRowList.stream().map(stringObjectEasyHashMap -> {
                String sql = "delete  from " + acwTableRowBatchConsoleCommand.getTableName();
                sql = sql + " where ";
                String condition = stringObjectEasyHashMap.entrySet().stream().map(stringObjectEntry -> {
                    Object value = stringObjectEntry.getValue();
                    if (value == null) {
                        return NormalUsedString.SPACE + LazyTableFieldUtil.cleanSpecialColumn(stringObjectEntry.getKey()) + NormalUsedString.SPACE + NormalUsedString.IS + NormalUsedString.SPACE + null + NormalUsedString.SPACE;

                    } else {
                        if (value instanceof Boolean || value instanceof Integer || value instanceof Long) {
                            return NormalUsedString.SPACE + LazyTableFieldUtil.cleanSpecialColumn(stringObjectEntry.getKey()) + NormalUsedString.SPACE + NormalUsedString.EQUALS + NormalUsedString.SPACE + value + NormalUsedString.SPACE;
                        }
                    }
                    return NormalUsedString.SPACE + LazyTableFieldUtil.cleanSpecialColumn(stringObjectEntry.getKey()) + NormalUsedString.SPACE + NormalUsedString.EQUALS + NormalUsedString.SPACE + NormalUsedString.SINGLE_QUOTE + value + NormalUsedString.SINGLE_QUOTE + NormalUsedString.SPACE;
                }).collect(Collectors.joining(NormalUsedString.SPACE + NormalUsedString.AND + NormalUsedString.SPACE));
                sql = sql + condition + " limit 1 ;";
                return sql;
            }).collect(Collectors.joining(NormalUsedString.SPACE));
//
            Integer aBoolean = lazyLambdaStream.executeSQLForBean(multiSql, Integer.class); //TODO 无法执行多个脚本
//            return ResultFactory.successOf(aBoolean);
//            lazyLambdaStream.stringScriptRunner(multiSql);
            return ResultFactory.successOf();
        } catch (Exception e) {
            e.printStackTrace();
            RuntimeExceptionFactory.of(e.getMessage());
            return null;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /**
     * 获取表指定列数据列表
     *
     * @param acwTableColumnConsoleCommand
     * @return
     */
    @Override
    public Result<LazyPage<String>> tableColumnSqlConsole(AcwTableColumnConsoleCommand acwTableColumnConsoleCommand) {
        String instanceId = acwTableColumnConsoleCommand.getInstanceId();
        String schemaName = acwTableColumnConsoleCommand.getSchemaName();
        String tableName = acwTableColumnConsoleCommand.getTableName();
        AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper().eq(AcwInstanceUo::getIsDeleted, false).eq(AcwInstanceUo::getId, instanceId).limit(1));
        Assert.notNull(acwInstanceUo, "数据库实例不存在");
        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        lazyDynamicEndpoint.setName(acwInstanceUo.getId());
        lazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        // 查询列数据
        int current = acwTableColumnConsoleCommand.getCurrent();
        int size = acwTableColumnConsoleCommand.getSize();
        String column = acwTableColumnConsoleCommand.getColumn();

        LazyPage<String> lazyPage = new LazyPage<>(current, size);
        LazyPage<String> stringLazyPage = lazyLambdaStream.selectPage(lazyPage, String.class, "select {1} from {0} group by {1}", tableName, LazyTableFieldUtil.cleanSpecialColumn(column));
        DynamicLazyDSContextHolder.clear();
        return ResultFactory.successOf(stringLazyPage);
    }

    /**
     * describe 导出结果为MD
     *
     * @param acwTableConsoleCommand 查询参数
     * @return String
     */
    @Override
    public String tableSqlConsoleMdExport(AcwTableConsoleCommand acwTableConsoleCommand) {
        List<DbMap> easyHashMaps = tableSqlConsoleFind(acwTableConsoleCommand);
        if (ObjectUtils.isEmpty(easyHashMaps)) {
            throw new IllegalArgumentException("查询数据为空");
        }
        // 表头
        DbMap first = easyHashMaps.stream().findFirst().get();
        StringBuilder stringBuilder=new StringBuilder();

        String firstTitle = "|" + first.keySet().stream().map(title -> title + "|").collect(Collectors.joining());
        stringBuilder.append(firstTitle);

        stringBuilder.append(NormalUsedString.NEWLINE);
        stringBuilder.append("|");
        stringBuilder.append(first.keySet().stream().map(title -> "-----" + "|").collect(Collectors.joining()));
        stringBuilder.append(NormalUsedString.NEWLINE);
        for (DbMap easyHashMap : easyHashMaps) {
            stringBuilder.append("|");
            stringBuilder.append(easyHashMap.values().stream().map(value -> value + "|").collect(Collectors.joining()));
            stringBuilder.append(NormalUsedString.NEWLINE);
        }
        return stringBuilder.toString();
    }
}
