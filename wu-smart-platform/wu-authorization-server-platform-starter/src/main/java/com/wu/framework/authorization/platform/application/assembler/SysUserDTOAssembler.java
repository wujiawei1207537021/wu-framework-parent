package com.wu.framework.authorization.platform.application.assembler;


import com.wu.framework.authorization.platform.application.command.SysUserCommand;
import com.wu.framework.authorization.platform.application.dto.SysUserDTO;
import com.wu.framework.authorization.platform.model.sys.user.SysUser;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/

public class SysUserDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static SysUser toSysUser(SysUserCommand sysUserCommand) {
        if (null != sysUserCommand) {
            SysUser sysUser = new SysUser();
            sysUser.setColumnName(sysUserCommand.getColumnName());
            sysUser.setCreateTime(sysUserCommand.getCreateTime());
            sysUser.setId(sysUserCommand.getId());
            sysUser.setIsDeleted(sysUserCommand.getIsDeleted());
            sysUser.setPassword(sysUserCommand.getPassword());
            sysUser.setScope(sysUserCommand.getScope());
            sysUser.setStatus(sysUserCommand.getStatus());
            sysUser.setUpdateTime(sysUserCommand.getUpdateTime());
            sysUser.setUsername(sysUserCommand.getUsername());
            return sysUser;
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
    public static SysUserDTO fromSysUser(SysUser sysUser) {
        if (null != sysUser) {
            SysUserDTO sysUserDTO = new SysUserDTO();
            sysUserDTO.setColumnName(sysUser.getColumnName());
            sysUserDTO.setCreateTime(sysUser.getCreateTime());
            sysUserDTO.setId(sysUser.getId());
            sysUserDTO.setIsDeleted(sysUser.getIsDeleted());
            sysUserDTO.setPassword(sysUser.getPassword());
            sysUserDTO.setScope(sysUser.getScope());
            sysUserDTO.setStatus(sysUser.getStatus());
            sysUserDTO.setUpdateTime(sysUser.getUpdateTime());
            sysUserDTO.setUsername(sysUser.getUsername());
            return sysUserDTO;
        }
        return null;
    }

}