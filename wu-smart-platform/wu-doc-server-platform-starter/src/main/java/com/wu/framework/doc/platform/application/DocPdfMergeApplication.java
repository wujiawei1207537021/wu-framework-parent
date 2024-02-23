package com.wu.framework.doc.platform.application;


import com.wu.framework.doc.platform.application.command.DocPdfMergeCommand;
import com.wu.framework.doc.platform.model.doc.pdf.merge.DocPdfMerge;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/06/03 05:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface DocPdfMergeApplication {


    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<DocPdfMerge> story(DocPdfMergeCommand docPdfMergeCommand);

    /**
     * describe 更新
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<DocPdfMerge> updateOne(DocPdfMergeCommand docPdfMergeCommand);

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<DocPdfMerge> findOne(DocPdfMergeCommand docPdfMergeCommand);

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<List<DocPdfMerge>> findList(DocPdfMergeCommand docPdfMergeCommand);

    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<LazyPage<DocPdfMerge>> findPage(int size, int current, DocPdfMergeCommand docPdfMergeCommand);

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<DocPdfMerge> remove(DocPdfMergeCommand docPdfMergeCommand);

    /**
     * 合并pdf
     *
     * @param multipartFileList @return
     * @param targetName
     */
    File merge(List<MultipartFile> multipartFileList,String targetName);
}