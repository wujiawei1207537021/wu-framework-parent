package com.wu.framework.play.platform.application.assembler;

import com.wu.framework.play.platform.domain.model.file.File;
import com.wu.framework.play.platform.application.command.file.FileRemoveCommand;
import com.wu.framework.play.platform.application.command.file.FileStoryCommand;
import com.wu.framework.play.platform.application.command.file.FileUpdateCommand;
import com.wu.framework.play.platform.application.command.file.FileQueryListCommand;
import com.wu.framework.play.platform.application.command.file.FileQueryOneCommand;
import com.wu.framework.play.platform.application.dto.FileDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe file 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface FileDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
    FileDTOAssembler INSTANCE = Mappers.getMapper(FileDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param fileStoryCommand 保存对象     
     * @return {@link File} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     File toFile(FileStoryCommand fileStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param fileUpdateCommand 更新对象     
     * @return {@link File} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     File toFile(FileUpdateCommand fileUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param fileQueryOneCommand 查询单个对象参数     
     * @return {@link File} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     File toFile(FileQueryOneCommand fileQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param fileQueryListCommand 查询集合对象参数     
     * @return {@link File} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     File toFile(FileQueryListCommand fileQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param fileRemoveCommand 删除对象参数     
     * @return {@link File} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     File toFile(FileRemoveCommand fileRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param file 领域对象     
     * @return {@link FileDTO} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/
     FileDTO fromFile(File file);
}