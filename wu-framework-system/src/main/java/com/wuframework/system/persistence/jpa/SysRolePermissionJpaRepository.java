package com.wuframework.system.persistence.jpa;

import com.wuframework.system.common.entity.SysPermission;
import com.wuframework.system.common.entity.SysRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRolePermissionJpaRepository extends JpaRepository<SysRolePermission,Integer> {
    /**
     * 删除角色所有权限
     * @param roleId
     */
    @Modifying
    void deleteByRoleId(Integer roleId);

    @Query("SELECT sp FROM SysPermission AS sp LEFT JOIN SysRolePermission AS srp ON sp.permissionId = srp.permissionId where srp.roleId=?1")
    List<SysPermission> findSysPermissionByRoleId(Integer roleId);
}
