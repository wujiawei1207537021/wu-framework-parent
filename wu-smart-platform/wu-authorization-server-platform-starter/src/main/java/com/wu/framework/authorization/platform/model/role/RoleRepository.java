package com.wu.framework.authorization.platform.model.role;

import com.wu.framework.authorization.platform.infrastructure.entity.RoleDO;
import com.wu.framework.response.Result;
import com.wu.framework.response.repository.LazyCrudRepository;

import java.util.List;

/**
 * describe 角色
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository
 **/

public interface RoleRepository extends LazyCrudRepository<RoleDO,Role, Integer> {


    /**
     * describe 新增角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Role> story(Role role);

    /**
     * describe 查询单个角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Role> findOne(Role role);

    /**
     * describe 查询多个角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<Role>> findList(Role role);

    /**
     * describe 删除角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Role> delete(Role role);

}