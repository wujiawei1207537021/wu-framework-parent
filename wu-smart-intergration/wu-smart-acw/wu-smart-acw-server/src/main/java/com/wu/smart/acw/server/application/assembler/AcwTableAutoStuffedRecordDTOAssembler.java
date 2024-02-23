package com.wu.smart.acw.server.application.assembler;


import com.wu.smart.acw.core.domain.uo.AcwTableAutoStuffedRecordUo;
import com.wu.smart.acw.server.application.command.AcwTableAutoStuffedRecordCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.*;
import com.wu.smart.acw.server.application.dto.AcwTableAutoStuffedRecordDTO;
import com.wu.smart.acw.server.domain.model.model.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe 数据库表填充记录
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/
@Mapper
public interface AcwTableAutoStuffedRecordDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwTableAutoStuffedRecordDTOAssembler INSTANCE = Mappers.getMapper(AcwTableAutoStuffedRecordDTOAssembler.class);

    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwTableAutoStuffedRecord toAcwTableAutoStuffedRecord(AcwTableAutoStuffedRecordStoryCommand acwTableAutoStuffedRecordStoryCommand);

    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwTableAutoStuffedRecord toAcwTableAutoStuffedRecord(AcwTableAutoStuffedRecordUpdateCommand acwTableAutoStuffedRecordUpdateCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwTableAutoStuffedRecord toAcwTableAutoStuffedRecord(AcwTableAutoStuffedRecordQueryOneCommand acwTableAutoStuffedRecordQueryOneCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwTableAutoStuffedRecord toAcwTableAutoStuffedRecord(AcwTableAutoStuffedRecordQueryListCommand acwTableAutoStuffedRecordQueryListCommand);

    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwTableAutoStuffedRecord toAcwTableAutoStuffedRecord(AcwTableAutoStuffedRecordRemoveCommand acwTableAutoStuffedRecordRemoveCommand);

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwTableAutoStuffedRecordDTO fromAcwTableAutoStuffedRecord(AcwTableAutoStuffedRecord acwTableAutoStuffedRecord);

    AcwTableAutoStuffedRecordUo toDatabaseTableAutoStuffedRecordUo(AcwTableAutoStuffedRecordCommand acwTableAutoStuffedRecordCommand);

    AcwTableAutoStuffedRecordStoryCommand toAcwTableAutoStuffedRecordStoryCommand(AcwTableAutoStuffedRecordCommand acwTableAutoStuffedRecordCommand);
}