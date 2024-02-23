package com.wu.framework.doc.platform.controller;

import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.doc.platform.application.DocPdfMergeApplication;
import com.wu.framework.doc.platform.application.command.DocPdfMergeCommand;
import com.wu.framework.doc.platform.infrastructure.entity.DocPdfMergeDO;
import com.wu.framework.doc.platform.model.doc.pdf.merge.DocPdfMerge;
import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/06/03 05:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "DOC-PDF提供者")
@EasyController("/doc/pdf/merge")
public class DocPdfMergeProvider extends AbstractLazyCrudProvider<DocPdfMergeDO,DocPdfMergeDO, Long> {

    @Autowired
    private DocPdfMergeApplication docPdfMergeApplication;

    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    @PostMapping("/story")
    public Result<DocPdfMerge> story(@RequestBody DocPdfMergeCommand docPdfMergeCommand) {
        return docPdfMergeApplication.story(docPdfMergeCommand);
    }

    /**
     * describe 更新
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    @PutMapping("/updateOne")
    public Result<DocPdfMerge> updateOne(@RequestBody DocPdfMergeCommand docPdfMergeCommand) {
        return docPdfMergeApplication.updateOne(docPdfMergeCommand);
    }

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    @GetMapping("/findOne")
    public Result<DocPdfMerge> findOne(@ModelAttribute DocPdfMergeCommand docPdfMergeCommand) {
        return docPdfMergeApplication.findOne(docPdfMergeCommand);
    }

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    @GetMapping("/findList")
    public Result<List<DocPdfMerge>> findList(@ModelAttribute DocPdfMergeCommand docPdfMergeCommand) {
        return docPdfMergeApplication.findList(docPdfMergeCommand);
    }

    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    @GetMapping("/findPage")
    public Result<LazyPage<DocPdfMerge>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                  @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute DocPdfMergeCommand docPdfMergeCommand) {
        return docPdfMergeApplication.findPage(size, current, docPdfMergeCommand);
    }

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    @DeleteMapping("/remove")
    public Result<DocPdfMerge> remove(@ModelAttribute DocPdfMergeCommand docPdfMergeCommand) {
        return docPdfMergeApplication.remove(docPdfMergeCommand);
    }


    /**
     * 合并pdf
     *
     * @param multipartFileList
     * @param targetName
     * @return
     */
    @EasyFile(fileType = EasyFile.FileType.FILE_TYPE)
    @PostMapping("/merge")
    public File merge(@RequestPart List<MultipartFile> multipartFileList,String targetName) {
        return docPdfMergeApplication.merge(multipartFileList, targetName);
    }

}