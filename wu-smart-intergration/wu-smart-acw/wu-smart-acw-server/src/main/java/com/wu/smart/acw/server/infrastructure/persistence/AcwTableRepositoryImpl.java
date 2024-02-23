package com.wu.smart.acw.server.infrastructure.persistence;


import com.wu.framework.inner.layer.data.translation.NormalTranslation;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.server.domain.model.model.acw.table.AcwTable;
import com.wu.smart.acw.server.domain.model.model.acw.table.AcwTableRepository;
import com.wu.smart.acw.server.infrastructure.converter.AcwTableConverter;
import com.wu.smart.acw.server.infrastructure.entity.AcwTableDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * describe 数据库表信息（即将弃用）
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class AcwTableRepositoryImpl implements AcwTableRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/

    @Override
    public Result<AcwTable> story(AcwTable acwTable) {
        AcwTableDO acwTableDO = AcwTableConverter.INSTANCE.fromAcwTable(acwTable);
        lazyLambdaStream.upsert(acwTableDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/

    @Override
    public Result<AcwTable> batchStory(List<AcwTable> acwTableList) {
        List<AcwTableDO> acwTableDOList = acwTableList.stream().map(AcwTableConverter.INSTANCE::fromAcwTable).collect(Collectors.toList());
        lazyLambdaStream.upsert(acwTableDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/

    @Override
    public Result<AcwTable> findOne(AcwTable acwTable) {
        AcwTableDO acwTableDO = AcwTableConverter.INSTANCE.fromAcwTable(acwTable);
        AcwTable acwTableOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(acwTableDO), AcwTable.class);
        return ResultFactory.successOf(acwTableOne);
    }

    /**
     * describe 查询多个数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    @NormalTranslation
    @Override
    public Result<List<AcwTable>> findList(AcwTable acwTable) {
        Boolean initializeToLocal = acwTable.getInitializeToLocal();
        AcwTableDO acwTableDO = AcwTableConverter.INSTANCE.fromAcwTable(acwTable);
        if (Boolean.TRUE.equals(initializeToLocal)) {
            List<AcwTable> acwTableUoList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(acwTableDO), AcwTable.class);
            return ResultFactory.successOf(acwTableUoList);
        }
        String schemaName = acwTable.getSchemaName();
        String tableName = acwTable.getTableName();
        // 查询列数据
        List<AcwTable> acwTableList = lazyLambdaStream.
                selectList(
                        LazyWrappers.<LazyTableInfo>lambdaWrapper()
                                .like(!ObjectUtils.isEmpty(schemaName), LazyTableInfo::getTableSchema, schemaName)
                                .like(!ObjectUtils.isEmpty(tableName), LazyTableInfo::getTableName, "%" + tableName + "%")
                                .notNull(LazyTableInfo::getTableName).orderByAsc(LazyTableInfo::getTableName)
                                .oas("false", AcwTable::getInitializeToLocal).oas(String.valueOf(acwTable.getInstanceId()), AcwTable::getInstanceId)
                                .functionAs("concat( round(  DATA_LENGTH / 1024 / 1024 , 2 ), 'M' ) ", AcwTable::getDataLength)
                                .functionAs("concat( round(  INDEX_LENGTH / 1024 / 1024 , 2 ), 'M' ) ", AcwTable::getIndexLength)

                        , AcwTable.class);
        return ResultFactory.successOf(acwTableList);
    }

    /**
     * describe 分页查询多个数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    @NormalTranslation
    @Override
    public Result<LazyPage<AcwTable>> findPage(int size, int current, AcwTable acwTable) {

        LazyPage lazyPage = new LazyPage(current, size);
        Boolean initializeToLocal = acwTable.getInitializeToLocal();
        AcwTableDO acwTableDO = AcwTableConverter.INSTANCE.fromAcwTable(acwTable);

        if (initializeToLocal) {
            LazyPage<AcwTable> resultLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(acwTableDO), lazyPage, AcwTable.class);
            return ResultFactory.successOf(resultLazyPage);
        }
        String schemaName = acwTable.getSchemaName();
        String tableName = acwTable.getTableName();
        // 查询列数据
        LazyPage<AcwTable> acwTableLazyPage = lazyLambdaStream
                .selectPage(
                        LazyWrappers.<LazyTableInfo>lambdaWrapper()
                                .like(!ObjectUtils.isEmpty(schemaName), LazyTableInfo::getTableSchema, schemaName)
                                .like(!ObjectUtils.isEmpty(tableName), LazyTableInfo::getTableName, "%" + tableName + "%")
                                .notNull(LazyTableInfo::getTableName).orderByAsc(LazyTableInfo::getTableName)
                                .oas("false", AcwTable::getInitializeToLocal)
                                .oas(String.valueOf(acwTable.getInstanceId()), AcwTable::getInstanceId)
                                .as(LazyTableInfo::getTableSchema, AcwTable::getSchemaName)
                                .functionAs("concat( round(  DATA_LENGTH / 1024 / 1024 , 2 ), 'M' ) ", AcwTable::getDataLength)
                                .functionAs("concat( round(  INDEX_LENGTH / 1024 / 1024 , 2 ), 'M' ) ", AcwTable::getIndexLength)
                        , lazyPage, AcwTable.class);

        if (!ObjectUtils.isEmpty(acwTableLazyPage.getRecord())) {
            String unionAllSql = acwTableLazyPage.getRecord().stream().map(selectAcwTable -> {
                String tableName1 = selectAcwTable.getTableName();
                String schemaName1 = selectAcwTable.getSchemaName();
                return "select count(*) as tableRows,'" + tableName1 + "' as tableName from " + (ObjectUtils.isEmpty(schemaName1) ? tableName1 : schemaName1 + "." + tableName1);
            }).collect(Collectors.joining(" UNION ALL "));
            ConcurrentMap<String, Long> tableRowsMap = lazyLambdaStream.executeSQL(unionAllSql, EasyHashMap.class).stream().collect(Collectors.toConcurrentMap(e -> e.get("tableName").toString(), e -> (Long) e.get("tableRows")));

            acwTableLazyPage.convert(item -> {
                item.setTableRows(tableRowsMap.getOrDefault(item.getTableName(), 0L));
                return item;
            });
        }

        return ResultFactory.successOf(acwTableLazyPage);

    }

    /**
     * describe 删除数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/

    @Override
    public Result<AcwTable> remove(AcwTable acwTable) {
        AcwTableDO acwTableDO = AcwTableConverter.INSTANCE.fromAcwTable(acwTable);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(acwTableDO));
        return ResultFactory.successOf();
    }

}