package com.wu.framework.play.platform.application;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.play.platform.domain.model.file.File;
import com.wu.framework.play.platform.application.command.file.FileRemoveCommand;
import com.wu.framework.play.platform.application.command.file.FileStoryCommand;
import com.wu.framework.play.platform.application.command.file.FileUpdateCommand;
import com.wu.framework.play.platform.application.command.file.FileQueryListCommand;
import com.wu.framework.play.platform.application.command.file.FileQueryOneCommand;
import com.wu.framework.play.platform.application.dto.FileDTO;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe file 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface FileApplication {


    /**
     * describe 新增
     *
     * @param fileStoryCommand 新增     
     * @return {@link Result<File>} 新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<File> story(FileStoryCommand fileStoryCommand);

    /**
     * describe 批量新增
     *
     * @param fileStoryCommandList 批量新增     
     * @return {@link Result<List<File>>} 新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<File>> batchStory(List<FileStoryCommand> fileStoryCommandList);

    /**
     * describe 更新
     *
     * @param fileUpdateCommand 更新     
     * @return {@link Result<File>} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<File> updateOne(FileUpdateCommand fileUpdateCommand);

    /**
     * describe 查询单个
     *
     * @param fileQueryOneCommand 查询单个     
     * @return {@link Result<FileDTO>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<FileDTO> findOne(FileQueryOneCommand fileQueryOneCommand);

    /**
     * describe 查询多个
     *
     * @param fileQueryListCommand 查询多个     
     * @return {@link Result <List<FileDTO>>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result <List<FileDTO>> findList(FileQueryListCommand fileQueryListCommand);

    /**
     * describe 分页查询多个
     *
     * @param fileQueryListCommand 分页查询多个     
     * @return {@link Result <LazyPage<FileDTO>>} 分页DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result <LazyPage<FileDTO>> findPage(int size,int current,FileQueryListCommand fileQueryListCommand);

    /**
     * describe 删除
     *
     * @param fileRemoveCommand 删除     
     * @return {@link Result<File>}      
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<File> remove(FileRemoveCommand fileRemoveCommand);

}