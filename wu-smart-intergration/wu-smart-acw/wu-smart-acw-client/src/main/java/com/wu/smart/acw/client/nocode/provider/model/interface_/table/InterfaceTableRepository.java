package com.wu.smart.acw.client.nocode.provider.model.interface_.table;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 接口与表的关联 
 *
 * @author Jia wei Wu
 * @date 2023/08/15 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface InterfaceTableRepository {


    /**
     * describe 新增接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    Result<InterfaceTable> story(InterfaceTable interfaceTable);

    /**
     * describe 批量新增接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    Result<InterfaceTable> batchStory(List<InterfaceTable> interfaceTableList);

    /**
     * describe 查询单个接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    Result<InterfaceTable> findOne(InterfaceTable interfaceTable);

    /**
     * describe 查询多个接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    Result<List<InterfaceTable>> findList(InterfaceTable interfaceTable);

    /**
     * describe 分页查询多个接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    Result<LazyPage<InterfaceTable>> findPage(int size,int current,InterfaceTable interfaceTable);

    /**
     * describe 删除接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    Result<InterfaceTable> remove(InterfaceTable interfaceTable);

}