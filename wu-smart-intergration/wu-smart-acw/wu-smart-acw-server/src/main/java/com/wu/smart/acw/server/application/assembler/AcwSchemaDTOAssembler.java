package com.wu.smart.acw.server.application.assembler;


import com.wu.smart.acw.server.application.command.acw.schema.*;
import com.wu.smart.acw.server.application.dto.AcwSchemaDTO;
import com.wu.smart.acw.server.domain.model.model.acw.schema.AcwSchema;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe ACW 数据库库信息
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/
@Mapper
public interface AcwSchemaDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchemaDTOAssembler INSTANCE = Mappers.getMapper(AcwSchemaDTOAssembler.class);

    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchema toAcwSchema(AcwSchemaStoryCommand acwSchemaStoryCommand);

    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchema toAcwSchema(AcwSchemaUpdateCommand acwSchemaUpdateCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchema toAcwSchema(AcwSchemaQueryOneCommand acwSchemaQueryOneCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchema toAcwSchema(AcwSchemaQueryListCommand acwSchemaQueryListCommand);

    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchema toAcwSchema(AcwSchemaRemoveCommand acwSchemaRemoveCommand);

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchemaDTO fromAcwSchema(AcwSchema acwSchema);
}