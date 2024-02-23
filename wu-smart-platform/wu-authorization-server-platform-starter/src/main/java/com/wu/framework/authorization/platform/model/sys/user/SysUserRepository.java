package com.wu.framework.authorization.platform.model.sys.user;

import com.wu.framework.authorization.platform.infrastructure.entity.SysUserDO;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.repository.LazyCrudRepository;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository
 **/

public interface SysUserRepository extends LazyCrudRepository<SysUserDO,SysUser, Integer> {


    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<SysUser> story(SysUser sysUser);

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<SysUser> findOne(SysUser sysUser);

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<SysUser>> findList(SysUser sysUser);

    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/21 11:16 晚上
     **/

    Result<LazyPage<SysUser>> findPage(int size, int current, SysUser sysUser);
    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<SysUser> delete(SysUser sysUser);

}