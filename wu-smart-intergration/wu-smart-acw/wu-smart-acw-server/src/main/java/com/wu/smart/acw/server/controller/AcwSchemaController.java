package com.wu.smart.acw.server.controller;

import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.dynamic.DynamicAdapter;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.core.domain.uo.AcwSchemaUo;
import com.wu.smart.acw.server.application.AcwSchemaApplication;
import com.wu.smart.acw.server.application.command.AcwSchemaBatchExportCommand;
import com.wu.smart.acw.server.application.command.AcwSchemaDeleteCommand;
import com.wu.smart.acw.server.application.command.AcwSchemaDeriveViewCommand;
import com.wu.smart.acw.server.application.command.AcwSchemaImportDataCommand;
import com.wu.smart.acw.server.application.command.acw.schema.AcwSchemaQueryListCommand;
import com.wu.smart.acw.server.application.command.acw.schema.AcwSchemaStoryCommand;
import com.wu.smart.acw.server.application.dto.AcwSchemaDTO;
import com.wu.smart.acw.server.application.dto.SchemaSameColumnDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * describe : 数据库库信息
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/5/6 21:50
 */
@Slf4j
@Tag(name = "ACW-数据库库信息")
@EasyController("/schema")
public class AcwSchemaController {
    protected final LazyLambdaStream lazyLambdaStream;
    private final AcwSchemaApplication acwSchemaApplication;
    private final DynamicAdapter dynamicAdapter;

