package com.wu.framework.doc.platform.model.doc.pdf.merge;

import com.wu.framework.doc.platform.infrastructure.entity.DocPdfMergeDO;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.repository.LazyCrudRepository;

import java.util.List;
/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/06/03 05:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface DocPdfMergeRepository extends LazyCrudRepository<DocPdfMergeDO,DocPdfMerge,Integer> {


    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<DocPdfMerge> story(DocPdfMerge docPdfMerge);

    /**
     * describe 批量新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<DocPdfMerge> batchStory(List<DocPdfMerge> docPdfMergeList);

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<DocPdfMerge> findOne(DocPdfMerge docPdfMerge);

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<List<DocPdfMerge>> findList(DocPdfMerge docPdfMerge);

    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<LazyPage<DocPdfMerge>> findPage(int size,int current,DocPdfMerge docPdfMerge);

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    Result<DocPdfMerge> remove(DocPdfMerge docPdfMerge);

}