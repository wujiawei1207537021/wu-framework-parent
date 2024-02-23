package com.wu.framework.play.platform.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.play.platform.application.FileApplication;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.play.platform.domain.model.file.File;
import com.wu.framework.play.platform.application.command.file.FileRemoveCommand;
import com.wu.framework.play.platform.application.command.file.FileStoryCommand;
import com.wu.framework.play.platform.application.command.file.FileUpdateCommand;
import com.wu.framework.play.platform.application.command.file.FileQueryListCommand;
import com.wu.framework.play.platform.application.command.file.FileQueryOneCommand;
import com.wu.framework.play.platform.application.assembler.FileDTOAssembler;
import com.wu.framework.play.platform.application.dto.FileDTO;
import java.util.stream.Collectors;
import jakarta.annotation.Resource;
import com.wu.framework.play.platform.domain.model.file.FileRepository;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe file 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class FileApplicationImpl implements FileApplication {

    @Resource
    FileRepository fileRepository;
    /**
     * describe 新增
     *
     * @param fileStoryCommand 新增     
     * @return {@link Result<File>} 新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<File> story(FileStoryCommand fileStoryCommand) {
        File file = FileDTOAssembler.INSTANCE.toFile(fileStoryCommand);
        return fileRepository.story(file);
    }
    /**
     * describe 批量新增
     *
     * @param fileStoryCommandList 批量新增     
     * @return {@link Result<List<File>>} 新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<File>> batchStory(List<FileStoryCommand> fileStoryCommandList) {
        List<File> fileList = fileStoryCommandList.stream().map( FileDTOAssembler.INSTANCE::toFile).collect(Collectors.toList());
        return fileRepository.batchStory(fileList);
    }
    /**
     * describe 更新
     *
     * @param fileUpdateCommand 更新     
     * @return {@link Result<File>} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<File> updateOne(FileUpdateCommand fileUpdateCommand) {
        File file = FileDTOAssembler.INSTANCE.toFile(fileUpdateCommand);
        return fileRepository.story(file);
    }

    /**
     * describe 查询单个
     *
     * @param fileQueryOneCommand 查询单个     
     * @return {@link Result<FileDTO>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<FileDTO> findOne(FileQueryOneCommand fileQueryOneCommand) {
        File file = FileDTOAssembler.INSTANCE.toFile(fileQueryOneCommand);
        return fileRepository.findOne(file).convert(FileDTOAssembler.INSTANCE::fromFile);
    }

    /**
     * describe 查询多个
     *
     * @param fileQueryListCommand 查询多个     
     * @return {@link Result<List<FileDTO>>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<FileDTO>> findList(FileQueryListCommand fileQueryListCommand) {
        File file = FileDTOAssembler.INSTANCE.toFile(fileQueryListCommand);
        return fileRepository.findList(file)        .convert(files -> files.stream().map(FileDTOAssembler.INSTANCE::fromFile).collect(Collectors.toList())) ;
    }

    /**
     * describe 分页查询多个
     *
     * @param fileQueryListCommand 分页查询多个     
     * @return {@link Result<LazyPage<FileDTO>>} 分页DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<LazyPage<FileDTO>> findPage(int size,int current,FileQueryListCommand fileQueryListCommand) {
        File file = FileDTOAssembler.INSTANCE.toFile(fileQueryListCommand);
        return fileRepository.findPage(size,current,file)        .convert(page -> page.convert(FileDTOAssembler.INSTANCE::fromFile))            ;
    }

    /**
     * describe 删除
     *
     * @param fileRemoveCommand 删除     
     * @return {@link Result<File>}      
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<File> remove(FileRemoveCommand fileRemoveCommand) {
     File file = FileDTOAssembler.INSTANCE.toFile(fileRemoveCommand);
     return fileRepository.remove(file);
    }

}