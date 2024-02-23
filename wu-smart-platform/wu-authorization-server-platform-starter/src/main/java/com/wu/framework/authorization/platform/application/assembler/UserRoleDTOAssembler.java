package com.wu.framework.authorization.platform.application.assembler;


import com.wu.framework.authorization.platform.application.command.UserRoleCommand;
import com.wu.framework.authorization.platform.application.dto.UserRoleDTO;
import com.wu.framework.authorization.platform.model.user.role.UserRole;

/**
 * describe 用户角色关联关系
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/

public class UserRoleDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static UserRole toUserRole(UserRoleCommand userRoleCommand) {
        if (null != userRoleCommand) {
            UserRole userRole = new UserRole();
            userRole.setCreateTime(userRoleCommand.getCreateTime());
            userRole.setId(userRoleCommand.getId());
            userRole.setIsDeleted(userRoleCommand.getIsDeleted());
            userRole.setRoleId(userRoleCommand.getRoleId());
            userRole.setUpdateTime(userRoleCommand.getUpdateTime());
            userRole.setUserId(userRoleCommand.getUserId());
            return userRole;
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
    public static UserRoleDTO fromUserRole(UserRole userRole) {
        if (null != userRole) {
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setCreateTime(userRole.getCreateTime());
            userRoleDTO.setId(userRole.getId());
            userRoleDTO.setIsDeleted(userRole.getIsDeleted());
            userRoleDTO.setRoleId(userRole.getRoleId());
            userRoleDTO.setUpdateTime(userRole.getUpdateTime());
            userRoleDTO.setUserId(userRole.getUserId());
            return userRoleDTO;
        }
        return null;
    }

}