package com.wu.smart.acw.server.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.easy.excel.endpoint.EasyFilePoint;
import com.wu.framework.easy.excel.toolkit.DynamicEasyExcelPointContextHolder;
import com.wu.framework.easy.excel.toolkit.DynamicEasyFileContextHolder;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.schema.SchemaMap;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.config.enums.WebArchitecture;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyColumnIndex;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.persistence.LazySmartLazyOperation;
import com.wu.framework.inner.lazy.persistence.conf.*;
import com.wu.framework.inner.lazy.persistence.conf.field.AbstractLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.index.AbstractLazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.FieldLazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.enmus.DefaultResultCode;
import com.wu.framework.response.exceptions.RuntimeExceptionFactory;
import com.wu.smart.acw.core.domain.dto.JavaModelDto;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.core.domain.uo.AcwTableUo;
import com.wu.smart.acw.server.application.AcwTableApplication;
import com.wu.smart.acw.server.application.assembler.AcwTableDTOAssembler;
import com.wu.smart.acw.server.application.command.AcwGenerateLocalJavaCommand;
import com.wu.smart.acw.server.application.command.AcwTableColumnCommand;
import com.wu.smart.acw.server.application.command.AcwTableColumnIndexCommand;
import com.wu.smart.acw.server.application.command.AcwTableCommand;
import com.wu.smart.acw.server.application.command.acw.table.AcwTableQueryListCommand;
import com.wu.smart.acw.server.application.dto.AcwExportTableStructureExcelDTO;
import com.wu.smart.acw.server.application.dto.AcwTableDTO;
import com.wu.smart.acw.server.domain.model.model.acw.instance.AcwInstanceRepository;
import com.wu.smart.acw.server.domain.model.model.acw.table.AcwTable;
import com.wu.smart.acw.server.domain.model.model.acw.table.AcwTableRepository;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * describe : 表操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/31 22:43
 */
@LazyApplication
public class AcwTableApplicationImpl implements AcwTableApplication {

    private final LazyLambdaStream lazyLambdaStream;
    private final LazyOperationConfig operationConfig;
    private final LazySmartLazyOperation lazySmartLazyOperation;

    private final AcwInstanceRepository acwInstanceRepository;
    private final AcwTableRepository acwTableRepository;


