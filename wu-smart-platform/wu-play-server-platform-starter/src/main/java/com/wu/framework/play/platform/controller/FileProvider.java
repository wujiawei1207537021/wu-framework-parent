package com.wu.framework.play.platform.controller;

import com.wu.framework.easy.excel.endpoint.EasyFilePoint;
import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.easy.excel.toolkit.DynamicEasyFileContextHolder;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.play.platform.application.FileApplication;
import com.wu.framework.play.platform.application.command.file.*;
import com.wu.framework.play.platform.application.dto.FileDTO;
import com.wu.framework.play.platform.domain.FileUo;
import com.wu.framework.play.platform.domain.ResourceFileUo;
import com.wu.framework.play.platform.domain.model.file.File;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * describe file
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "提供者")
@EasyController("/play/file")
public class FileProvider {

    @Resource
    private FileApplication fileApplication;

    /**
     * describe 新增
     *
     * @param fileStoryCommand 新增
     * @return {@link Result<File>} 新增后领域对象
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "新增")
    @PostMapping("/story")
    public Result<File> story(@RequestBody FileStoryCommand fileStoryCommand) {
        return fileApplication.story(fileStoryCommand);
    }

    /**
     * describe 批量新增
     *
     * @param fileStoryCommandList 批量新增
     * @return {@link Result<List<File>>} 新增后领域对象集合
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "批量新增")
    @PostMapping("/batchStory")
    public Result<List<File>> batchStory(@RequestBody List<FileStoryCommand> fileStoryCommandList) {
        return fileApplication.batchStory(fileStoryCommandList);
    }

    /**
     * describe 更新
     *
     * @param fileUpdateCommand 更新
     * @return {@link Result<File>} 领域对象
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "更新")
    @PutMapping("/updateOne")
    public Result<File> updateOne(@RequestBody FileUpdateCommand fileUpdateCommand) {
        return fileApplication.updateOne(fileUpdateCommand);
    }

    /**
     * describe 查询单个
     *
     * @param fileQueryOneCommand 查询单个
     * @return {@link Result<FileDTO>} DTO对象
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "查询单个")
    @GetMapping("/findOne")
    public Result<FileDTO> findOne(@ModelAttribute FileQueryOneCommand fileQueryOneCommand) {
        return fileApplication.findOne(fileQueryOneCommand);
    }

    /**
     * describe 查询多个
     *
     * @param fileQueryListCommand 查询多个
     * @return {@link Result<List<FileDTO>>} DTO对象
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "查询多个")
    @GetMapping("/findList")
    public Result<List<FileDTO>> findList(@ModelAttribute FileQueryListCommand fileQueryListCommand) {
        return fileApplication.findList(fileQueryListCommand);
    }

    /**
     * describe 分页查询多个
     *
     * @param fileQueryListCommand 分页查询多个
     * @return {@link Result<LazyPage<FileDTO>>} 分页DTO对象
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "分页查询多个")
    @GetMapping("/findPage")
    public Result<LazyPage<FileDTO>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                              @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute FileQueryListCommand fileQueryListCommand) {
        return fileApplication.findPage(size, current, fileQueryListCommand);
    }

    /**
     * describe 删除
     *
     * @param fileRemoveCommand 删除
     * @return {@link Result<File>}
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Operation(summary = "删除")
    @DeleteMapping("/remove")
    public Result<File> remove(@ModelAttribute FileRemoveCommand fileRemoveCommand) {
        return fileApplication.remove(fileRemoveCommand);
    }

    /**
     * describe  新增
     *
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public Result<FileUo> add(@RequestBody MultipartFile data,
                              String name,
                              Long id,
                              String length,
                              String describe,
                              String type,
                              String uid) throws IOException {
        FileStoryCommand fileUo = new FileStoryCommand();
        fileUo.setName(name);
        fileUo.setId(id);
        fileUo.setData(data.getBytes());
        fileUo.setLength(length);
        fileUo.setDescribe(describe);
        fileUo.setType(type);
        fileUo.setUid(uid);
        fileApplication.story(fileUo);

        return ResultFactory.successOf();
    }


    /**
     * describe  根据主键ID查询
     *
     * @param id 主键ID
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @EasyFile
    @ApiOperation("根据主键ID查询")
    @GetMapping("/retrieve/data/{id}")
    public byte[] findMusicDataById(@PathVariable("id") Long id, HttpServletResponse httpServletResponse) {
        FileQueryOneCommand fileQueryOneCommand = new FileQueryOneCommand();
        fileQueryOneCommand.setId(id);
        return fileApplication.findOne(fileQueryOneCommand).applyOther(fileDTO -> {
            byte[] data = fileDTO.getData();
            EasyFilePoint easyFilePoint = new EasyFilePoint();
            easyFilePoint.setFileType(EasyFile.FileType.BYTE_TYPE);
            easyFilePoint.setFileName(fileDTO.getName());
            easyFilePoint.setSuffix(fileDTO.getType());
            DynamicEasyFileContextHolder.push(easyFilePoint);
            return data;
        });

    }

    /**
     * 获取本地文件
     *
     * @param path 文件绝对路径
     */
    @EasyFile(fileType = EasyFile.FileType.FILE_TYPE)
    @ApiOperation("获取本地文件")
    @GetMapping("/retrieve/local/file")
    public java.io.File findMusicDataById(@RequestParam("path") String path) {
        return new java.io.File(path);
    }

    /**
     * description:json生成Excel
     *
     * @param path 根路径数据
     * @return
     * @author 吴佳伟
     * @date: 4.7.23 23:09
     */
    @ApiOperation("获取系统文件")
    @GetMapping("/getFileResourceList")
    public Result<List<ResourceFileUo>> getFileResourceList(@RequestParam(defaultValue = "/") String path) {
        java.io.File file = new java.io.File(path);

        if (!file.exists()) {
            throw new IllegalArgumentException("无法获取文件树");
        }
        if (file.isDirectory()) {
            if (ObjectUtils.isEmpty(file.listFiles())) {
                return ResultFactory.successOf();
            }
            List<ResourceFileUo> resourceFileUoList = Arrays.stream(Objects.requireNonNull(file.listFiles())).sequential().map(resourceFile -> {
                        ResourceFileUo resourceFileUo = new ResourceFileUo();
                        resourceFileUo.setIsFile(resourceFile.isFile());
                        resourceFileUo.setRootPath(resourceFile.getParent());
                        resourceFileUo.setName(resourceFile.getName());
                        resourceFileUo.setAbsolutePath(resourceFile.getAbsolutePath());
                        return resourceFileUo;
                    }).sorted(Comparator.comparing(ResourceFileUo::getName))
                    .toList();

            return ResultFactory.successOf(resourceFileUoList);
        } else {
            return ResultFactory.errorOf("当前路径是个文件");
        }
    }
}