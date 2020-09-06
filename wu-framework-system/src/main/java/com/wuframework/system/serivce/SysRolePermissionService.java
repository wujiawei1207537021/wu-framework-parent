package com.wuframework.system.serivce;

import com.wuframework.response.Result;

import java.util.List;

public interface SysRolePermissionService {
    /**
     * 添加角色权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    Result save(Integer roleId, List<Integer> permissionIds);

    /**
     * 更新角色权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    Result update(Integer roleId, List<Integer> permissionIds);

    /***
     * 根据角色ID查询权限
     * @param roleId
     * @return
     */
    Result query(Integer roleId);

}
