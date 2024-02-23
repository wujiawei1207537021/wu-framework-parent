package com.wu.smart.acw.server.application.impl;

import com.wu.framework.easy.excel.endpoint.EasyFilePoint;
import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.easy.excel.toolkit.DynamicEasyFileContextHolder;
import com.wu.framework.inner.layer.data.translation.NormalTranslation;
import com.wu.framework.inner.layer.util.FileUtil;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyFunctionWrappers;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperation;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.inner.lazy.persistence.util.SqlMessageFormatUtil;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.AcwSchemaUo;
import com.wu.smart.acw.server.application.AcwSchemaApplication;
import com.wu.smart.acw.server.application.assembler.AcwSchemaDTOAssembler;
import com.wu.smart.acw.server.application.command.AcwSchemaBatchExportCommand;
import com.wu.smart.acw.server.application.command.AcwSchemaDeriveViewCommand;
import com.wu.smart.acw.server.application.command.AcwSchemaImportDataCommand;
import com.wu.smart.acw.server.application.command.acw.schema.AcwSchemaQueryListCommand;
import com.wu.smart.acw.server.application.command.acw.schema.AcwSchemaStoryCommand;
import com.wu.smart.acw.server.application.dto.AcwSchemaDTO;
import com.wu.smart.acw.server.application.dto.SchemaSameColumnDTO;
import com.wu.smart.acw.server.domain.model.model.acw.instance.AcwInstanceRepository;
import com.wu.smart.acw.server.domain.model.model.acw.schema.AcwSchema;
import com.wu.smart.acw.server.domain.model.model.acw.schema.AcwSchemaRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/11/28 21:40
 */
@Service
public class AcwSchemaApplicationImpl implements AcwSchemaApplication {

    private final AcwSchemaRepository acwSchemaRepository;
    private final AcwInstanceRepository acwInstanceRepository;
    private final SmartLazyOperation smartLazyOperation;

    private final LazyLambdaStream lazyLambdaStream;

