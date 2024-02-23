package com.wu.smart.acw.server.infrastructure.converter;

import com.wu.smart.acw.server.domain.model.model.acw.table.AcwTable;
import com.wu.smart.acw.server.infrastructure.entity.AcwTableDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AcwTableConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwTableConverter INSTANCE = Mappers.getMapper(AcwTableConverter.class);

    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwTable toAcwTable(AcwTableDO acwTableDO);

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwTableDO fromAcwTable(AcwTable acwTable);
}