package com.wu.smart.acw.server.application.impl;


import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.layer.toolkit.DynamicLazyAttributeContextHolder;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.audit.AuditAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.smart.database.persistence.LazyOperationSmartAutoStuffed;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.enmus.DefaultResultCode;
import com.wu.smart.acw.server.application.AcwTableAutoStuffedRecordApplication;
import com.wu.smart.acw.server.application.assembler.AcwTableAutoStuffedRecordDTOAssembler;
import com.wu.smart.acw.server.application.command.AcwTableAutoStuffedRecordCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordQueryListCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordRemoveCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordStoryCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordUpdateCommand;
import com.wu.smart.acw.server.application.dto.AcwTableAutoStuffedRecordDTO;
import com.wu.smart.acw.server.domain.model.model.acw.instance.AcwInstanceRepository;
import com.wu.smart.acw.server.domain.model.model.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecord;
import com.wu.smart.acw.server.domain.model.model.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@LazyApplication
public class AcwTableAutoStuffedRecordApplicationImpl implements AcwTableAutoStuffedRecordApplication {


    private final AcwInstanceRepository acwInstanceRepository;

    private final LazyOperationSmartAutoStuffed lazyOperationSmartAutoStuffed;
    private final AcwTableAutoStuffedRecordRepository acwTableAutoStuffedRecordRepository;


    public AcwTableAutoStuffedRecordApplicationImpl(AcwInstanceRepository acwInstanceRepository,
                                                    LazyOperationSmartAutoStuffed lazyOperationSmartAutoStuffed,
                                                    AcwTableAutoStuffedRecordRepository acwTableAutoStuffedRecordRepository) {
        this.acwInstanceRepository = acwInstanceRepository;
        this.lazyOperationSmartAutoStuffed = lazyOperationSmartAutoStuffed;
        this.acwTableAutoStuffedRecordRepository = acwTableAutoStuffedRecordRepository;
    }

    /**
     * describe 查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/25 02:15 下午
     **/

    @Override
    public Result<List<AcwTableAutoStuffedRecordDTO>> findList(AcwTableAutoStuffedRecordQueryListCommand acwTableAutoStuffedRecordQueryListCommand) {
        AcwTableAutoStuffedRecord acwTableAutoStuffedRecord = AcwTableAutoStuffedRecordDTOAssembler.INSTANCE.toAcwTableAutoStuffedRecord(acwTableAutoStuffedRecordQueryListCommand);
        return acwTableAutoStuffedRecordRepository.findList(acwTableAutoStuffedRecord).convert(acwTableAutoStuffedRecords -> acwTableAutoStuffedRecords.stream().map(AcwTableAutoStuffedRecordDTOAssembler.INSTANCE::fromAcwTableAutoStuffedRecord).collect(Collectors.toList()));
    }

    /**
     * describe 分页查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/25 02:15 下午
     **/

    @Override
    public Result<LazyPage<AcwTableAutoStuffedRecordDTO>> findPage(int size, int current, AcwTableAutoStuffedRecordQueryListCommand acwTableAutoStuffedRecordQueryListCommand) {
        AcwTableAutoStuffedRecord acwTableAutoStuffedRecord = AcwTableAutoStuffedRecordDTOAssembler.INSTANCE.toAcwTableAutoStuffedRecord(acwTableAutoStuffedRecordQueryListCommand);
        return acwTableAutoStuffedRecordRepository.findPage(size, current, acwTableAutoStuffedRecord).convert(acwTableAutoStuffedRecordLazyPage -> {
            return acwTableAutoStuffedRecordLazyPage.convert(AcwTableAutoStuffedRecordDTOAssembler.INSTANCE::fromAcwTableAutoStuffedRecord);
        });
    }

    /**
     * describe 删除数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/25 02:15 下午
     **/

    @Override
    public Result<AcwTableAutoStuffedRecord> remove(AcwTableAutoStuffedRecordRemoveCommand acwTableAutoStuffedRecordRemoveCommand) {
        AcwTableAutoStuffedRecord acwTableAutoStuffedRecord = AcwTableAutoStuffedRecordDTOAssembler.INSTANCE.toAcwTableAutoStuffedRecord(acwTableAutoStuffedRecordRemoveCommand);
        return acwTableAutoStuffedRecordRepository.remove(acwTableAutoStuffedRecord);
    }

