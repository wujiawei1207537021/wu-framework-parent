package com.wu.smart.acw.server.domain.model.model.acw.code;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe ACW 行 code 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwCodeRepository {


    /**
     * describe 新增ACW 行 code
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwCode> story(AcwCode acwCode);

    /**
     * describe 批量新增ACW 行 code
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwCode> batchStory(List<AcwCode> acwCodeList);

    /**
     * describe 查询单个ACW 行 code
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwCode> findOne(AcwCode acwCode);

    /**
     * describe 查询多个ACW 行 code
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwCode>> findList(AcwCode acwCode);

    /**
     * describe 分页查询多个ACW 行 code
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwCode>> findPage(int size, int current, AcwCode acwCode);

    /**
     * describe 删除ACW 行 code
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwCode> remove(AcwCode acwCode);

}