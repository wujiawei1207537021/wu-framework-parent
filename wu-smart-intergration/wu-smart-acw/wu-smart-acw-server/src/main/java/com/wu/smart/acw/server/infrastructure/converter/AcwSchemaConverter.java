package com.wu.smart.acw.server.infrastructure.converter;


import com.wu.smart.acw.server.domain.model.model.acw.schema.AcwSchema;
import com.wu.smart.acw.server.infrastructure.entity.AcwSchemaDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe ACW 数据库库信息
 *
 * @author Jia wei Wu
 * @date 2023/10/24 03:15 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/
@Mapper
public interface AcwSchemaConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/
    AcwSchemaConverter INSTANCE = Mappers.getMapper(AcwSchemaConverter.class);

    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/
    AcwSchema toAcwSchema(AcwSchemaDO acwSchemaDO);

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/
    AcwSchemaDO fromAcwSchema(AcwSchema acwSchema);
}