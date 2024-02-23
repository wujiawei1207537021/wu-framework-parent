package com.wu.smart.acw.server.infrastructure.converter;


import com.wu.smart.acw.server.domain.model.model.acw.instance.AcwInstance;
import com.wu.smart.acw.server.infrastructure.entity.AcwInstanceDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe 数据库服务器
 *
 * @author Jia wei Wu
 * @date 2023/10/24 03:15 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/
@Mapper
public interface AcwInstanceConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/
    AcwInstanceConverter INSTANCE = Mappers.getMapper(AcwInstanceConverter.class);

    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/
    AcwInstance toAcwInstance(AcwInstanceDO acwInstanceDO);

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/
    AcwInstanceDO fromAcwInstance(AcwInstance acwInstance);
}