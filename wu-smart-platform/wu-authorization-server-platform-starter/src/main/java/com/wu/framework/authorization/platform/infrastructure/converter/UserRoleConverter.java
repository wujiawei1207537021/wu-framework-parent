package com.wu.framework.authorization.platform.infrastructure.converter;


import com.wu.framework.authorization.platform.infrastructure.entity.UserRoleDO;
import com.wu.framework.authorization.platform.model.user.role.UserRole;

/**
 * describe 用户角色关联关系
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class UserRoleConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static UserRole toUserRole(UserRoleDO userRoleDO) {
        if (null != userRoleDO) {
            UserRole userRole = new UserRole();
            userRole.setCreateTime(userRoleDO.getCreateTime());
            userRole.setId(userRoleDO.getId());
            userRole.setIsDeleted(userRoleDO.getIsDeleted());
            userRole.setRoleId(userRoleDO.getRoleId());
            userRole.setUpdateTime(userRoleDO.getUpdateTime());
            userRole.setUserId(userRoleDO.getUserId());
            return userRole;
        }
        return null;
    }

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static UserRoleDO fromUserRole(UserRole userRole) {
        if (null != userRole) {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setCreateTime(userRole.getCreateTime());
            userRoleDO.setId(userRole.getId());
            userRoleDO.setIsDeleted(userRole.getIsDeleted());
            userRoleDO.setRoleId(userRole.getRoleId());
            userRoleDO.setUpdateTime(userRole.getUpdateTime());
            userRoleDO.setUserId(userRole.getUserId());
            return userRoleDO;
        }
        return null;
    }

}