    protected AcwSchemaController(LazyLambdaStream lazyLambdaStream, AcwSchemaApplication acwSchemaApplication, DynamicAdapter dynamicAdapter) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.acwSchemaApplication = acwSchemaApplication;
        this.dynamicAdapter = dynamicAdapter;
    }

    /**
     * describe  查询出分页的数据
     *
     * @param size                      分页大小
     * @param current                   当前页数
     * @param acwSchemaQueryListCommand
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("查询出分页的数据")
    @GetMapping("/retrieve/page")
    public Result<LazyPage<AcwSchemaDTO>> retrievePage(int size, int current, AcwSchemaQueryListCommand acwSchemaQueryListCommand) {
        return acwSchemaApplication.retrievePage(size, current, acwSchemaQueryListCommand);
    }

    /**
     * describe  查询出所有的数据页面使用
     *
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("查询出所有的数据包含数据大小")
    @GetMapping("/findListWithSize")
    public Result<List<AcwSchemaDTO>> findSchemaListWithSize(AcwSchemaQueryListCommand acwSchemaQueryListCommand) {
        return acwSchemaApplication.findSchemaListWithSize(acwSchemaQueryListCommand);
    }
    /**
     * describe  查询数据库信息
     *
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("查询数据库信息")
    @GetMapping("/findList")
    public Result<List<AcwSchemaDTO>> findList(AcwSchemaQueryListCommand acwSchemaQueryListCommand) {
        return acwSchemaApplication.findList(acwSchemaQueryListCommand);
    }

    /**
     * describe  新增
     *
     * @param acwSchemaStoryCommand 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/

    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public Result<AcwSchemaUo> save(@RequestBody AcwSchemaStoryCommand acwSchemaStoryCommand) {
        return acwSchemaApplication.save(acwSchemaStoryCommand);
    }

    /**
     * 数据库衍生视图
     *
     * @param acwSchemaDeriveViewCommand
     * @return
     */
    @ApiOperation(value = "数据库衍生视图")
    @PostMapping("/schemaDeriveView")
    public Result schemaDeriveView(@RequestBody AcwSchemaDeriveViewCommand acwSchemaDeriveViewCommand) {
        return acwSchemaApplication.schemaDeriveView(acwSchemaDeriveViewCommand);
    }

    /**
     * describe  根据主键ID 删除
     *
     * @param id 主键ID
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/

    @Deprecated
    @ApiOperation("根据主键ID 删除")
    @DeleteMapping("/{id}")
    public Result deleteById(Long id) {
        // 删除数据库
        AcwSchemaUo acwSchemaUo = lazyLambdaStream
                .selectOne(
                        LazyWrappers.<AcwSchemaUo>lambdaWrapper()
                                .eq(AcwSchemaUo::getId, id)
                );
        String schemaName = acwSchemaUo.getSchemaName();
        if (LazyDatabaseJsonMessage.specialSchema.contains(schemaName)) {
            return ResultFactory.errorOf("无法删除特殊数据库：" + schemaName);
        }
        // 获取schema所在的instance
        String instanceId = acwSchemaUo.getInstanceId();
        boolean exists = lazyLambdaStream.exists(LazyWrappers.<AcwInstanceUo>lambdaWrapper().eq(AcwInstanceUo::getId, instanceId)
                .eq(AcwInstanceUo::getIsDeleted, false));
        if (exists) {
            AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                    .eq(AcwInstanceUo::getId, instanceId)
                    .eq(AcwInstanceUo::getIsDeleted, false));
            LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
            lazyDataSourceProperties.setUrl(acwInstanceUo.getUrl());
            lazyDataSourceProperties.setUsername(acwInstanceUo.getUsername());
            lazyDataSourceProperties.setPassword(acwInstanceUo.getPassword());
            lazyDataSourceProperties.setAlias(acwInstanceUo.getId());
            dynamicAdapter.loading(lazyDataSourceProperties);
            ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
            classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
            DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
            // 切换数据源
            PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
            String format = "DROP DATABASE IF  EXISTS `%s`  ;";
            persistenceRepository.setResultClass(Boolean.class);
            persistenceRepository.setExecutionType(LambdaTableType.CREATE);
            persistenceRepository.setQueryString(String.format(format, schemaName));
            Object booleans = lazyLambdaStream.executeOne(persistenceRepository);
            DynamicLazyDSContextHolder.clear();
            return ResultFactory.successOf();
        } else {
            // 执行失败 无法找到数据源
            return ResultFactory.errorOf("执行失败 无法找到数据源");
        }

    }


    /**
     * describe  根据主键ID 删除
     *
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @DeleteMapping("/delete/batchDeleteInstanceSchema")
    public Result batchDeleteInstanceSchema(@RequestBody List<AcwSchemaDeleteCommand> acwSchemaDeleteCommandList) {
        Map<String, List<AcwSchemaDeleteCommand>> databaseSchemaDeleteCommandMap = acwSchemaDeleteCommandList
                .stream()
                .filter(acwSchemaDeleteCommand -> !LazyDatabaseJsonMessage.specialSchema.contains(acwSchemaDeleteCommand.getSchema()))
                .collect(
                        Collectors.groupingBy(AcwSchemaDeleteCommand::getInstanceId));

        // 删除数据库
        try {
            databaseSchemaDeleteCommandMap.forEach((instanceId, databaseSchemaDeleteCommands) -> {
                ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
                classLazyDynamicEndpoint.setName(instanceId);
                DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
                // 切换数据源

                String deleteSql = databaseSchemaDeleteCommands.stream().map(acwSchemaDeleteCommand -> {
                    String format = "DROP DATABASE IF  EXISTS `%s`  ;";
                    String schema = acwSchemaDeleteCommand.getSchema();
                    return String.format(format, schema);
                }).collect(Collectors.joining());
                log.warn("执行删除数据库脚本：" + deleteSql);
                lazyLambdaStream.stringScriptRunner(deleteSql);

            });
            return ResultFactory.successOf();
        } catch (Throwable throwable) {
            return ResultFactory.errorOf(throwable.getCause().getMessage());
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /**
     * 数据库中获取相同字段集合
     *
     * @param instanceId
     * @param schema
     * @return
     */
    @ApiOperation(value = "数据库中获取相同字段集合")
    @GetMapping("/findSchemaSameColumnList/{instanceId}/{schema}")
    public Result<List<SchemaSameColumnDTO>> findSchemaSameColumnList(@PathVariable("instanceId") String instanceId, @PathVariable("schema") String schema) {
        return acwSchemaApplication.findSchemaSameColumnList(instanceId, schema);
    }

    /**
     * 重新初始化数据库
     *
     * @param instanceId 实例名称
     * @param schema     数据库
     * @return
     */
    @ApiOperation(value = "重新初始化数据库服务器")
    @PatchMapping("/reload/{instanceId}/{schema}")
    public Result reload(@PathVariable("instanceId") String instanceId, @PathVariable("schema") String schema) {
        return acwSchemaApplication.reload(instanceId, schema);
    }

    /**
     * 批量导出数据
     *
     * @param acwSchemaBatchExportCommandList schema 信息
     * @return File 文件
     */
    @EasyFile(fileType = EasyFile.FileType.FILE_TYPE, suffix = "sql")
    @ApiOperation(value = "批量导出数据")
    @PostMapping("/batchExportSchemaData")
    public File batchExportSchemaData(@RequestBody List<AcwSchemaBatchExportCommand> acwSchemaBatchExportCommandList) {
        return acwSchemaApplication.batchExportSchemaData(acwSchemaBatchExportCommandList);
    }

    /**
     * 批量导入数据
     * @param multipartFileList  导入的文件
     * @param acwSchemaImportDataCommand schema 信息
     * @return Result<Void>
     */
    @ApiOperation(value = "批量导出数据")
    @PostMapping("/batchImportSchemaData")
    public Result<Void> batchImportSchemaData( @RequestPart List<MultipartFile> multipartFileList,
                                                AcwSchemaImportDataCommand acwSchemaImportDataCommand) {
        return acwSchemaApplication.batchImportSchemaData(multipartFileList,acwSchemaImportDataCommand);
    }


}
