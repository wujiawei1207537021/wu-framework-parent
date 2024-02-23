package com.wu.smart.acw.server.application.assembler;

import com.wu.smart.acw.server.domain.model.acw.client.java.path.AcwClientJavaPath;
import com.wu.smart.acw.server.application.command.acw.client.java.path.AcwClientJavaPathRemoveCommand;
import com.wu.smart.acw.server.application.command.acw.client.java.path.AcwClientJavaPathStoryCommand;
import com.wu.smart.acw.server.application.command.acw.client.java.path.AcwClientJavaPathUpdateCommand;
import com.wu.smart.acw.server.application.command.acw.client.java.path.AcwClientJavaPathQueryListCommand;
import com.wu.smart.acw.server.application.command.acw.client.java.path.AcwClientJavaPathQueryOneCommand;
import com.wu.smart.acw.server.application.dto.AcwClientJavaPathDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe 客户端使用创建Java代码常用路径 
 *
 * @author Jia wei Wu
 * @date 2023/12/08 06:04 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface AcwClientJavaPathDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/
    AcwClientJavaPathDTOAssembler INSTANCE = Mappers.getMapper(AcwClientJavaPathDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param acwClientJavaPathStoryCommand 保存客户端使用创建Java代码常用路径对象     
     * @return {@link AcwClientJavaPath} 客户端使用创建Java代码常用路径领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/
     AcwClientJavaPath toAcwClientJavaPath(AcwClientJavaPathStoryCommand acwClientJavaPathStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param acwClientJavaPathUpdateCommand 更新客户端使用创建Java代码常用路径对象     
     * @return {@link AcwClientJavaPath} 客户端使用创建Java代码常用路径领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/
     AcwClientJavaPath toAcwClientJavaPath(AcwClientJavaPathUpdateCommand acwClientJavaPathUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param acwClientJavaPathQueryOneCommand 查询单个客户端使用创建Java代码常用路径对象参数     
     * @return {@link AcwClientJavaPath} 客户端使用创建Java代码常用路径领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/
     AcwClientJavaPath toAcwClientJavaPath(AcwClientJavaPathQueryOneCommand acwClientJavaPathQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param acwClientJavaPathQueryListCommand 查询集合客户端使用创建Java代码常用路径对象参数     
     * @return {@link AcwClientJavaPath} 客户端使用创建Java代码常用路径领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/
     AcwClientJavaPath toAcwClientJavaPath(AcwClientJavaPathQueryListCommand acwClientJavaPathQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param acwClientJavaPathRemoveCommand 删除客户端使用创建Java代码常用路径对象参数     
     * @return {@link AcwClientJavaPath} 客户端使用创建Java代码常用路径领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/
     AcwClientJavaPath toAcwClientJavaPath(AcwClientJavaPathRemoveCommand acwClientJavaPathRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param acwClientJavaPath 客户端使用创建Java代码常用路径领域对象     
     * @return {@link AcwClientJavaPathDTO} 客户端使用创建Java代码常用路径DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/
     AcwClientJavaPathDTO fromAcwClientJavaPath(AcwClientJavaPath acwClientJavaPath);
}