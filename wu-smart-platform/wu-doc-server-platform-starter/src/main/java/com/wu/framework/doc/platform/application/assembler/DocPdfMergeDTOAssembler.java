package com.wu.framework.doc.platform.application.assembler;


import com.wu.framework.doc.platform.application.command.DocPdfMergeCommand;
import com.wu.framework.doc.platform.application.dto.DocPdfMergeDTO;
import com.wu.framework.doc.platform.model.doc.pdf.merge.DocPdfMerge;

/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/06/03 05:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/

public class DocPdfMergeDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/
    public static DocPdfMerge toDocPdfMerge(DocPdfMergeCommand docPdfMergeCommand) {
        if (null != docPdfMergeCommand) {
        DocPdfMerge docPdfMerge = new DocPdfMerge();
           docPdfMerge.setCreateTime(docPdfMergeCommand.getCreateTime());
           docPdfMerge.setIsDeleted(docPdfMergeCommand.getIsDeleted());
           docPdfMerge.setMergeUrl(docPdfMergeCommand.getMergeUrl());
           docPdfMerge.setPdfUrlList(docPdfMergeCommand.getPdfUrlList());
           docPdfMerge.setUpdateTime(docPdfMergeCommand.getUpdateTime());
            return docPdfMerge;
        }
        return null;
    }

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/
    public static DocPdfMergeDTO fromDocPdfMerge(DocPdfMerge docPdfMerge) {
        if (null != docPdfMerge) {
        DocPdfMergeDTO docPdfMergeDTO = new DocPdfMergeDTO(); 
           docPdfMergeDTO.setCreateTime(docPdfMerge.getCreateTime());
           docPdfMergeDTO.setIsDeleted(docPdfMerge.getIsDeleted());
           docPdfMergeDTO.setMergeUrl(docPdfMerge.getMergeUrl());
           docPdfMergeDTO.setPdfUrlList(docPdfMerge.getPdfUrlList());
           docPdfMergeDTO.setUpdateTime(docPdfMerge.getUpdateTime());
            return docPdfMergeDTO;
        }
        return null;
    }

}