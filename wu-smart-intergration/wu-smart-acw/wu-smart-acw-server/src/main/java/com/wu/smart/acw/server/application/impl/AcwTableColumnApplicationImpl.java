package com.wu.smart.acw.server.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyColumnIndex;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.AcwTableColumnUo;
import com.wu.smart.acw.server.application.AcwTableColumnApplication;
import com.wu.smart.acw.server.application.assembler.AcwTableCommandColumnDTOAssembler;
import com.wu.smart.acw.server.application.dto.AcwTableCommandColumnDTO;
import com.wu.smart.acw.server.domain.model.model.acw.instance.AcwInstanceRepository;

import java.util.*;
import java.util.stream.Collectors;

@LazyApplication
public class AcwTableColumnApplicationImpl implements AcwTableColumnApplication {
    private final AcwInstanceRepository acwInstanceRepository;
    private final LazyLambdaStream lazyLambdaStream;

    public AcwTableColumnApplicationImpl(AcwInstanceRepository acwInstanceRepository, LazyLambdaStream lazyLambdaStream) {
        this.acwInstanceRepository = acwInstanceRepository;
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * describe  查询出所有的数据
     *
     * @param instanceId    实例ID
     * @param schemaName    schema
     * @param tableNameList 表名称集合
     * @return Result<T>
     * @author Jia wei Wu
     */
    @Override
    public Result<List<AcwTableCommandColumnDTO.AcwTableColumnDTO>> retrieveAllByTableNameList(String instanceId, String schemaName, List<String> tableNameList) {
        // 切换数据源
        acwInstanceRepository.switchInstance(instanceId);
        // 查询数据库表字段
        try {
            List<AcwTableCommandColumnDTO.AcwTableColumnDTO> acwTableColumnUoList = lazyLambdaStream.selectList(LazyWrappers.<LazyColumn>lambdaWrapper()
                            .eq(LazyColumn::getTableSchema, schemaName)
                            .in(LazyColumn::getTableName, tableNameList),
                    AcwTableCommandColumnDTO.AcwTableColumnDTO.class
            );

            return ResultFactory.successOf(acwTableColumnUoList);
        } catch (Throwable e) {
            throw e;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /**
     * 根据表名 获取当前表中字段
     *
     * @param instanceId 实例ID
     * @param schemaName schema
     * @param tableName  表名称
     * @return 当前表中字段
     */
    @Override
    public Result<AcwTableCommandColumnDTO> retrieveAllByTableName(String instanceId, String schemaName, String tableName) {
        // 切换数据源
        acwInstanceRepository.switchInstance(instanceId);
        // 查询数据库表字段
        try {
            List<AcwTableCommandColumnDTO.AcwTableColumnDTO> acwTableColumnDTOList = lazyLambdaStream.selectList(LazyWrappers.<LazyColumn>lambdaWrapper()
                            .eq(LazyColumn::getTableSchema, schemaName)
                            .eq(LazyColumn::getTableName, tableName),
                    AcwTableCommandColumnDTO.AcwTableColumnDTO.class
            );
            // 查询索引信息
            List<LazyColumnIndex> lazyColumnIndexList = lazyLambdaStream.selectList(LazyWrappers.<LazyColumnIndex>lambdaWrapper()
                    .eq(LazyColumnIndex::getTableSchema, schemaName)
                    .eq(LazyColumnIndex::getTableName, tableName)
            );

            List<AcwTableCommandColumnDTO.AcwTableColumnIndexDTO> acwTableColumnIndexDTOList = lazyColumnIndexList.stream().collect(Collectors.groupingBy(LazyColumnIndex::getIndexName)).values().stream()
                    .map(lazyColumnIndices -> {
                        List<String> columnNameList = lazyColumnIndices.stream().map(LazyColumnIndex::getColumnName).collect(Collectors.toList());
                        AcwTableCommandColumnDTO.AcwTableColumnIndexDTO acwTableColumnIndexDTO =
                                AcwTableCommandColumnDTOAssembler.INSTANCE.formLazyColumnIndex(lazyColumnIndices.get(0));

                        acwTableColumnIndexDTO.setColumnNameList(columnNameList);

                        return acwTableColumnIndexDTO;
                    }).collect(Collectors.toList());

            AcwTableCommandColumnDTO acwTableCommandColumnDTO = new AcwTableCommandColumnDTO();
            acwTableCommandColumnDTO.setAcwTableColumnDTOList(acwTableColumnDTOList);
            acwTableCommandColumnDTO.setAcwTableColumnIndexDTOList(acwTableColumnIndexDTOList);
            return ResultFactory.successOf(acwTableCommandColumnDTO);
        } catch (Throwable e) {
            throw e;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /**
     * 查询实例上所有的字段
     *
     * @param instanceId 实例ID
     * @param schemaName schema
     * @return
     */
    @Override
    public Result<List<AcwTableCommandColumnDTO.AcwTableColumnDTO>> findInstanceSchemaColumnList(String instanceId, String schemaName) {
        // 切换数据源
        acwInstanceRepository.switchInstance(instanceId);
        // 查询数据库表字段
        try {

            List<AcwTableCommandColumnDTO.AcwTableColumnDTO> acwTableColumnDTOList =
                    lazyLambdaStream.selectList(
                                    LazyWrappers.<LazyColumn>lambdaWrapper()
                                            .eq(LazyColumn::getTableSchema, schemaName),
                                    AcwTableCommandColumnDTO.AcwTableColumnDTO.class
                            )
                            .stream()
                            .collect(
                                    Collectors.collectingAndThen(Collectors.toCollection(() ->
                                            new TreeSet<>(Comparator.comparing(AcwTableCommandColumnDTO.AcwTableColumnDTO::getColumnName))), ArrayList::new));
            return ResultFactory.successOf(acwTableColumnDTOList);
        } catch (Throwable e) {
            throw e;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /**
     * 查询出表上所有字段
     *
     * @param instanceId 实例ID
     * @param schemaName schema
     * @param tableName  表名称
     * @return Result<T>
     */
    @Override
    public Result<List<AcwTableCommandColumnDTO.AcwTableColumnDTO>> findColumnByTableName(String instanceId, String schemaName, String tableName) {
        // 切换数据源
        acwInstanceRepository.switchInstance(instanceId);
        // 查询数据库表字段
        try {
            List<AcwTableCommandColumnDTO.AcwTableColumnDTO> acwTableColumnDTOList = lazyLambdaStream.selectList(LazyWrappers.<LazyColumn>lambdaWrapper()
                            .eq(LazyColumn::getTableSchema, schemaName)
                            .eq(LazyColumn::getTableName, tableName),
                    AcwTableCommandColumnDTO.AcwTableColumnDTO.class
            );
            return ResultFactory.successOf(acwTableColumnDTOList);
        } catch (Throwable e) {
            throw e;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }
}
