package com.wu.framework.authorization.platform.infrastructure.converter;

import com.wu.framework.authorization.platform.infrastructure.entity.RoleDO;
import com.wu.framework.authorization.platform.model.role.Role;

/**
 * describe 角色
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class RoleConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static Role toRole(RoleDO roleDO) {
        if (null != roleDO) {
            Role role = new Role();
            role.setCode(roleDO.getCode());
            role.setCreateTime(roleDO.getCreateTime());
            role.setId(roleDO.getId());
            role.setIsDeleted(roleDO.getIsDeleted());
            role.setName(roleDO.getName());
            role.setStatus(roleDO.isStatus());
            role.setParentCode(roleDO.getParentCode());
            role.setUpdateTime(roleDO.getUpdateTime());
            return role;
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
    public static RoleDO fromRole(Role role) {
        if (null != role) {
            RoleDO roleDO = new RoleDO();
            roleDO.setCode(role.getCode());
            roleDO.setCreateTime(role.getCreateTime());
            roleDO.setId(role.getId());
            roleDO.setIsDeleted(role.getIsDeleted());
            roleDO.setStatus(role.isStatus());
            roleDO.setName(role.getName());
            roleDO.setParentCode(role.getParentCode());
            roleDO.setUpdateTime(role.getUpdateTime());
            return roleDO;
        }
        return null;
    }

}