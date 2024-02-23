package com.wu.smart.acw.server.domain.model.model.acw.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe ACW 方法 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwMethodRepository {


    /**
     * describe 新增ACW 方法
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwMethod> story(AcwMethod acwMethod);

    /**
     * describe 批量新增ACW 方法
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwMethod> batchStory(List<AcwMethod> acwMethodList);

    /**
     * describe 查询单个ACW 方法
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwMethod> findOne(AcwMethod acwMethod);

    /**
     * describe 查询多个ACW 方法
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwMethod>> findList(AcwMethod acwMethod);

    /**
     * describe 分页查询多个ACW 方法
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwMethod>> findPage(int size, int current, AcwMethod acwMethod);

    /**
     * describe 删除ACW 方法
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwMethod> remove(AcwMethod acwMethod);

}