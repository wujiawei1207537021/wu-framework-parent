package com.wu.smart.acw.server.infrastructure.persistence;


import com.wu.framework.inner.layer.util.LazyListUtils;
import com.wu.framework.inner.lazy.config.enums.MysqlColumnTypeEnum;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.smart.acw.server.domain.model.acw.table.association.relation.AcwTableAssociationRelation;
import com.wu.smart.acw.server.domain.model.acw.table.association.relation.AcwTableAssociationRelationRepository;
import com.wu.smart.acw.server.infrastructure.converter.AcwTableAssociationRelationConverter;
import com.wu.smart.acw.server.infrastructure.entity.AcwTableAssociationRelationDO;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 表关联关系
 *
 * @author Jia wei Wu
 * @date 2023/11/01 10:16 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class AcwTableAssociationRelationRepositoryImpl implements AcwTableAssociationRelationRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<AcwTableAssociationRelation> story(AcwTableAssociationRelation acwTableAssociationRelation) {
        AcwTableAssociationRelationDO acwTableAssociationRelationDO = AcwTableAssociationRelationConverter.INSTANCE.fromAcwTableAssociationRelation(acwTableAssociationRelation);
        lazyLambdaStream.upsert(acwTableAssociationRelationDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<List<AcwTableAssociationRelation>> batchStory(List<AcwTableAssociationRelation> acwTableAssociationRelationList) {
        List<AcwTableAssociationRelationDO> acwTableAssociationRelationDOList = acwTableAssociationRelationList.stream().map(AcwTableAssociationRelationConverter.INSTANCE::fromAcwTableAssociationRelation).collect(Collectors.toList());
        lazyLambdaStream.upsert(acwTableAssociationRelationDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<AcwTableAssociationRelation> findOne(AcwTableAssociationRelation acwTableAssociationRelation) {
        AcwTableAssociationRelationDO acwTableAssociationRelationDO = AcwTableAssociationRelationConverter.INSTANCE.fromAcwTableAssociationRelation(acwTableAssociationRelation);
        AcwTableAssociationRelation acwTableAssociationRelationOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(acwTableAssociationRelationDO), AcwTableAssociationRelation.class);
        return ResultFactory.successOf(acwTableAssociationRelationOne);
    }

    /**
     * describe 查询多个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<List<AcwTableAssociationRelation>> findList(AcwTableAssociationRelation acwTableAssociationRelation) {
        AcwTableAssociationRelationDO acwTableAssociationRelationDO = AcwTableAssociationRelationConverter.INSTANCE.fromAcwTableAssociationRelation(acwTableAssociationRelation);
        List<AcwTableAssociationRelation> acwTableAssociationRelationList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(acwTableAssociationRelationDO), AcwTableAssociationRelation.class);
        return ResultFactory.successOf(acwTableAssociationRelationList);
    }

    /**
     * describe 分页查询多个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<LazyPage<AcwTableAssociationRelation>> findPage(int size, int current, AcwTableAssociationRelation acwTableAssociationRelation) {
        AcwTableAssociationRelationDO acwTableAssociationRelationDO = AcwTableAssociationRelationConverter.INSTANCE.fromAcwTableAssociationRelation(acwTableAssociationRelation);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<AcwTableAssociationRelation> acwTableAssociationRelationLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(acwTableAssociationRelationDO), lazyPage, AcwTableAssociationRelation.class);
        return ResultFactory.successOf(acwTableAssociationRelationLazyPage);
    }

    /**
     * describe 删除表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Override
    public Result<AcwTableAssociationRelation> remove(AcwTableAssociationRelation acwTableAssociationRelation) {
        AcwTableAssociationRelationDO acwTableAssociationRelationDO = AcwTableAssociationRelationConverter.INSTANCE.fromAcwTableAssociationRelation(acwTableAssociationRelation);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(acwTableAssociationRelationDO));
        return ResultFactory.successOf();
    }

    /**
     * 分析数据库中表与表之间的关系
     *
     * @param acwTableAssociationRelation 分析数据库中表与表
     * @return
     */
    @Override
    public Result<Void> analysisSchema(AcwTableAssociationRelation acwTableAssociationRelation) {
        String instanceId = acwTableAssociationRelation.getInstanceId();
        String schema = acwTableAssociationRelation.getSchema();
        List<String> ignoreFieldList = acwTableAssociationRelation.getIgnoreFieldList();
        double relationThreshold = acwTableAssociationRelation.getRelationThreshold();
        // 删除解析出的关系
        Integer delete = lazyLambdaStream.delete(LazyWrappers.<AcwTableAssociationRelationDO>lambdaWrapper()
                .eq(AcwTableAssociationRelationDO::getInstanceId, instanceId)
                .eq(AcwTableAssociationRelationDO::getSchema, schema)
                .eq(AcwTableAssociationRelationDO::getType, 1)
        );
        try {
            // 切换数据库
            ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
            lazyDynamicEndpoint.setName(instanceId);
            lazyDynamicEndpoint.setSchema(schema);
            DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
            // 查询数据库表结构
            LinkedHashMap<String/*表名称*/, List<LazyColumn>/*表字段*/> stringListMap = lazyLambdaStream.selectList(
                    LazyWrappers.<LazyColumn>lambdaWrapper()
                            .eq(LazyColumn::getTableSchema, schema)
                            .notInIgnoreEmpty(LazyColumn::getColumnName, ignoreFieldList)
            ).stream().collect(Collectors.groupingBy(LazyColumn::getTableName, LinkedHashMap::new, Collectors.toList()));


            // 查询表对应的数量
            String unionAllSql = stringListMap.keySet().stream().map(table -> "select count(1) as tableRows,'" + table + "' as tableName from " + table).collect(Collectors.joining(" UNION ALL "));
            ConcurrentMap<String/*表*/, Long/*表数据量*/> tableRowsMap = lazyLambdaStream.executeSQL(unionAllSql, EasyHashMap.class).stream().collect(Collectors.toConcurrentMap(e -> e.get("tableName").toString(), e -> (Long) e.get("tableRows")));


            List<String> schemaAnalysisSqlList = new ArrayList<>();


            // 拼接sql
            stringListMap.forEach((sourceTable, sourceLazyColumns) -> stringListMap.forEach((relationTable, relationLazyColumns) -> {
                if (!Objects.equals(sourceTable, relationTable)) {
                    for (LazyColumn sourceTableColumn : sourceLazyColumns) {
                        for (LazyColumn relationLazyColumn : relationLazyColumns) {
                            String sourceTableColumnName = sourceTableColumn.getColumnName();
                            String relationTableColumnName = relationLazyColumn.getColumnName();
                            if (!Objects.equals(sourceTableColumnName, relationTableColumnName)
                                    && Objects.equals(sourceTableColumn.getDataType(), relationLazyColumn.getDataType())) {
                                // source rows
                                Long sourceRows = tableRowsMap.getOrDefault(sourceTable, 0L);
                                if (sourceRows == 0) {
                                    return;
                                }
                                // select instanceId, schema,sourceTable,relationTable, count(1)  from sourceTable inner join relationTable where sourceTable.sourceTableColumn=relationTable.relationLazyColumn

                                // longtext CONVERT(xx.xx using utf8)
                                String whereSourceColumn = String.format("%s.%s", sourceTable, sourceTableColumnName);
                                String whereRelationColumn = String.format("%s.%s", relationTable, relationTableColumnName);
                                String dataType = sourceTableColumn.getDataType();
                                if (MysqlColumnTypeEnum.LONG_TEXT.getDataType().equals(dataType)) {
                                    whereRelationColumn = "CONVERT(" + whereRelationColumn + " using utf8)";
                                }

                                String format = String.format(
                                        " select '%s' as instanceId , '%s' as `schema`, '%s' as sourceTable ,'%s' as sourceTableColumn ,'%s' as relationTable , '%s' as relationTableColumn , count(1)/%s as relation " +
                                                " from %s inner join %s " +
                                                " where %s = %s  ",
                                        instanceId, schema, sourceTable, sourceTableColumnName, relationTable, relationTableColumnName, sourceRows,
                                        sourceTable, relationTable,
                                        whereSourceColumn, whereRelationColumn
                                );
                                schemaAnalysisSqlList.add(format);
                            }
                        }
                    }
                }
            }));
            // 后去当前上下文
            LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
            // 每次获取一百条数据
            LazyListUtils.splitListThen(schemaAnalysisSqlList, 100, schemaAnalysisSqlSplitList -> {
                DynamicLazyDSContextHolder.push(peek);
                try {
                    String schemaAnalysisSql = String.join(" UNION ALL ", schemaAnalysisSqlSplitList);
                    // 查询 对应阀值
                    List<AcwTableAssociationRelationDO> acwTableAssociationRelationDOList =
                            lazyLambdaStream.executeSQL(schemaAnalysisSql, AcwTableAssociationRelationDO.class);
                    acwTableAssociationRelationDOList = acwTableAssociationRelationDOList
                            .stream()
                            .filter(acwTableAssociationRelationDO -> Double.compare(acwTableAssociationRelationDO.getRelation(), relationThreshold) > 0)
                            .peek(acwTableAssociationRelationDO -> {
                                acwTableAssociationRelationDO.setType(1);
                                acwTableAssociationRelationDO.setIsDeleted(false);
                            }).collect(Collectors.toList());
                    DynamicLazyDSContextHolder.clear();
                    lazyLambdaStream.upsert(acwTableAssociationRelationDOList);
                } catch (Throwable e) {
                    e.printStackTrace();
                    analysisSchemaOneRow(schemaAnalysisSqlSplitList, relationThreshold);
                }finally {
                    DynamicLazyDSContextHolder.clear();
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
            return ResultFactory.errorOf(e.getMessage());
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
        return ResultFactory.successOf();
    }

    /**
     * 一行一行解析数据
     *
     * @param schemaAnalysisSqlSplitList 需要解析的大量数据
     * @param relationThreshold          数据对比阀值
     */
    void analysisSchemaOneRow(List<String> schemaAnalysisSqlSplitList, double relationThreshold) {
        LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
        for (String schemaAnalysisSql : schemaAnalysisSqlSplitList) {
            DynamicLazyDSContextHolder.push(peek);
            try {
                List<AcwTableAssociationRelationDO> acwTableAssociationRelationDOList = lazyLambdaStream.executeSQL(schemaAnalysisSql, AcwTableAssociationRelationDO.class);
                acwTableAssociationRelationDOList = acwTableAssociationRelationDOList
                        .stream()
                        .filter(acwTableAssociationRelationDO -> Double.compare(acwTableAssociationRelationDO.getRelation(), relationThreshold) > 0)
                        .peek(acwTableAssociationRelationDO -> {
                            acwTableAssociationRelationDO.setType(1);
                            acwTableAssociationRelationDO.setIsDeleted(false);
                        }).collect(Collectors.toList());
                DynamicLazyDSContextHolder.clear();
                lazyLambdaStream.upsert(acwTableAssociationRelationDOList);
            } catch (Throwable e) {
                e.printStackTrace();
                System.out.println("当前数据导致解析schema执行失败。。 当前数据将自动过滤: " + schemaAnalysisSql);
            }finally {
                DynamicLazyDSContextHolder.clear();
            }
        }

    }
}