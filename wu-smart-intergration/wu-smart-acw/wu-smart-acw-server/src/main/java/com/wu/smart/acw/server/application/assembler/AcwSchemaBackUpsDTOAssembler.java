package com.wu.smart.acw.server.application.assembler;


import com.wu.smart.acw.server.application.command.AcwSchemaBackUpsCommand;
import com.wu.smart.acw.server.application.command.acw.schema.back.ups.*;
import com.wu.smart.acw.server.application.dto.AcwSchemaBackUpsDTO;
import com.wu.smart.acw.server.domain.model.database.schema.back.ups.DatabaseSchemaBackUps;
import com.wu.smart.acw.server.domain.model.model.acw.schema.back.ups.AcwSchemaBackUps;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe 数据库备份信息
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/
@Mapper
public interface AcwSchemaBackUpsDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchemaBackUpsDTOAssembler INSTANCE = Mappers.getMapper(AcwSchemaBackUpsDTOAssembler.class);

    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchemaBackUps toAcwSchemaBackUps(AcwSchemaBackUpsStoryCommand acwSchemaBackUpsStoryCommand);

    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchemaBackUps toAcwSchemaBackUps(AcwSchemaBackUpsUpdateCommand acwSchemaBackUpsUpdateCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchemaBackUps toAcwSchemaBackUps(AcwSchemaBackUpsQueryOneCommand acwSchemaBackUpsQueryOneCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchemaBackUps toAcwSchemaBackUps(AcwSchemaBackUpsQueryListCommand acwSchemaBackUpsQueryListCommand);

    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchemaBackUps toAcwSchemaBackUps(AcwSchemaBackUpsRemoveCommand acwSchemaBackUpsRemoveCommand);

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwSchemaBackUpsDTO fromAcwSchemaBackUps(AcwSchemaBackUps acwSchemaBackUps);
    AcwSchemaBackUpsDTO fromAcwSchemaBackUps(DatabaseSchemaBackUps databaseSchemaBackUps);

    DatabaseSchemaBackUps toDatabaseSchemaBackUps(AcwSchemaBackUpsCommand acwSchemaBackUpsCommand);
}