    public AcwTableApplicationImpl(LazyLambdaStream lazyLambdaStream, LazyOperationConfig operationConfig, LazySmartLazyOperation lazySmartLazyOperation, AcwInstanceRepository acwInstanceRepository, AcwTableRepository acwTableRepository) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.operationConfig = operationConfig;
        this.lazySmartLazyOperation = lazySmartLazyOperation;
        this.acwInstanceRepository = acwInstanceRepository;
        this.acwTableRepository = acwTableRepository;
    }


    /**
     * 查询所有数据
     *
     * @param acwTableQueryListCommand
     * @return
     */
    @Override
    public Result<List<AcwTableDTO>> retrieveAll(AcwTableQueryListCommand acwTableQueryListCommand) {
        if (acwTableQueryListCommand != null && acwTableQueryListCommand.getInitializeToLocal() != null && acwTableQueryListCommand.getInitializeToLocal()) {

        } else {
            // 切换数据源
            acwInstanceRepository.switchInstance(acwTableQueryListCommand.getInstanceId());
        }
        AcwTable acwTable = AcwTableDTOAssembler.INSTANCE.toAcwTable(acwTableQueryListCommand);


        Result<List<AcwTable>> acwTableRepositoryList = acwTableRepository.findList(acwTable);
        DynamicLazyDSContextHolder.clear();
        return acwTableRepositoryList.convert(acwTables -> acwTables.stream().map(AcwTableDTOAssembler.INSTANCE::fromAcwTable).collect(Collectors.toList()));
    }

    /**
     * 查询出分页的数据
     *
     * @param size
     * @param current
     * @param acwTableQueryListCommand
     * @return
     */
    @Override
    public Result<LazyPage<AcwTableDTO>> retrievePage(int size, int current, AcwTableQueryListCommand acwTableQueryListCommand) {
        if (acwTableQueryListCommand.getInitializeToLocal()) {

        } else {
            // 切换数据源
            acwInstanceRepository.switchInstance(acwTableQueryListCommand.getInstanceId());
        }
        AcwTable acwTable = AcwTableDTOAssembler.INSTANCE.toAcwTable(acwTableQueryListCommand);
        Result<LazyPage<AcwTable>> page = acwTableRepository.findPage(size, current, acwTable);
        DynamicLazyDSContextHolder.clear();
        return page.convert(acwTableLazyPage -> acwTableLazyPage.convert(AcwTableDTOAssembler.INSTANCE::fromAcwTable));
    }

    /**
     * describe 数据存储
     *
     * @param tableName 表名
     * @param data      数据
     * @return
     * @author Jia wei Wu
     * @date 2022/1/31 22:46
     **/
    @Override
    public Result dataStorage(String tableName, List<EasyHashMap> data) {
        for (EasyHashMap datum : data) {
            datum.setUniqueLabel(tableName);
            lazyLambdaStream.upsert(datum);
        }
        return ResultFactory.successOf();
    }

    /**
     * describe 根据表生成Java对应模型
     *
     * @param instanceId 实例ID
     * @param schemaName schema
     * @param tableName  表名称
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/26 21:37
     **/
    @Override
    public Result<JavaModelDto> generateJavaModel(String instanceId, String schemaName, String tableName) {

        // 切换数据源
        acwInstanceRepository.switchInstance(instanceId);
        try {
            // 查询表信息

            LazyTableInfo lazyTableInfo = lazyLambdaStream.selectOne(LazyWrappers.<LazyTableInfo>lambdaWrapper().eq(LazyTableInfo::getTableSchema, schemaName).eq(LazyTableInfo::getTableName, tableName));

            String tableComment = lazyTableInfo.getTableComment();
            // 查询表字段信息
            Collection<LazyColumn> lazyColumnList = lazyLambdaStream.selectList(LazyWrappers.<LazyColumn>lambdaWrapper().eq(LazyColumn::getTableSchema, schemaName).eq(LazyColumn::getTableName, tableName));
            // 生成数据

            List<LazyTableFieldEndpoint> fieldLazyTableFieldEndpointList = lazyColumnList.stream().map(lazyColumn -> {
                LazyTableFieldEndpoint fieldEndpoint = AbstractLazyTableFieldEndpoint.getInstance();
                String columnName = lazyColumn.getColumnName();
                fieldEndpoint.setColumnName(columnName);
                fieldEndpoint.setName(CamelAndUnderLineConverter.lineToHumpField(columnName));
                fieldEndpoint.setComment(lazyColumn.getColumnComment());
                fieldEndpoint.setColumnType(lazyColumn.getColumnType());
                fieldEndpoint.setDataType(lazyColumn.getDataType());
                fieldEndpoint.setNotNull(NormalUsedString.NO.equalsIgnoreCase(lazyColumn.getIsNullable()));
                fieldEndpoint.setDefaultValue(lazyColumn.getColumnDefault());
                String extra = lazyColumn.getExtra();
                fieldEndpoint.setExtra(extra);
                return fieldEndpoint;
            }).collect(Collectors.toList());
            ClassLazyTableEndpoint tableEndpoint = new ClassLazyTableEndpoint();
            tableEndpoint.setSchema(schemaName);
            tableEndpoint.setTableName(tableName);
            tableEndpoint.setClassName(CamelAndUnderLineConverter.lineToHumpClass(tableName));
            tableEndpoint.setFieldEndpoints(fieldLazyTableFieldEndpointList);
            tableEndpoint.setComment(tableComment);
            tableEndpoint.setPackageName(operationConfig.getReverseEngineering().getPackageName() + NormalUsedString.DOT + "domain");

            String entitySuffix = operationConfig.getReverseEngineering().getEntitySuffix();
            if (!ObjectUtils.isEmpty(entitySuffix)) {
                String className = tableEndpoint.getClassName();
                tableEndpoint.setClassName(className + entitySuffix);
            }

            StringBuilder javaController = LazyTableUtil.createJavaController(tableEndpoint, operationConfig.getReverseEngineering());
            StringBuilder javaEntity = LazyTableUtil.createJavaEntity(tableEndpoint, operationConfig.getReverseEngineering());
            StringBuilder javaService = LazyTableUtil.createJavaService(tableEndpoint, operationConfig.getReverseEngineering());
            StringBuilder javaServiceImpl = LazyTableUtil.createJavaServiceImpl(tableEndpoint, operationConfig.getReverseEngineering());
            StringBuilder javaMapper = LazyTableUtil.createJavaMapper(tableEndpoint, operationConfig.getReverseEngineering());
            StringBuilder javaMapperXml = LazyTableUtil.createJavaMapperXml(tableEndpoint, operationConfig.getReverseEngineering());

            JavaModelDto javaModelDto = new JavaModelDto();
            javaModelDto.setJavaEntity(javaEntity);
            javaModelDto.setJavaMapper(javaMapper);
            javaModelDto.setJavaController(javaController);
            javaModelDto.setJavaService(javaService);
            javaModelDto.setJavaServiceImpl(javaServiceImpl);
            javaModelDto.setJavaMapperXml(javaMapperXml);
            return ResultFactory.successOf(javaModelDto);
        } catch (Exception e) {
            throw e;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }

    }

    /**
     * 根据表生成本地Java对应模型
     *
     * @param acwGenerateLocalJavaCommand@return
     */
    @Override
    public Result generateLocalJava(AcwGenerateLocalJavaCommand acwGenerateLocalJavaCommand) {
        String instanceId = acwGenerateLocalJavaCommand.getInstanceId();
        List<String> tableList = acwGenerateLocalJavaCommand.getTableList();
        String schemaName = acwGenerateLocalJavaCommand.getSchemaName();
        String absolutePath = acwGenerateLocalJavaCommand.getAbsolutePath();
        String packageName = acwGenerateLocalJavaCommand.getPackageName();
        String prefix = acwGenerateLocalJavaCommand.getPrefix();// 文件前缀
        WebArchitecture webArchitecture = acwGenerateLocalJavaCommand.getWebArchitecture();
        OrmArchitecture ormArchitecture = acwGenerateLocalJavaCommand.getOrmArchitecture();
        // 切换数据源
        acwInstanceRepository.switchInstance(instanceId);
        LazyOperationConfig.ReverseEngineering reverseEngineering = operationConfig.getReverseEngineering();
        if (!ObjectUtils.isEmpty(absolutePath)) {
            reverseEngineering.setResourceFilePrefix(absolutePath + File.separator);
        }
        if (!ObjectUtils.isEmpty(packageName)) {
            reverseEngineering.setPackageName(packageName);
        }
        if (!ObjectUtils.isEmpty(webArchitecture)) {
            reverseEngineering.setWebArchitecture(webArchitecture);
        }
        if (!ObjectUtils.isEmpty(ormArchitecture)) {
            reverseEngineering.setOrmArchitecture(ormArchitecture);
        }
        try {
            // 查询表信息

            Map<String, LazyTableInfo> tableInfoMap = lazyLambdaStream
                    .selectList(
                            LazyWrappers.<LazyTableInfo>lambdaWrapper()
                                    .eq(LazyTableInfo::getTableSchema, schemaName)
                                    .in(LazyTableInfo::getTableName, tableList))
                    .stream()
                    .collect(Collectors.toMap(LazyTableInfo::getTableName, Function.identity(), (A, B) -> A));

            // 查询表字段信息
            Map<String/*表*/, List<LazyColumn>/*表字段*/> tableColumnMap = lazyLambdaStream.selectList(
                            LazyWrappers.<LazyColumn>lambdaWrapper()
                                    .eq(LazyColumn::getTableSchema, schemaName)
                                    .in(LazyColumn::getTableName, tableList))
                    .stream().collect(Collectors.groupingBy(LazyColumn::getTableName));


            // 生成数据
            tableColumnMap.forEach((tableName, lazyColumnList) -> {
                LazyTableInfo lazyTableInfo = tableInfoMap.get(tableName);
                List<LazyTableFieldEndpoint> fieldLazyTableFieldEndpointList = lazyColumnList.stream().map(lazyColumn -> {
                    LazyTableFieldEndpoint fieldEndpoint = AbstractLazyTableFieldEndpoint.getInstance();
                    String columnName = lazyColumn.getColumnName();
                    fieldEndpoint.setColumnName(columnName);
                    fieldEndpoint.setName(CamelAndUnderLineConverter.lineToHumpField(columnName));
                    fieldEndpoint.setComment(lazyColumn.getColumnComment());
                    fieldEndpoint.setColumnType(lazyColumn.getColumnType());
                    fieldEndpoint.setDataType(lazyColumn.getDataType());
                    fieldEndpoint.setNotNull(NormalUsedString.NO.equalsIgnoreCase(lazyColumn.getIsNullable()));
                    fieldEndpoint.setDefaultValue(lazyColumn.getColumnDefault());
                    String extra = lazyColumn.getExtra();
                    fieldEndpoint.setExtra(extra);
                    return fieldEndpoint;
                }).collect(Collectors.toList());
                ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint = new ReverseClassLazyTableEndpoint();
                reverseClassLazyTableEndpoint.setSchema(schemaName);
                reverseClassLazyTableEndpoint.setTableName(tableName);
                if (ObjectUtils.isEmpty(prefix)) {
                    reverseClassLazyTableEndpoint.setClassName(CamelAndUnderLineConverter.lineToHumpClass(tableName));
                } else {
                    reverseClassLazyTableEndpoint.setClassName(CamelAndUnderLineConverter.capitalizeFirstLetter(packageName) + CamelAndUnderLineConverter.lineToHumpClass(tableName));
                }
                reverseClassLazyTableEndpoint.setInLazyTableFieldEndpoints(fieldLazyTableFieldEndpointList);
                reverseClassLazyTableEndpoint.setOutLazyTableFieldEndpoints(fieldLazyTableFieldEndpointList);
                reverseClassLazyTableEndpoint.setComment(lazyTableInfo.getTableComment());
                reverseClassLazyTableEndpoint.setPackageName(reverseEngineering.getPackageName());

                String entitySuffix = reverseEngineering.getEntitySuffix();
                if (!ObjectUtils.isEmpty(entitySuffix)) {
                    String className = reverseClassLazyTableEndpoint.getClassName();
                    reverseClassLazyTableEndpoint.setClassName(className + entitySuffix);
                }

                LazyTableUtil.createJava(reverseClassLazyTableEndpoint, reverseEngineering);
            });


            return ResultFactory.successOf();
        } catch (Exception e) {
            throw e;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }

    }

    /**
     * describe 删除表
     *
     * @param id@return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/3 19:36
     **/
    @Override
    public Result deleteTable(Long id) {
        AcwTableUo acwTableUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwTableUo>lambdaWrapper().eq(AcwTableUo::getId, id));
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwTableUo.getInstanceId());
        classLazyDynamicEndpoint.setSchema(acwTableUo.getSchemaName());
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        // 删除表
        String format = "DROP TABLE IF EXISTS `%s`;";
        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        persistenceRepository.setExecutionType(LambdaTableType.INSERT);
        persistenceRepository.setResultClass(Boolean.class);
        persistenceRepository.setQueryString(String.format(format, acwTableUo.getTableName()));
        lazyLambdaStream.executeOne(persistenceRepository);
        DynamicLazyDSContextHolder.clear();
        lazyLambdaStream.delete(LazyWrappers.<AcwTableUo>lambdaWrapper().eq(AcwTableUo::getId, id));
        return ResultFactory.successOf();
    }

    /**
     * 删除表
     *
     * @param instanceId 实例ID
     * @param schemaName 数据库
     * @param tableName  表名称
     * @return
     */
    @Override
    public Result deleteTable(String instanceId, String schemaName, String tableName) {

        if (LazyDatabaseJsonMessage.specialSchema.contains(schemaName)) {
            return ResultFactory.of(DefaultResultCode.INTERNAL_SERVER_ERROR.getCode(), "无法删除特殊数据库:" + schemaName + "中的表:" + tableName);
        }
        // 切换数据源
        acwInstanceRepository.switchInstance(instanceId);

        LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(peek.getName());
        classLazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        // 删除表
        String format = "DROP TABLE IF EXISTS `%s`;";
        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        persistenceRepository.setExecutionType(LambdaTableType.INSERT);
        persistenceRepository.setResultClass(Boolean.class);
        persistenceRepository.setQueryString(String.format(format, tableName));
        lazyLambdaStream.executeOne(persistenceRepository);
        DynamicLazyDSContextHolder.clear();
        return ResultFactory.successOf();

    }

    /**
     * 批量删除
     *
     * @param acwTableCommandList
     * @return
     */
    @Override
    public Result batchDeleteTable(List<AcwTableCommand> acwTableCommandList) {
        for (AcwTableCommand acwTableCommand : acwTableCommandList) {
            String instanceId = acwTableCommand.getInstanceId();
            String schemaName = acwTableCommand.getSchemaName();
            String tableName = acwTableCommand.getTableName();
            deleteTable(instanceId, schemaName, tableName);
        }
        return ResultFactory.successOf();
    }

    /**
     * describe 新增表结构
     *
     * @param acwTableCommand 表
     * @return acwTableColumnUoList 表字段
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/6 00:22
     **/
    @Override
    public Result storage(AcwTableCommand acwTableCommand) {
        // 切换数据源
        String instanceId = acwTableCommand.getInstanceId();
        String schemaName = acwTableCommand.getSchemaName();
        List<AcwTableColumnIndexCommand> tableColumnIndexList = acwTableCommand.getTableColumnIndexList();
        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        lazyDynamicEndpoint.setName(instanceId);
        lazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        List<LazyColumnIndex> columnIndexList = tableColumnIndexList.stream().map(acwTableColumnIndexCommand -> {
            String indexName = acwTableColumnIndexCommand.getIndexName();
            String comment = acwTableColumnIndexCommand.getComment();
            String indexType = acwTableColumnIndexCommand.getIndexType();

            return
                    acwTableColumnIndexCommand.getColumnNameList().stream().map(columnName -> {
                        LazyColumnIndex lazyColumnIndex = new LazyColumnIndex();
                        lazyColumnIndex.setIndexName(indexName);
                        lazyColumnIndex.setIndexType(indexType);
                        lazyColumnIndex.setColumnName(columnName);
                        return lazyColumnIndex;
                    }).toList();

        }).collect(Collectors.flatMapping(Collection::stream, Collectors.toList()));
        Map<String/*字段column*/, ArrayList<LazyTableIndexEndpoint>/*字段索引*/> clolumnLazyTableIndexEndpointMap =
                columnIndexList
                        .stream()
                        .collect(Collectors.groupingBy(LazyColumnIndex::getColumnName,
                                Collectors.mapping(lazyColumnIndex -> {
                                    LazyTableIndexEndpoint lazyTableIndexEndpoint = FieldLazyTableIndexEndpoint.getInstance();
                                    String indexName = lazyColumnIndex.getIndexName();
                                    String indexType = lazyColumnIndex.getIndexType();
                                    lazyTableIndexEndpoint.setIndexName(indexName);
                                    lazyTableIndexEndpoint.setFieldIndexType(LayerField.LayerFieldType.valueOf(indexType));
                                    return lazyTableIndexEndpoint;
                                }, Collectors.toCollection(ArrayList::new))));

        List<AcwTableColumnCommand> acwTableColumnCommandList = acwTableCommand.getTableColumnList();
        // 创建数据库信息
        String tableName = acwTableCommand.getTableName();
        String tableComment = acwTableCommand.getTableComment();
        String engine = acwTableCommand.getEngine();
        ClassLazyTableEndpoint lazyTableEndpoint = new ClassLazyTableEndpoint();
        lazyTableEndpoint.setSchema(schemaName);
        lazyTableEndpoint.setComment(tableComment);
        lazyTableEndpoint.setTableName(tableName);
        lazyTableEndpoint.setEngine(LazyTable.Engine.valueOf(engine));
        List<LazyTableFieldEndpoint> fieldLazyTableFieldEndpoints = acwTableColumnCommandList.stream().map(databaseTableColumnUo -> {
            String columnName = databaseTableColumnUo.getColumnName();
            String columnType = databaseTableColumnUo.getColumnType();
            String columnComment = databaseTableColumnUo.getColumnComment();
            String dataType = databaseTableColumnUo.getDataType();
            String isNullable = databaseTableColumnUo.getIsNullable();
            Long characterMaximumLength = databaseTableColumnUo.getCharacterMaximumLength();
            if (!ObjectUtils.isEmpty(characterMaximumLength)) {
                columnType = dataType + NormalUsedString.LEFT_BRACKET + characterMaximumLength + NormalUsedString.RIGHT_BRACKET;
            }

            FieldLazyTableFieldEndpoint lazyTableFieldEndpoint = new FieldLazyTableFieldEndpoint();
            lazyTableFieldEndpoint.setColumnName(columnName);
            lazyTableFieldEndpoint.setColumnType(columnType);
            lazyTableFieldEndpoint.setComment(columnComment);
            lazyTableFieldEndpoint.setNotNull(Boolean.parseBoolean(isNullable));
            lazyTableFieldEndpoint.setExist(true);
            // 索引
            ArrayList<LazyTableIndexEndpoint> lazyTableIndexEndpoints = clolumnLazyTableIndexEndpointMap
                    .getOrDefault(columnName, new ArrayList<>());
            lazyTableFieldEndpoint.setLazyTableIndexEndpoints(lazyTableIndexEndpoints.toArray(LazyTableIndexEndpoint[]::new));
            return lazyTableFieldEndpoint;
        }).collect(Collectors.toList());
        lazyTableEndpoint.setFieldEndpoints(fieldLazyTableFieldEndpoints);

        lazyTableEndpoint.setExist(true);
        lazyLambdaStream.perfect(lazyTableEndpoint);
        // 归还原始数据源
        DynamicLazyDSContextHolder.clear();
        // 存储数据库表结构


        return ResultFactory.successOf();
    }

    /**
     * describe 下载 insert-sql
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/2 18:37
     **/
    @Override
    public String exportInsertSql(String instanceId, String schemaName, String tableName) {
        // 数据源切换
        AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper().eq(AcwInstanceUo::getIsDeleted, false).eq(AcwInstanceUo::getId, instanceId));

        Assert.notNull(acwInstanceUo, "无法找到服务器");
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
        classLazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        EasyFilePoint easyFilePoint = new EasyFilePoint();
        easyFilePoint.setFileName("导出表-[" + tableName + "]insert-sql");
        easyFilePoint.setSuffix(".sql");
        DynamicEasyFileContextHolder.push(easyFilePoint);
        // 查询出 insert sql
        try {
            return lazySmartLazyOperation.exportInsertSql(schemaName, tableName);
        } catch (Exception e) {
            e.printStackTrace();
            RuntimeExceptionFactory.of(e);
            return null;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
        // 数据导出
    }

    /**
     * describe 下载 upsert-sql
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/2 22:21
     **/
    @Override
    public String exportUpsertSql(String instanceId, String schemaName, String tableName) {
        // 数据源切换
        AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper().eq(AcwInstanceUo::getIsDeleted, false).eq(AcwInstanceUo::getId, instanceId));

        Assert.notNull(acwInstanceUo, "无法找到服务器");
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
        classLazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        EasyFilePoint easyFilePoint = new EasyFilePoint();
        easyFilePoint.setFileName("导出表-[" + tableName + "]upsert-sql");
        easyFilePoint.setSuffix(".sql");
        DynamicEasyFileContextHolder.push(easyFilePoint);
        // 查询出 upsert sql
        try {
            return lazySmartLazyOperation.exportUpsertSql(schemaName, tableName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 数据导出
            DynamicLazyDSContextHolder.clear();
        }

    }

    /**
     * describe 下载表结构Excel
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/5 21:11
     **/
    @Override
    public List<AcwExportTableStructureExcelDTO> exportTableStructureExcel(String instanceId, String schemaName, String tableName) {
        // 数据源切换
        AcwInstanceUo acwInstanceUo = lazyLambdaStream
                .selectOne(
                        LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                                .eq(AcwInstanceUo::getIsDeleted, false)
                                .eq(AcwInstanceUo::getId, instanceId)
                );

        Assert.notNull(acwInstanceUo, "无法找到服务器");
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
        classLazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        // 查询出 表结构
        List<AcwExportTableStructureExcelDTO.LazyExcelColumn> lazyColumnList = lazyLambdaStream
                .selectList(LazyWrappers.<LazyColumn>lambdaWrapper()
                        .eqIgnoreEmpty(LazyColumn::getTableName, tableName)
                        .eq(LazyColumn::getTableSchema, schemaName), AcwExportTableStructureExcelDTO.LazyExcelColumn.class);
        // 数据导出
        DynamicLazyDSContextHolder.clear();
        EasyExcelPoint easyExcelPoint = new EasyExcelPoint();
        if (ObjectUtils.isEmpty(tableName)) {
            if (ObjectUtils.isEmpty(schemaName)) {
                easyExcelPoint.setFileName("实例[" + instanceId + "]结构");
            } else {
                easyExcelPoint.setFileName("数据库[" + schemaName + "]结构");
            }
        } else {
            easyExcelPoint.setFileName("表" + tableName + "结构");
        }

        easyExcelPoint.setTitleFixedHead(false);
        DynamicEasyExcelPointContextHolder.push(easyExcelPoint);

        List<AcwExportTableStructureExcelDTO> acwExportTableStructureExcelDTOList =

                lazyColumnList.stream().collect(Collectors.groupingBy(AcwExportTableStructureExcelDTO.LazyExcelColumn::getTableName))
                        .entrySet().stream().map(stringListEntry -> {
                            String key = stringListEntry.getKey();
                            List<AcwExportTableStructureExcelDTO.LazyExcelColumn> excelColumnList = stringListEntry.getValue();
                            AcwExportTableStructureExcelDTO acwExportTableStructureExcelDTO = new AcwExportTableStructureExcelDTO();
                            acwExportTableStructureExcelDTO.setTableName(key);
                            acwExportTableStructureExcelDTO.setExcelColumnList(excelColumnList);
                            return acwExportTableStructureExcelDTO;
                        }).collect(Collectors.toList());

        return acwExportTableStructureExcelDTOList;
    }

    /**
     * 下载表结构SQL
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     */
    @Override
    public String exportTableStructureSql(String instanceId, String schemaName, String tableName) {
        // 数据源切换
        AcwInstanceUo acwInstanceUo = lazyLambdaStream
                .selectOne(
                        LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                                .eq(AcwInstanceUo::getIsDeleted, false)
                                .eq(AcwInstanceUo::getId, instanceId)
                );

        Assert.notNull(acwInstanceUo, "无法找到服务器");
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
        classLazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        try {
            // 查询出 表结构

            // 当前数据库
            // 获取数据库中所有的表
            String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";
            List<EasyHashMap> allTables = lazyLambdaStream.executeSQL(String.format(sqlSelectTable, schemaName, schemaName), EasyHashMap.class);


            // 数据库数据

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(NormalUsedString.NEWLINE);
            // -- use information_schema;
            stringBuilder.append(String.format("use %s;", schemaName));


            for (EasyHashMap table : allTables) {
                String tableN = table.get("tableName").toString();
                // 创建建表语句  show create table lazy.english_word;
                SchemaMap createTableSchema = lazyLambdaStream.executeSQLForBean("show create table {0}", SchemaMap.class, tableN);
                String createTableSql = String.valueOf(createTableSchema.get("Create Table"));
//         CREATE TABLE `table`  ====>>>>>>>   CREATE TABLE IF NOT EXISTS `table`
                stringBuilder.append(NormalUsedString.NEWLINE);
                createTableSql = createTableSql.replaceAll("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
                stringBuilder.append(createTableSql);
                stringBuilder.append(NormalUsedString.SEMICOLON);
                stringBuilder.append(NormalUsedString.NEWLINE);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DynamicLazyDSContextHolder.clear();
        }


    }
}
