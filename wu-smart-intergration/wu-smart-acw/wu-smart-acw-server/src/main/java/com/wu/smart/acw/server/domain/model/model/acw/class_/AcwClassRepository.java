package com.wu.smart.acw.server.domain.model.model.acw.class_;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 对应的class字节码 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwClassRepository {


    /**
     * describe 新增对应的class字节码
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwClass> story(AcwClass acwClass);

    /**
     * describe 批量新增对应的class字节码
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwClass> batchStory(List<AcwClass> acwClassList);

    /**
     * describe 查询单个对应的class字节码
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwClass> findOne(AcwClass acwClass);

    /**
     * describe 查询多个对应的class字节码
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwClass>> findList(AcwClass acwClass);

    /**
     * describe 分页查询多个对应的class字节码
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwClass>> findPage(int size, int current, AcwClass acwClass);

    /**
     * describe 删除对应的class字节码
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwClass> remove(AcwClass acwClass);

}