package com.wu.smart.acw.server.application.assembler;


import com.wu.smart.acw.server.application.command.AcwInstanceBackUpsCommand;
import com.wu.smart.acw.server.application.command.acw.instance.back.ups.*;
import com.wu.smart.acw.server.application.dto.AcwInstanceBackUpsDTO;
import com.wu.smart.acw.server.domain.model.database.instance.back.ups.DatabaseInstanceBackUps;
import com.wu.smart.acw.server.domain.model.model.acw.instance.back.ups.AcwInstanceBackUps;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe 数据库实例备份信息
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/
@Mapper
public interface AcwInstanceBackUpsDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwInstanceBackUpsDTOAssembler INSTANCE = Mappers.getMapper(AcwInstanceBackUpsDTOAssembler.class);

    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwInstanceBackUps toAcwInstanceBackUps(AcwInstanceBackUpsStoryCommand acwInstanceBackUpsStoryCommand);

    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwInstanceBackUps toAcwInstanceBackUps(AcwInstanceBackUpsUpdateCommand acwInstanceBackUpsUpdateCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwInstanceBackUps toAcwInstanceBackUps(AcwInstanceBackUpsQueryOneCommand acwInstanceBackUpsQueryOneCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwInstanceBackUps toAcwInstanceBackUps(AcwInstanceBackUpsQueryListCommand acwInstanceBackUpsQueryListCommand);

    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwInstanceBackUps toAcwInstanceBackUps(AcwInstanceBackUpsRemoveCommand acwInstanceBackUpsRemoveCommand);

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwInstanceBackUpsDTO fromAcwInstanceBackUps(AcwInstanceBackUps acwInstanceBackUps);

    DatabaseInstanceBackUps toDatabaseInstanceBackUps(AcwInstanceBackUpsCommand acwInstanceBackUpsCommand);
}