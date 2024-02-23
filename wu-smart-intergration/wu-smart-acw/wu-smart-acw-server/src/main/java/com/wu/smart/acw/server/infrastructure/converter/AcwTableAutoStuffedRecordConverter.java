package com.wu.smart.acw.server.infrastructure.converter;


import com.wu.smart.acw.server.domain.model.model.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecord;
import com.wu.smart.acw.server.infrastructure.entity.AcwTableAutoStuffedRecordDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe 数据库表填充记录
 *
 * @author Jia wei Wu
 * @date 2023/10/24 04:43 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/
@Mapper
public interface AcwTableAutoStuffedRecordConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 04:43 下午
     **/
    AcwTableAutoStuffedRecordConverter INSTANCE = Mappers.getMapper(AcwTableAutoStuffedRecordConverter.class);

    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 04:43 下午
     **/
    AcwTableAutoStuffedRecord toAcwTableAutoStuffedRecord(AcwTableAutoStuffedRecordDO acwTableAutoStuffedRecordDO);

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 04:43 下午
     **/
    AcwTableAutoStuffedRecordDO fromAcwTableAutoStuffedRecord(AcwTableAutoStuffedRecord acwTableAutoStuffedRecord);
}