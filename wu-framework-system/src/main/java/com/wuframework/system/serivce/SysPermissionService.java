package com.wuframework.system.serivce;

import com.wuframework.pojo.qo.UniversalSearchQO;
import com.wuframework.response.Result;
import com.wuframework.system.common.entity.SysPermission;

public interface SysPermissionService {
    /**
     * 添加权限
     *
     * @param sysPermission
     * @return
     */
    Result save(SysPermission sysPermission);

    /**
     * 更新权限
     *
     * @param sysPermission
     * @return
     */
    Result update(SysPermission sysPermission);

    /**
     * 查询权限
     *
     * @param universalSearchQO
     * @return
     */
    Result queryList(UniversalSearchQO universalSearchQO);
}
