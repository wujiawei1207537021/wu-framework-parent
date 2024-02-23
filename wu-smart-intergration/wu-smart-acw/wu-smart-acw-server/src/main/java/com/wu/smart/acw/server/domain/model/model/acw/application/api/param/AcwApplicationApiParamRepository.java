package com.wu.smart.acw.server.domain.model.model.acw.application.api.param;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe api参数 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwApplicationApiParamRepository {


    /**
     * describe 新增api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiParam> story(AcwApplicationApiParam acwApplicationApiParam);

    /**
     * describe 批量新增api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiParam> batchStory(List<AcwApplicationApiParam> acwApplicationApiParamList);

    /**
     * describe 查询单个api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiParam> findOne(AcwApplicationApiParam acwApplicationApiParam);

    /**
     * describe 查询多个api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwApplicationApiParam>> findList(AcwApplicationApiParam acwApplicationApiParam);

    /**
     * describe 分页查询多个api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwApplicationApiParam>> findPage(int size, int current, AcwApplicationApiParam acwApplicationApiParam);

    /**
     * describe 删除api参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiParam> remove(AcwApplicationApiParam acwApplicationApiParam);

}