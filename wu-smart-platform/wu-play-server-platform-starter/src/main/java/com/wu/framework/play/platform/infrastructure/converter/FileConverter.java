package com.wu.framework.play.platform.infrastructure.converter;

import com.wu.framework.play.platform.domain.model.file.File;
import com.wu.framework.play.platform.infrastructure.entity.FileDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe file 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface FileConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    FileConverter INSTANCE = Mappers.getMapper(FileConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param fileDO 实体对象     
     * @return {@link File} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    File toFile(FileDO fileDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param file 领域对象     
     * @return {@link FileDO} 实体对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     FileDO fromFile(File file); 
}