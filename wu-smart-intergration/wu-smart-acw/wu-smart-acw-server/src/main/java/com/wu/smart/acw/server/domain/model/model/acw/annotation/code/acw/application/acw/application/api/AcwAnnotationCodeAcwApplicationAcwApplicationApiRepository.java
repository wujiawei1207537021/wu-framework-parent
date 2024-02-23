package com.wu.smart.acw.server.domain.model.model.acw.annotation.code.acw.application.acw.application.api;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwAnnotationCodeAcwApplicationAcwApplicationApiRepository {


    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwAnnotationCodeAcwApplicationAcwApplicationApi> story(AcwAnnotationCodeAcwApplicationAcwApplicationApi acwAnnotationCodeAcwApplicationAcwApplicationApi);

    /**
     * describe 批量新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwAnnotationCodeAcwApplicationAcwApplicationApi> batchStory(List<AcwAnnotationCodeAcwApplicationAcwApplicationApi> acwAnnotationCodeAcwApplicationAcwApplicationApiList);

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwAnnotationCodeAcwApplicationAcwApplicationApi> findOne(AcwAnnotationCodeAcwApplicationAcwApplicationApi acwAnnotationCodeAcwApplicationAcwApplicationApi);

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwAnnotationCodeAcwApplicationAcwApplicationApi>> findList(AcwAnnotationCodeAcwApplicationAcwApplicationApi acwAnnotationCodeAcwApplicationAcwApplicationApi);

    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwAnnotationCodeAcwApplicationAcwApplicationApi>> findPage(int size, int current, AcwAnnotationCodeAcwApplicationAcwApplicationApi acwAnnotationCodeAcwApplicationAcwApplicationApi);

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwAnnotationCodeAcwApplicationAcwApplicationApi> remove(AcwAnnotationCodeAcwApplicationAcwApplicationApi acwAnnotationCodeAcwApplicationAcwApplicationApi);

}