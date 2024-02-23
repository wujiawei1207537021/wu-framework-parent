package com.wu.framework.doc.platform.infrastructure.persistence;


import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudRepository;
import com.wu.framework.doc.platform.infrastructure.converter.DocPdfMergeConverter;
import com.wu.framework.doc.platform.infrastructure.entity.DocPdfMergeDO;
import com.wu.framework.doc.platform.model.doc.pdf.merge.DocPdfMerge;
import com.wu.framework.doc.platform.model.doc.pdf.merge.DocPdfMergeRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/06/03 05:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class DocPdfMergeRepositoryImpl extends AbstractLazyCrudRepository<DocPdfMergeDO,DocPdfMerge,Integer> implements DocPdfMergeRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

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
    public Result<DocPdfMerge> story(DocPdfMerge docPdfMerge) {
        DocPdfMergeDO docPdfMergeDO = DocPdfMergeConverter.fromDocPdfMerge(docPdfMerge);
        lazyLambdaStream.upsert(docPdfMergeDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/06/03 05:08 下午
     **/

    @Override
    public Result<DocPdfMerge> batchStory(List<DocPdfMerge> docPdfMergeList) {
        List<DocPdfMergeDO> docPdfMergeDOList = docPdfMergeList.stream().map(DocPdfMergeConverter::fromDocPdfMerge).collect(Collectors.toList());
        lazyLambdaStream.upsert(docPdfMergeDOList);
        return ResultFactory.successOf();
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
    public Result<DocPdfMerge> findOne(DocPdfMerge docPdfMerge) {
        DocPdfMergeDO docPdfMergeDO = DocPdfMergeConverter.fromDocPdfMerge(docPdfMerge);
        DocPdfMerge docPdfMergeOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(docPdfMergeDO), DocPdfMerge.class);
        return ResultFactory.successOf(docPdfMergeOne);
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
    public Result<List<DocPdfMerge>> findList(DocPdfMerge docPdfMerge) {
        DocPdfMergeDO docPdfMergeDO = DocPdfMergeConverter.fromDocPdfMerge(docPdfMerge);
        List<DocPdfMerge> docPdfMergeList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(docPdfMergeDO), DocPdfMerge.class);
        return ResultFactory.successOf(docPdfMergeList);
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
    public Result<LazyPage<DocPdfMerge>> findPage(int size,int current,DocPdfMerge docPdfMerge) {
        DocPdfMergeDO docPdfMergeDO = DocPdfMergeConverter.fromDocPdfMerge(docPdfMerge);
        LazyPage lazyPage = new LazyPage(current,size);
        LazyPage<DocPdfMerge> docPdfMergeLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(docPdfMergeDO),lazyPage, DocPdfMerge.class);
        return ResultFactory.successOf(docPdfMergeLazyPage);
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
    public Result<DocPdfMerge> remove(DocPdfMerge docPdfMerge) {
        DocPdfMergeDO docPdfMergeDO = DocPdfMergeConverter.fromDocPdfMerge(docPdfMerge);
        //  lazyLambdaStream.delete(docPdfMergeDO);
        return ResultFactory.successOf();
    }

}