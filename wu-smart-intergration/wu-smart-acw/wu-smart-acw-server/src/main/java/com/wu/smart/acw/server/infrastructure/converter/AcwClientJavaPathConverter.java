package com.wu.smart.acw.server.infrastructure.converter;

import com.wu.smart.acw.server.domain.model.acw.client.java.path.AcwClientJavaPath;
import com.wu.smart.acw.server.infrastructure.entity.AcwClientJavaPathDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe 客户端使用创建Java代码常用路径 
 *
 * @author Jia wei Wu
 * @date 2023/12/08 06:04 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface AcwClientJavaPathConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/
    AcwClientJavaPathConverter INSTANCE = Mappers.getMapper(AcwClientJavaPathConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param acwClientJavaPathDO 客户端使用创建Java代码常用路径实体对象     
     * @return {@link AcwClientJavaPath} 客户端使用创建Java代码常用路径领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/
    AcwClientJavaPath toAcwClientJavaPath(AcwClientJavaPathDO acwClientJavaPathDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param acwClientJavaPath 客户端使用创建Java代码常用路径领域对象     
     * @return {@link AcwClientJavaPathDO} 客户端使用创建Java代码常用路径实体对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/
     AcwClientJavaPathDO fromAcwClientJavaPath(AcwClientJavaPath acwClientJavaPath); 
}