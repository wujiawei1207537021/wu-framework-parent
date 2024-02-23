package com.wu.framework.authorization.platform.application.assembler;

import com.wu.framework.authorization.platform.application.command.RoleCommand;
import com.wu.framework.authorization.platform.application.dto.RoleDTO;
import com.wu.framework.authorization.platform.model.role.Role;

/**
 * describe 角色
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/

public class RoleDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static Role toRole(RoleCommand roleCommand) {
        if (null != roleCommand) {
            Role role = new Role();
            role.setCode(roleCommand.getCode());
            role.setCreateTime(roleCommand.getCreateTime());
            role.setId(roleCommand.getId());
            role.setIsDeleted(roleCommand.getIsDeleted());
            role.setStatus(roleCommand.isStatus());
            role.setName(roleCommand.getName());
            role.setParentCode(roleCommand.getParentCode());
            role.setUpdateTime(roleCommand.getUpdateTime());
            return role;
        }
        return null;
    }

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static RoleDTO fromRole(Role role) {
        if (null != role) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setCode(role.getCode());
            roleDTO.setCreateTime(role.getCreateTime());
            roleDTO.setId(role.getId());
            roleDTO.setIsDeleted(role.getIsDeleted());
            roleDTO.setStatus(role.isStatus());
            roleDTO.setName(role.getName());
            roleDTO.setParentCode(role.getParentCode());
            roleDTO.setUpdateTime(role.getUpdateTime());
            return roleDTO;
        }
        return null;
    }

}