    public AcwSchemaApplicationImpl(AcwSchemaRepository acwSchemaRepository, AcwInstanceRepository acwInstanceRepository, SmartLazyOperation smartLazyOperation, LazyLambdaStream lazyLambdaStream) {
        this.acwSchemaRepository = acwSchemaRepository;
        this.acwInstanceRepository = acwInstanceRepository;
        this.smartLazyOperation = smartLazyOperation;
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * 重新初始化数据库
     *
     * @param instanceId 实例名称
     * @param schema     数据库
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/11/28 21:51
     **/
    @Override
    public Result reload(String instanceId, String schema) {
        // 数据库初始化
        acwSchemaRepository.reload(instanceId, schema);
        return ResultFactory.successOf();
    }

    /**
     * 获取schema分页
     *
     * @param size
     * @param current
     * @param acwSchemaQueryListCommand
     * @return
     */
    @NormalTranslation
    @Override
    public Result<LazyPage<AcwSchemaDTO>> retrievePage(int size, int current, AcwSchemaQueryListCommand acwSchemaQueryListCommand) {
        String instanceId = acwSchemaQueryListCommand.getInstanceId();
        Boolean initializeToLocal = acwSchemaQueryListCommand.getInitializeToLocal();
        AcwSchema acwSchema = AcwSchemaDTOAssembler.INSTANCE.toAcwSchema(acwSchemaQueryListCommand);
        try {
            if (!ObjectUtils.isEmpty(instanceId) && !ObjectUtils.isEmpty(initializeToLocal) && !initializeToLocal) {
                // 切换数据库
                acwInstanceRepository.switchInstance(instanceId);
            }
            Result<LazyPage<AcwSchema>> page = acwSchemaRepository.findPage(size, current, acwSchema);
            return page.convert(acwSchemaLazyPage -> acwSchemaLazyPage.convert(AcwSchemaDTOAssembler.INSTANCE::fromAcwSchema));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.errorOf(e.getMessage());
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /**
     * 查询出所有的数据页面使用
     *
     * @param acwSchemaQueryListCommand
     * @return
     */
    @NormalTranslation
    @Override
    public Result<List<AcwSchemaDTO>> findSchemaListWithSize(AcwSchemaQueryListCommand acwSchemaQueryListCommand) {
        String instanceId = acwSchemaQueryListCommand.getInstanceId();
        Boolean initializeToLocal = acwSchemaQueryListCommand.getInitializeToLocal();
        try {
            if (!ObjectUtils.isEmpty(instanceId) && !ObjectUtils.isEmpty(initializeToLocal) && !initializeToLocal) {
                // 切换数据库
                acwInstanceRepository.switchInstance(instanceId);
            }
            AcwSchema acwSchema = AcwSchemaDTOAssembler.INSTANCE.toAcwSchema(acwSchemaQueryListCommand);
            return acwSchemaRepository.findListWithSize(acwSchema).convert(acwSchemas -> acwSchemas.stream().map(AcwSchemaDTOAssembler.INSTANCE::fromAcwSchema).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.errorOf(e.getMessage());
        } finally {
            DynamicLazyDSContextHolder.clear();
        }

    }

    /**
     * 批量导出数据
     *
     * @param acwSchemaBatchExportCommandList schema 信息
     * @return
     * @author 吴佳伟
     * @date: 1.6.23 10:21
     */
    @Override
    public File batchExportSchemaData(List<AcwSchemaBatchExportCommand> acwSchemaBatchExportCommandList) {
        List<File> fileList = new ArrayList<>();
        // 切换数据源
        for (AcwSchemaBatchExportCommand acwSchemaBatchExportCommand : acwSchemaBatchExportCommandList) {

            String schema = acwSchemaBatchExportCommand.getSchema();
            String instanceId = acwSchemaBatchExportCommand.getInstanceId();

            ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
            classLazyDynamicEndpoint.setName(instanceId);
            classLazyDynamicEndpoint.setSchema(schema);

            DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
            try {
                // 获取执行数据
                File file = smartLazyOperation.saveSqlFile(schema);
                fileList.add(file);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DynamicLazyDSContextHolder.clear();
            }
        }
        // 返回结果
        File file = null;
        try {
            file = File.createTempFile(UUID.randomUUID().toString(), ".sql");
            FileUtil.aggregateFiles(fileList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        EasyFilePoint peek = new EasyFilePoint();
        peek.setFileType(EasyFile.FileType.FILE_TYPE);
        peek.setFileName("批量导出数据库" + acwSchemaBatchExportCommandList.stream().map(AcwSchemaBatchExportCommand::getSchema).collect(Collectors.joining("、")) + "数据");
        peek.setSuffix(".sql");
        DynamicEasyFileContextHolder.push(peek);
        return file;
    }

    /**
     * 数据库中获取相同字段集合
     *
     * @param instanceId
     * @param schema
     * @return
     */
    @Override
    public Result<List<SchemaSameColumnDTO>> findSchemaSameColumnList(String instanceId, String schema) {
        // 切换数据库
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(instanceId);
        classLazyDynamicEndpoint.setSchema(schema);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        // 统计表数量
        Long schemaTableCount = lazyLambdaStream.count(LazyWrappers.<LazyTableInfo>lambdaWrapper().eq(LazyTableInfo::getTableSchema, schema));
        // 查询表字段数据量
        List<SchemaSameColumnDTO> schemaSameColumnDTOList = lazyLambdaStream.selectList(LazyWrappers.<LazyColumn>lambdaWrapper().eq(LazyColumn::getTableSchema, schema).groupBy(LazyColumn::getTableName).onlyUseAs().functionAs(LazyFunctionWrappers.function().max(LazyColumn::getColumnName), SchemaSameColumnDTO::getNum).as(LazyColumn::getColumnName, SchemaSameColumnDTO::getColumnName), SchemaSameColumnDTO.class);
        schemaSameColumnDTOList = schemaSameColumnDTOList.stream().peek(schemaSameColumnDTO -> schemaSameColumnDTO.setAllTableHas(Objects.equals(schemaTableCount, schemaSameColumnDTO.getNum()))).collect(Collectors.toList());
        return ResultFactory.successOf(schemaSameColumnDTOList);
    }

    /**
     * 数据库衍生视图
     *
     * @param acwSchemaDeriveViewCommand
     * @return
     */
    @Override
    public Result schemaDeriveView(AcwSchemaDeriveViewCommand acwSchemaDeriveViewCommand) {
        // 创建数据库
        String instanceId = acwSchemaDeriveViewCommand.getInstanceId();
        String schemaName = acwSchemaDeriveViewCommand.getSchemaName();

        String columnName = acwSchemaDeriveViewCommand.getColumnName();
        String tableName = acwSchemaDeriveViewCommand.getTableName();
        String separator = acwSchemaDeriveViewCommand.getSeparator();

        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        lazyDynamicEndpoint.setName(instanceId);
        lazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        // 查询表中指定字段
        List<String> schema_suffix = lazyLambdaStream.executeSQL("select {0} from {1} group by {0}", String.class, columnName, tableName);


        // 获取所有的数据库表
        List<String> schemaTables = lazyLambdaStream.selectList(LazyWrappers.<LazyColumn>lambdaWrapper().eq(LazyColumn::getColumnName, columnName).eq(LazyColumn::getTableSchema, schemaName).groupBy(LazyColumn::getTableName).onlyUseAs().as(LazyColumn::getTableName, LazyColumn::getTableName), String.class

        );
        // 生成对应的schema

        for (String schemaSuffix : schema_suffix) {
            StringBuilder sqlStringBuilder = new StringBuilder();
            String viewSchema = schemaName + separator + schemaSuffix;
            // 创建数据库 create database if not exists upms_1;
            sqlStringBuilder.append("create database if not exists " + viewSchema + ";");
            // use upms_1;
            sqlStringBuilder.append("use " + viewSchema + ";");

            // 创建视图
            for (String schemaTable : schemaTables) {
                sqlStringBuilder.append(SqlMessageFormatUtil.format("CREATE OR REPLACE   view {0} AS SELECT * FROM {1}.{0} WHERE {2} = {3};", schemaTable, schemaName, columnName, schemaSuffix));
            }
            lazyLambdaStream.stringScriptRunner(sqlStringBuilder.toString());
        }


        DynamicLazyDSContextHolder.clear();
        return ResultFactory.successOf();
    }

    /**
     * 新增
     *
     * @param acwSchemaStoryCommand
     * @return
     */
    @Override
    public Result<AcwSchemaUo> save(AcwSchemaStoryCommand acwSchemaStoryCommand) {
        // 创建数据库
        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        lazyDynamicEndpoint.setName(acwSchemaStoryCommand.getInstanceId());
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        String format = "CREATE  DATABASE IF NOT EXISTS `{0}` CHARACTER SET {1} ;";
        persistenceRepository.setResultClass(Boolean.class);
        persistenceRepository.setExecutionType(LambdaTableType.CREATE);
        persistenceRepository.setQueryString(SqlMessageFormatUtil.format(format, acwSchemaStoryCommand.getSchemaName(), acwSchemaStoryCommand.getCharacterSet()));
        Object booleans = lazyLambdaStream.executeOne(persistenceRepository);
        DynamicLazyDSContextHolder.clear();
        return ResultFactory.successOf();
    }

    /**
     * 批量导入数据
     *
     * @param multipartFileList          导入的文件
     * @param acwSchemaImportDataCommand schema 信息
     * @return Result<Void>
     */
    @Override
    public Result<Void> batchImportSchemaData(List<MultipartFile> multipartFileList, AcwSchemaImportDataCommand acwSchemaImportDataCommand) {
        // 切换数据库
        String instanceId = acwSchemaImportDataCommand.getInstanceId();
        String schema = acwSchemaImportDataCommand.getSchema();
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(instanceId);
        classLazyDynamicEndpoint.setSchema(schema);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        Resource[] resources = multipartFileList.stream().map(MultipartFile::getResource).toArray(Resource[]::new);
        // 导入数据
        lazyLambdaStream.scriptRunner(resources);
        return ResultFactory.successOf();
    }

    /**
     * 查询数据库信息
     *
     * @param acwSchemaQueryListCommand 查询参数
     * @return 返回查询数据库信息
     */
    @NormalTranslation
    @Override
    public Result<List<AcwSchemaDTO>> findList(AcwSchemaQueryListCommand acwSchemaQueryListCommand) {
        String instanceId = acwSchemaQueryListCommand.getInstanceId();
        Boolean initializeToLocal = acwSchemaQueryListCommand.getInitializeToLocal();
        try {
            if (!ObjectUtils.isEmpty(instanceId) && !ObjectUtils.isEmpty(initializeToLocal) && !initializeToLocal) {
                // 切换数据库
                acwInstanceRepository.switchInstance(instanceId);
            }
            AcwSchema acwSchema = AcwSchemaDTOAssembler.INSTANCE.toAcwSchema(acwSchemaQueryListCommand);
            return acwSchemaRepository.findList(acwSchema).convert(acwSchemas -> acwSchemas.stream().map(AcwSchemaDTOAssembler.INSTANCE::fromAcwSchema).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.errorOf(e.getMessage());
        } finally {
            DynamicLazyDSContextHolder.clear();
        }

    }
}
