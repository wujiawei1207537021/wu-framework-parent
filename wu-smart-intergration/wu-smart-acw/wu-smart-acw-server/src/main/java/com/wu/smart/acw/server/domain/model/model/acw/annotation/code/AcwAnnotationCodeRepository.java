package com.wu.smart.acw.server.domain.model.model.acw.annotation.code;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe ACW 使用的代码注解 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwAnnotationCodeRepository {


    /**
     * describe 新增ACW 使用的代码注解
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwAnnotationCode> story(AcwAnnotationCode acwAnnotationCode);

    /**
     * describe 批量新增ACW 使用的代码注解
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwAnnotationCode> batchStory(List<AcwAnnotationCode> acwAnnotationCodeList);

    /**
     * describe 查询单个ACW 使用的代码注解
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwAnnotationCode> findOne(AcwAnnotationCode acwAnnotationCode);

    /**
     * describe 查询多个ACW 使用的代码注解
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwAnnotationCode>> findList(AcwAnnotationCode acwAnnotationCode);

    /**
     * describe 分页查询多个ACW 使用的代码注解
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwAnnotationCode>> findPage(int size, int current, AcwAnnotationCode acwAnnotationCode);

    /**
     * describe 删除ACW 使用的代码注解
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwAnnotationCode> remove(AcwAnnotationCode acwAnnotationCode);

}