package com.wuframework.system.serivce.def;

import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.system.common.entity.SysRolePermission;
import com.wuframework.system.persistence.jpa.SysPermissionJpaRepository;
import com.wuframework.system.persistence.jpa.SysRolePermissionJpaRepository;
import com.wuframework.system.serivce.SysRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("defaultSysRolePermissionService")
public class DefaultSysRolePermissionService implements SysRolePermissionService {

    @Resource
    private SysRolePermissionJpaRepository sysRolePermissionJpaRepository;
    @Resource
    private  SysPermissionJpaRepository sysPermissionJpaRepository;

    /**
     * 添加角色权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @Override
    public Result save(Integer roleId, List<Integer> permissionIds) {
        List<SysRolePermission> sysRolePermissions = new ArrayList<>();
        for (Integer permissionId : permissionIds) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermission.setRoleId(roleId);
            sysRolePermissions.add(sysRolePermission);
        }
        sysRolePermissionJpaRepository.saveAll(sysRolePermissions);
        return ResultFactory.successOf();
    }

    /**
     * 更新角色权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @Transactional
    @Override
    public Result update(Integer roleId, List<Integer> permissionIds) {
        sysRolePermissionJpaRepository.deleteByRoleId(roleId);
        this.save(roleId, permissionIds);
        return ResultFactory.successOf();
    }

    /**
     * 根据角色ID查询权限
     * @param roleId
     * @return
     */
    @Override
    public Result query(Integer roleId) {
        return ResultFactory.successOf(sysRolePermissionJpaRepository.findSysPermissionByRoleId(roleId));
    }


}
