package com.wu.framework.authorization.platform.infrastructure.converter;


import com.wu.framework.authorization.platform.infrastructure.entity.SysUserDO;
import com.wu.framework.authorization.platform.model.sys.user.SysUser;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class SysUserConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static SysUser toSysUser(SysUserDO sysUserDO) {
        if (null != sysUserDO) {
            SysUser sysUser = new SysUser();
            sysUser.setCreateTime(sysUserDO.getCreateTime());
            sysUser.setId(sysUserDO.getId());
            sysUser.setIsDeleted(sysUserDO.getIsDeleted());
            sysUser.setPassword(sysUserDO.getPassword());
            sysUser.setScope(sysUserDO.getScope());
            sysUser.setStatus(sysUserDO.getStatus());
            sysUser.setUpdateTime(sysUserDO.getUpdateTime());
            sysUser.setUsername(sysUserDO.getUsername());
            return sysUser;
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
    public static SysUserDO fromSysUser(SysUser sysUser) {
        if (null != sysUser) {
            SysUserDO sysUserDO = new SysUserDO();
            sysUserDO.setCreateTime(sysUser.getCreateTime());
            sysUserDO.setId(sysUser.getId());
            sysUserDO.setIsDeleted(sysUser.getIsDeleted());
            sysUserDO.setPassword(sysUser.getPassword());
            sysUserDO.setScope(sysUser.getScope());
            sysUserDO.setStatus(sysUser.getStatus());
            sysUserDO.setUpdateTime(sysUser.getUpdateTime());
            sysUserDO.setUsername(sysUser.getUsername());
            return sysUserDO;
        }
        return null;
    }

}