package com.wu.framework.authorization.platform.model.interface_;

import com.wu.framework.authorization.platform.infrastructure.entity.InterfaceDO;
import com.wu.framework.response.Result;
import com.wu.framework.response.repository.LazyCrudRepository;

import java.util.List;

/**
 * describe 接口
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository
 **/

public interface InterfaceRepository extends LazyCrudRepository<InterfaceDO,Interface, Integer> {


    /**
     * describe 新增接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Interface> story(Interface interface_);

    /**
     * describe 查询单个接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Interface> findOne(Interface interface_);

    /**
     * describe 查询多个接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<Interface>> findList(Interface interface_);

    /**
     * describe 删除接口
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Interface> delete(Interface interface_);

}