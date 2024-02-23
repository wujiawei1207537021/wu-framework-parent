package com.wu.framework.doc.platform.infrastructure.converter;


import com.wu.framework.doc.platform.infrastructure.entity.DocPdfMergeDO;
import com.wu.framework.doc.platform.model.doc.pdf.merge.DocPdfMerge;

/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/06/03 05:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/

public class DocPdfMergeConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/
    public static DocPdfMerge toDocPdfMerge(DocPdfMergeDO docPdfMergeDO) {
        if (null != docPdfMergeDO) {
        DocPdfMerge docPdfMerge = new DocPdfMerge(); 
           docPdfMerge.setCreateTime(docPdfMergeDO.getCreateTime());
           docPdfMerge.setIsDeleted(docPdfMergeDO.getIsDeleted());
           docPdfMerge.setMergeUrl(docPdfMergeDO.getMergeUrl());
           docPdfMerge.setPdfUrlList(docPdfMergeDO.getPdfUrlList());
           docPdfMerge.setUpdateTime(docPdfMergeDO.getUpdateTime());
            return docPdfMerge;
        }
        return null;
    }

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/
    public static DocPdfMergeDO fromDocPdfMerge(DocPdfMerge docPdfMerge) {
        if (null != docPdfMerge) {
        DocPdfMergeDO docPdfMergeDO = new DocPdfMergeDO(); 
           docPdfMergeDO.setCreateTime(docPdfMerge.getCreateTime());
           docPdfMergeDO.setIsDeleted(docPdfMerge.getIsDeleted());
           docPdfMergeDO.setMergeUrl(docPdfMerge.getMergeUrl());
           docPdfMergeDO.setPdfUrlList(docPdfMerge.getPdfUrlList());
           docPdfMergeDO.setUpdateTime(docPdfMerge.getUpdateTime());
            return docPdfMergeDO;
        }
        return null;
    }

}