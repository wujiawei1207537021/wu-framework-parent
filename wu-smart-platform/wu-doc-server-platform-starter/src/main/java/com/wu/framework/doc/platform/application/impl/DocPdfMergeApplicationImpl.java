package com.wu.framework.doc.platform.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.doc.platform.application.DocPdfMergeApplication;
import com.wu.framework.doc.platform.application.assembler.DocPdfMergeDTOAssembler;
import com.wu.framework.doc.platform.application.command.DocPdfMergeCommand;
import com.wu.framework.doc.platform.model.doc.pdf.merge.DocPdfMerge;
import com.wu.framework.doc.platform.model.doc.pdf.merge.DocPdfMergeRepository;
import com.wu.framework.doc.platform.utils.PdfboxUtil;
import com.wu.framework.easy.excel.endpoint.EasyFilePoint;
import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.easy.excel.toolkit.DynamicEasyFileContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.exceptions.RuntimeExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/06/03 05:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class DocPdfMergeApplicationImpl implements DocPdfMergeApplication {

    @Autowired
    DocPdfMergeRepository docPdfMergeRepository;

    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    @Override
    public Result<DocPdfMerge> story(DocPdfMergeCommand docPdfMergeCommand) {
        DocPdfMerge docPdfMerge = DocPdfMergeDTOAssembler.toDocPdfMerge(docPdfMergeCommand);
        return docPdfMergeRepository.story(docPdfMerge);
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

    @Override
    public Result<DocPdfMerge> updateOne(DocPdfMergeCommand docPdfMergeCommand) {
        DocPdfMerge docPdfMerge = DocPdfMergeDTOAssembler.toDocPdfMerge(docPdfMergeCommand);
        return docPdfMergeRepository.story(docPdfMerge);
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

    @Override
    public Result<DocPdfMerge> findOne(DocPdfMergeCommand docPdfMergeCommand) {
        DocPdfMerge docPdfMerge = DocPdfMergeDTOAssembler.toDocPdfMerge(docPdfMergeCommand);
        return docPdfMergeRepository.findOne(docPdfMerge);
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

    @Override
    public Result<List<DocPdfMerge>> findList(DocPdfMergeCommand docPdfMergeCommand) {
        DocPdfMerge docPdfMerge = DocPdfMergeDTOAssembler.toDocPdfMerge(docPdfMergeCommand);
        return docPdfMergeRepository.findList(docPdfMerge);
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

    @Override
    public Result<LazyPage<DocPdfMerge>> findPage(int size, int current, DocPdfMergeCommand docPdfMergeCommand) {
        DocPdfMerge docPdfMerge = DocPdfMergeDTOAssembler.toDocPdfMerge(docPdfMergeCommand);
        return docPdfMergeRepository.findPage(size, current, docPdfMerge);
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

    @Override
    public Result<DocPdfMerge> remove(DocPdfMergeCommand docPdfMergeCommand) {
        DocPdfMerge docPdfMerge = DocPdfMergeDTOAssembler.toDocPdfMerge(docPdfMergeCommand);
        return docPdfMergeRepository.remove(docPdfMerge);
    }

    /**
     * 合并pdf
     *
     * @param multipartFileList @return
     * @param targetName
     */
    @Override
    public File merge(List<MultipartFile> multipartFileList, String targetName) {
//        String pdfUrlList = docPdfMergeCommand.getPdfUrlList();
//        DocPdfMerge docPdfMerge = new DocPdfMerge();
//        docPdfMerge.setPdfUrlList(pdfUrlList);
//        Result<DocPdfMerge> one = docPdfMergeRepository.findOne(docPdfMerge);
//
//        if (one.hasSuccessData()) {
////            获取数据
//            DocPdfMerge data = one.getData();
//            return ResultFactory.successOf(data.getMergeUrl());
//        }
        // 导出数据
        List<File> fileList = new ArrayList<>();
        File pdfMergeFile = null;
        String targetPath = UUID.randomUUID() + ".pdf";
        try {
            for (MultipartFile multipartFile : multipartFileList) {
                File targetFile = File.createTempFile(UUID.randomUUID().toString(), ".pdf");
                multipartFile.transferTo(targetFile);
                fileList.add(targetFile);
            }
            // 合并数据
            pdfMergeFile = PdfboxUtil.mulFile2One(fileList, targetPath);

        } catch (Exception e) {
            RuntimeExceptionFactory.of(e.getMessage());
        } finally {
            if (!ObjectUtils.isEmpty(fileList)) {
                for (File file : fileList) {
                    file.delete();
                }
            }
//            if(null!=pdfMergeFile){
//                pdfMergeFile.delete();
//            }
        }

        EasyFilePoint peek = new EasyFilePoint();
        peek.setFileType(EasyFile.FileType.FILE_TYPE);
        peek.setFileName(!ObjectUtils.isEmpty(targetName) ? targetName : multipartFileList.stream().map(MultipartFile::getName).collect(Collectors.joining("-")));
        peek.setSuffix(".pdf");
        DynamicEasyFileContextHolder.push(peek);
        return pdfMergeFile;
    }
}