package com.wu.smart.acw.server.infrastructure.persistence;

import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyDatabase;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.domain.Schemata;
import com.wu.framework.inner.lazy.database.dynamic.DynamicAdapter;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperation;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.exceptions.RuntimeExceptionFactory;
import com.wu.smart.acw.core.domain.dto.DatabaseSchemaDto;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.core.domain.uo.AcwSchemaUo;
import com.wu.smart.acw.core.domain.uo.AcwTableColumnUo;
import com.wu.smart.acw.core.domain.uo.AcwTableUo;
import com.wu.smart.acw.server.domain.model.model.acw.schema.AcwSchema;
import com.wu.smart.acw.server.domain.model.model.acw.schema.AcwSchemaRepository;
import com.wu.smart.acw.server.infrastructure.converter.AcwSchemaConverter;
import com.wu.smart.acw.server.infrastructure.entity.AcwInstanceDO;
import com.wu.smart.acw.server.infrastructure.entity.AcwSchemaDO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/11/28 21:43
 */
@Service
public class AcwSchemaRepositoryImpl implements AcwSchemaRepository {
    private final LazyLambdaStream lazyLambdaStream;
    private final DynamicAdapter dynamicAdapter;
    private final SmartLazyOperation smartLazyOperation;

    public AcwSchemaRepositoryImpl(LazyLambdaStream lazyLambdaStream, DynamicAdapter dynamicAdapter, SmartLazyOperation smartLazyOperation) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.dynamicAdapter = dynamicAdapter;
        this.smartLazyOperation = smartLazyOperation;
    }

    /**
     * describe 新增ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<AcwSchema> story(AcwSchema acwSchema) {
        AcwSchemaDO acwSchemaDO = AcwSchemaConverter.INSTANCE.fromAcwSchema(acwSchema);
        lazyLambdaStream.upsert(acwSchemaDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<List<AcwSchema>> batchStory(List<AcwSchema> acwSchemaList) {
        List<AcwSchemaDO> acwSchemaDOList = acwSchemaList.stream().map(AcwSchemaConverter.INSTANCE::fromAcwSchema).collect(Collectors.toList());
        lazyLambdaStream.upsert(acwSchemaDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<AcwSchema> findOne(AcwSchema acwSchema) {
        AcwSchemaDO acwSchemaDO = AcwSchemaConverter.INSTANCE.fromAcwSchema(acwSchema);
        AcwSchema acwSchemaOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(acwSchemaDO), AcwSchema.class);
        return ResultFactory.successOf(acwSchemaOne);
    }

    /**
     * describe 查询多个ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<List<AcwSchema>> findListWithSize(AcwSchema acwSchema) {
        String instanceId = acwSchema.getInstanceId();
        Boolean initializeToLocal = acwSchema.getInitializeToLocal();
        if (!ObjectUtils.isEmpty(instanceId) && !ObjectUtils.isEmpty(initializeToLocal) && !initializeToLocal) {
            // 切换数据库
            String schemaName = acwSchema.getSchemaName();
            // 查询schema
            List<AcwSchema> acwSchemaList = lazyLambdaStream.selectList(
                    LazyWrappers.<Schemata>lambdaWrapper()
                            .like(!ObjectUtils.isEmpty(schemaName), Schemata::getSchemaName, schemaName + "%")
                            .notNull(Schemata::getSchemaName)
                            .orderByAsc(Schemata::getSchemaName)
                            .oas(instanceId, AcwSchema::getInstanceId)
                            .oas("false", AcwSchema::getInitializeToLocal)
                            .as(Schemata::getSchemaName, AcwSchema::getSchemaName)
                            .functionAs("(select concat( sum(round(  DATA_LENGTH / 1024 / 1024 , 2 )), 'M' ) from information_schema.tables where table_schema =schema_name )", AcwSchema::getDataLength)
                            .functionAs("(select concat( sum(round(  INDEX_LENGTH / 1024 / 1024 , 2 )), 'M' ) from information_schema.tables where table_schema =schema_name )", AcwSchema::getIndexLength)
                    ,
                    AcwSchema.class
            );
            DynamicLazyDSContextHolder.clear();
            return ResultFactory.successOf(acwSchemaList);
        }

        AcwSchemaDO acwSchemaDO = AcwSchemaConverter.INSTANCE.fromAcwSchema(acwSchema);
        List<AcwSchema> acwSchemaList = lazyLambdaStream
                .selectList(LazyWrappers.<AcwSchemaDO>lambdaWrapperBean(acwSchemaDO)
                                .leftJoin(LazyWrappers.<AcwSchemaDO, AcwInstanceDO>lambdaWrapperJoin()
                                        .eq(AcwSchemaDO::getInstanceId, AcwInstanceDO::getId))
                                .onlyUseAs()
                                .as(AcwSchema.class)
                        ,
                        AcwSchema.class);
        return ResultFactory.successOf(acwSchemaList);
    }

    /**
     * describe 分页查询多个ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<LazyPage<AcwSchema>> findPage(int size, int current, AcwSchema acwSchema) {
        String instanceId = acwSchema.getInstanceId();
        Boolean initializeToLocal = acwSchema.getInitializeToLocal();
        if (!ObjectUtils.isEmpty(instanceId) && !ObjectUtils.isEmpty(initializeToLocal) && !initializeToLocal) {
            // 切换数据库
            String schemaName = acwSchema.getSchemaName();
            LazyPage lazyPage = new LazyPage(current, size);
            // 查询schema
            LazyPage<AcwSchema> databaseSchemaUoLazyPage = lazyLambdaStream.selectPage(
                    LazyWrappers.<Schemata>lambdaWrapper()
                            .like(!ObjectUtils.isEmpty(schemaName), Schemata::getSchemaName, schemaName + "%")
                            .notNull(Schemata::getSchemaName)
                            .orderByAsc(Schemata::getSchemaName)
                            .oas(instanceId, AcwSchema::getInstanceId)
                            .oas("false", AcwSchema::getInitializeToLocal)
                            .as(Schemata::getSchemaName, AcwSchema::getSchemaName)
                            .functionAs("(select concat( sum(round(  DATA_LENGTH / 1024 / 1024 , 2 )), 'M' ) from information_schema.tables where table_schema =schema_name )", AcwSchema::getDataLength)
                            .functionAs("(select concat( sum(round(  INDEX_LENGTH / 1024 / 1024 , 2 )), 'M' ) from information_schema.tables where table_schema =schema_name )", AcwSchema::getIndexLength)

                    ,
                    lazyPage,
                    AcwSchema.class
            );
            return ResultFactory.successOf(databaseSchemaUoLazyPage);
        }

        AcwSchemaDO acwSchemaDO = AcwSchemaConverter.INSTANCE.fromAcwSchema(acwSchema);
        LazyPage<AcwSchema> lazyPage = new LazyPage<AcwSchema>(current, size);
        LazyPage<AcwSchema> resultLazyPage = lazyLambdaStream
                .selectPage(LazyWrappers.<AcwSchemaDO>lambdaWrapperBean(acwSchemaDO)
                                .leftJoin(LazyWrappers.<AcwSchemaDO, AcwInstanceDO>lambdaWrapperJoin()
                                        .eq(AcwSchemaDO::getInstanceId, AcwInstanceDO::getId))

                                .onlyUseAs()
                                .as(AcwSchemaUo.class)
                                .as(AcwInstanceUo::getInstanceName, DatabaseSchemaDto::getInstanceName)
                        , lazyPage,
                        AcwSchema.class);
        return ResultFactory.successOf(resultLazyPage);
    }

    /**
     * describe 删除ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<AcwSchema> remove(AcwSchema acwSchema) {
        AcwSchemaDO acwSchemaDO = AcwSchemaConverter.INSTANCE.fromAcwSchema(acwSchema);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(acwSchemaDO));
        return ResultFactory.successOf();
    }

    /**
     * 切换schema
     *
     * @param instanceId 实例ID
     * @param schema     数据库schema
     */
    @Override
    public void switchSchema(String instanceId, String schema) {
        AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                .eq(AcwInstanceUo::getId, instanceId)
                .eq(AcwInstanceUo::getIsDeleted, false));
        if (ObjectUtils.isEmpty(acwInstanceUo)) {
            RuntimeExceptionFactory.of("无法找到数据源");
        }
        LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(acwInstanceUo.getUrl());
        lazyDataSourceProperties.setUsername(acwInstanceUo.getUsername());
        lazyDataSourceProperties.setPassword(acwInstanceUo.getPassword());
        lazyDataSourceProperties.setAlias(acwInstanceUo.getId());
        dynamicAdapter.loading(lazyDataSourceProperties);
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
        classLazyDynamicEndpoint.setSchema(schema);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
    }

    /**
     * 重新初始化数据库
     *
     * @param instanceId 实例名称
     * @param schema     数据库
     */
    @Override
    public Result reload(String instanceId, String schema) {
        // 切换数据源
        AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                .eq(AcwInstanceUo::getId, instanceId)
                .eq(AcwInstanceUo::getIsDeleted, false));
        if (ObjectUtils.isEmpty(acwInstanceUo)) {
            RuntimeExceptionFactory.of("无法找到数据源");
        }
        LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(acwInstanceUo.getUrl());
        lazyDataSourceProperties.setUsername(acwInstanceUo.getUsername());
        lazyDataSourceProperties.setPassword(acwInstanceUo.getPassword());
        lazyDataSourceProperties.setAlias(acwInstanceUo.getId());
        dynamicAdapter.loading(lazyDataSourceProperties);
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
        classLazyDynamicEndpoint.setSchema(schema);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        // 重新加载
        // 初始化数据库信息
        LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
        List<LazyDatabase> lazyDatabases = smartLazyOperation.showDatabases();
        List<AcwSchemaUo> acwSchemaUos = lazyDatabases.stream().map(lazyDatabase -> {
            AcwSchemaUo acwSchemaUo = new AcwSchemaUo();
            acwSchemaUo.setSchemaName(lazyDatabase.getDatabase());
            acwSchemaUo.setInstanceId(instanceId);
            acwSchemaUo.setIsDeleted(false);
            acwSchemaUo.setInitializeToLocal(true);
            return acwSchemaUo;
        }).collect(Collectors.toList());
        DynamicLazyDSContextHolder.push(peek);

        // 表信息
        Collection<AcwTableUo> acwTableUoList = lazyLambdaStream.select(LazyWrappers.<LazyTableInfo>lambdaWrapper()
                        .notNull(LazyTableInfo::getTableName)
                ).
                stream(AcwTableUo.class).collect(Collectors.toList());
        DynamicLazyDSContextHolder.push(peek);

        // 表字段信息
        Collection<AcwTableColumnUo> acwTableColumnUos = lazyLambdaStream.of(LazyColumn.class).
                select(LazyWrappers.<LazyTableInfo>lambdaWrapper())
                .stream(AcwTableColumnUo.class).collect(Collectors.toList());


        DynamicLazyDSContextHolder.push(peek);
        // 存储schema 信息
        lazyLambdaStream.upsert(acwSchemaUos);
        DynamicLazyDSContextHolder.push(peek);
        Map<String, Long> databaseSchemaUoMap = lazyLambdaStream
                .selectList(LazyWrappers.<AcwSchemaUo>lambdaWrapper().eq(AcwSchemaUo::getInstanceId, instanceId)
                ).stream().collect(Collectors.toMap(AcwSchemaUo::getSchemaName, AcwSchemaUo::getId, (A, B) -> A));
        // 存储表信息
        acwTableUoList = acwTableUoList.stream().peek(databaseTableUo -> {
            databaseTableUo.setInstanceId(instanceId);
            databaseTableUo.setInstanceName(acwInstanceUo.getInstanceName());
            databaseTableUo.setSchemaNameId(databaseSchemaUoMap.getOrDefault(databaseTableUo.getSchemaName(), 0L));
            databaseTableUo.setSchemaName(databaseTableUo.getTableName());
        }).collect(Collectors.toList());

        lazyLambdaStream.upsert(acwTableUoList);

        // 存储字段
        Map<String, Long> databaseTableUoMap = lazyLambdaStream.of(AcwTableUo.class).
                selectList(
                        LazyWrappers.<AcwTableUo>lambdaWrapper().
                                eq(AcwTableUo::getInstanceId, acwInstanceUo.getId())
                ).stream().collect(Collectors.toMap(AcwTableUo::getTableName, AcwTableUo::getId, (A, B) -> A));
        acwTableColumnUos = acwTableColumnUos.
                stream().
                peek(databaseTableColumnUo ->
                        databaseTableColumnUo.setTableId(databaseTableUoMap.getOrDefault(databaseTableColumnUo.getTableName(), 0L))).
                collect(Collectors.toList());

        lazyLambdaStream.upsert(acwTableColumnUos);
        return ResultFactory.successOf();
    }

    /**
     * 获取schema分页
     *
     * @param size
     * @param current
     * @param acwSchema
     * @return
     */
    @Override
    public Result<LazyPage<AcwSchema>> retrievePage1(int size, int current, AcwSchema acwSchema) {
        AcwSchemaDO acwSchemaDO = AcwSchemaConverter.INSTANCE.fromAcwSchema(acwSchema);
        LazyPage<AcwSchema> lazyPage = new LazyPage<AcwSchema>(current, size);
        LazyPage<AcwSchema> resultLazyPage = lazyLambdaStream
                .selectPage(LazyWrappers.<AcwSchemaDO>lambdaWrapperBean(acwSchemaDO)
                                .leftJoin(LazyWrappers.<AcwSchemaDO, AcwInstanceDO>lambdaWrapperJoin()
                                        .eq(AcwSchemaDO::getInstanceId, AcwInstanceDO::getId))

                                .onlyUseAs()
                                .as(AcwSchemaUo.class)
                                .as(AcwInstanceUo::getInstanceName, DatabaseSchemaDto::getInstanceName)
                        , lazyPage,
                        AcwSchema.class);
        return ResultFactory.successOf(resultLazyPage);
    }

    /**
     * 切换数据源查询数据库
     *
     * @param size
     * @param current
     * @param acwInstanceUo
     * @param acwSchema
     * @return
     */
    @Override
    public Result<LazyPage<AcwSchema>> switchSchemaRetrievePage(int size, int current, AcwInstanceUo acwInstanceUo, AcwSchema acwSchema) {

        String instanceId = acwSchema.getInstanceId();
        String schemaName = acwSchema.getSchemaName();
        LazyPage lazyPage = new LazyPage(current, size);
        // 查询schema
        LazyPage<AcwSchema> databaseSchemaUoLazyPage = lazyLambdaStream.selectPage(
                LazyWrappers.<Schemata>lambdaWrapper()
                        .like(!ObjectUtils.isEmpty(schemaName), Schemata::getSchemaName, schemaName + "%")
                        .notNull(Schemata::getSchemaName)
                        .orderByAsc(Schemata::getSchemaName)
                        .oas(String.valueOf(instanceId), DatabaseSchemaDto::getInstanceId)
                        .oas("false", DatabaseSchemaDto::getInitializeToLocal)
                        .as(Schemata::getSchemaName, DatabaseSchemaDto::getSchemaName)
                        .oas(acwInstanceUo.getInstanceName(), DatabaseSchemaDto::getInstanceName)
                ,
                lazyPage,
                AcwSchema.class
        );

        return ResultFactory.successOf(databaseSchemaUoLazyPage);
    }

    /**
     * 查询出所有的数据页面使用
     *
     * @param acwSchema
     * @return
     */
    @Override
    public Result<List<AcwSchema>> switchSchemaRetrieve(AcwSchema acwSchema) {

        String instanceId = acwSchema.getInstanceId();
        String schemaName = acwSchema.getSchemaName();
        LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
        // 查询schema
        List<AcwSchema> acwSchemaList = lazyLambdaStream.selectList(
                LazyWrappers.<Schemata>lambdaWrapper()
                        .like(!ObjectUtils.isEmpty(schemaName), Schemata::getSchemaName, schemaName + "%")
                        .notNull(Schemata::getSchemaName)
                        .oas(String.valueOf(instanceId), DatabaseSchemaDto::getInstanceId)
                        .oas("false", DatabaseSchemaDto::getInitializeToLocal)
                        .as(Schemata::getSchemaName, DatabaseSchemaDto::getSchemaName)
                        .oas(peek.getName(), DatabaseSchemaDto::getInstanceName)
                ,
                AcwSchema.class
        );

        return ResultFactory.successOf(acwSchemaList);
    }

    /**
     * @param acwSchema schema 查询信息
     * @return {@link Result<List<AcwSchema>>}
     */
    @Override
    public Result<List<AcwSchema>> findList(AcwSchema acwSchema) {
        String instanceId = acwSchema.getInstanceId();
        Boolean initializeToLocal = acwSchema.getInitializeToLocal();
        if (!ObjectUtils.isEmpty(instanceId) && !ObjectUtils.isEmpty(initializeToLocal) && !initializeToLocal) {
            // 切换数据库
            String schemaName = acwSchema.getSchemaName();
            // 查询schema
            List<AcwSchema> acwSchemaList = lazyLambdaStream.selectList(
                    LazyWrappers.<Schemata>lambdaWrapper()
                            .like(!ObjectUtils.isEmpty(schemaName), Schemata::getSchemaName, schemaName + "%")
                            .notNull(Schemata::getSchemaName)
                            .orderByAsc(Schemata::getSchemaName)
                            .oas(instanceId, AcwSchema::getInstanceId)
                            .oas("false", AcwSchema::getInitializeToLocal)
                            .as(Schemata::getSchemaName, AcwSchema::getSchemaName)
                    ,
                    AcwSchema.class
            );
            DynamicLazyDSContextHolder.clear();
            return ResultFactory.successOf(acwSchemaList);
        }

        AcwSchemaDO acwSchemaDO = AcwSchemaConverter.INSTANCE.fromAcwSchema(acwSchema);
        List<AcwSchema> acwSchemaList = lazyLambdaStream
                .selectList(LazyWrappers.<AcwSchemaDO>lambdaWrapperBean(acwSchemaDO)
                                .leftJoin(LazyWrappers.<AcwSchemaDO, AcwInstanceDO>lambdaWrapperJoin()
                                        .eq(AcwSchemaDO::getInstanceId, AcwInstanceDO::getId))
                                .onlyUseAs()
                                .as(AcwSchema.class)
                        ,
                        AcwSchema.class);
        return ResultFactory.successOf(acwSchemaList);
    }
}
