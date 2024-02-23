package com.wuframework.system.serivce;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wuframework.system.common.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 根据构造器分页查询数据
     *
     * @param role
     * @param page
     * @return
     */
    Page getRoleListPage(SysRole role, Page page);

    /**
     * 根据用户ID获取相关角色
     *
     * @param userId 用户ID
     * @return
     */
    List<SysRole> selectRoleByUserId(Long userId);

    /**
     * 根据角色名称查询角色
     *
     * @param roleName 角色名称
     * @return
     */
    SysRole selectRoleByRoleName(String roleName);

    /**
     * 根据ID物理删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    Integer deleteRoleByRoleId(Long roleId);

    /**
     * 根据角色ID逻辑删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    Integer batchDeleteRoleByRoleId(Integer[] roleId);

    /**
     * 根据角色标识获取对应角色
     *
     * @param roleSign 角色标识
     * @return
     */
    SysRole selectRoleByRoleSign(String roleSign);
}
