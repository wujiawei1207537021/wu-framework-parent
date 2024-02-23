package com.wu.framework.authorization.platform.model.user.role;

import com.wu.framework.authorization.platform.infrastructure.entity.UserRoleDO;
import com.wu.framework.response.Result;
import com.wu.framework.response.repository.LazyCrudRepository;

import java.util.List;

/**
 * describe 用户角色关联关系
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository
 **/

public interface UserRoleRepository extends LazyCrudRepository<UserRoleDO,UserRole, Integer> {


    /**
     * describe 新增用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<UserRole> story(UserRole userRole);

    /**
     * describe 查询单个用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<UserRole> findOne(UserRole userRole);

    /**
     * describe 查询多个用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<UserRole>> findList(UserRole userRole);

    /**
     * describe 删除用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<UserRole> delete(UserRole userRole);

    /**
     * description: 批量存储用户角色
     * @param userId 用户ID
     * @param userRoleList  用户角色数据
     * @return
     * @author 吴佳伟
     * @date: 21.5.23 22:42
     */
    void batchStoryUserRole(Long userId, List<UserRole> userRoleList);
}