    /**
     * 新增
     *
     * @param acwTableAutoStuffedRecordStoryCommand@return
     */
    @Override
    public Result save(AcwTableAutoStuffedRecordStoryCommand acwTableAutoStuffedRecordStoryCommand) {

        DynamicLazyAttributeContextHolder.push(AuditAdapter.class, false);
        acwTableAutoStuffedRecordStoryCommand.setId(UUID.randomUUID().toString());
        AcwTableAutoStuffedRecord acwTableAutoStuffedRecord = AcwTableAutoStuffedRecordDTOAssembler.INSTANCE.toAcwTableAutoStuffedRecord(acwTableAutoStuffedRecordStoryCommand);
        acwTableAutoStuffedRecordRepository.story(acwTableAutoStuffedRecord);
        String instanceId = acwTableAutoStuffedRecord.getInstanceId();
        Long databaseSchemaId = acwTableAutoStuffedRecord.getDatabaseSchemaId();
        Long autoStuffedNum = acwTableAutoStuffedRecord.getAutoStuffedNum();
        String tableName = acwTableAutoStuffedRecord.getTableName();
        String schemaName = acwTableAutoStuffedRecord.getSchemaName();


        // 切换数据库
        acwInstanceRepository.switchInstance(instanceId);
        // 切换schema
        LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(peek.getName());
        classLazyDynamicEndpoint.setSchema(acwTableAutoStuffedRecord.getSchemaName());
        // 切换到目标数据源
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);

        // 执行数据库数据填充
        try {
            lazyOperationSmartAutoStuffed.stuffed(schemaName, tableName, autoStuffedNum);
            // 清除目标数据源
            DynamicLazyDSContextHolder.clear();
            // 更新填充状态
            acwTableAutoStuffedRecord.setStatus(true);
            acwTableAutoStuffedRecordRepository.story(acwTableAutoStuffedRecord);
            return ResultFactory.successOf();
        } catch (Throwable e) {
            e.printStackTrace();
            DynamicLazyDSContextHolder.clear();
            acwTableAutoStuffedRecord.setStatus(false);
            acwTableAutoStuffedRecordRepository.story(acwTableAutoStuffedRecord);
            return ResultFactory.of(DefaultResultCode.INTERNAL_SERVER_ERROR.code, e.getMessage());
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /**
     * 批量新增
     *
     * @param acwTableAutoStuffedRecordCommand
     * @return
     */
    @Override
    public Result batchSave(AcwTableAutoStuffedRecordCommand acwTableAutoStuffedRecordCommand) {
        for (String tableName : acwTableAutoStuffedRecordCommand.getTableNameList()) {
            AcwTableAutoStuffedRecordStoryCommand acwTableAutoStuffedRecordStoryCommand = AcwTableAutoStuffedRecordDTOAssembler.INSTANCE.toAcwTableAutoStuffedRecordStoryCommand(acwTableAutoStuffedRecordCommand);
            acwTableAutoStuffedRecordStoryCommand.setTableName(tableName);
            save(acwTableAutoStuffedRecordStoryCommand);
        }
        return ResultFactory.successOf();
    }

    /**
     * 根据更新
     *
     * @param acwTableAutoStuffedRecordUpdateCommand 实体对象
     * @return
     */
    @Override
    public Result<Void> update(AcwTableAutoStuffedRecordUpdateCommand acwTableAutoStuffedRecordUpdateCommand) {
        AcwTableAutoStuffedRecord acwTableAutoStuffedRecord = AcwTableAutoStuffedRecordDTOAssembler.INSTANCE.toAcwTableAutoStuffedRecord(acwTableAutoStuffedRecordUpdateCommand);
        acwTableAutoStuffedRecordRepository.story(acwTableAutoStuffedRecord);
        return ResultFactory.successOf();
    }

    /**
     * 根据主键ID 删除
     *
     * @param id id
     * @return
     */
    @Override
    public Result<Void> deleteById(String id) {
        AcwTableAutoStuffedRecord acwTableAutoStuffedRecord = new AcwTableAutoStuffedRecord();
        acwTableAutoStuffedRecord.setId(id);
        acwTableAutoStuffedRecordRepository.remove(acwTableAutoStuffedRecord);
        return ResultFactory.successOf();
    }

    /**
     * 根据主键ID 批量删除
     *
     * @param ids ids
     * @return
     */
    @Override
    public Result<Void> batchDeleteById(List<String> ids) {
        List<AcwTableAutoStuffedRecord> acwTableAutoStuffedRecordList = ids.stream().map(id -> {
            AcwTableAutoStuffedRecord acwTableAutoStuffedRecord = new AcwTableAutoStuffedRecord();
            acwTableAutoStuffedRecord.setId(id);
            return acwTableAutoStuffedRecord;
        }).toList();
        acwTableAutoStuffedRecordRepository.batchRemove(acwTableAutoStuffedRecordList);
        return ResultFactory.successOf();
    }
}
