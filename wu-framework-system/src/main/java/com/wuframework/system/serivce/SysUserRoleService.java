package com.wuframework.system.serivce;


import com.baomidou.mybatisplus.service.IService;
import com.wuframework.system.common.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    /**
     * 根据用户存储其拥有的角色
     *
     * @param userId 用户编号
     * @param roleId 角色列表
     * @return
     */
    Integer saveByUserId(Integer userId, List<String> roleId);

    /**
     * 根据角色存储角色所有用的用户
     *
     * @param roleId 角色编号
     * @param userId 用户列表
     * @return
     */
    Integer saveByRoleId(Integer roleId, List<String> userId);

    /**
     * 根据用户ID获取对应的角色列表
     *
     * @param userId
     * @return
     */
    List<SysUserRole> listByUserId(Integer userId);

    /**
     * 获取拥有指定角色的所有用户列表
     *
     * @param roleId
     * @return
     */
    List<SysUserRole> listByRoleId(Integer roleId);

    /**
     * 根据用户ID 查询用户角色标示
     * @param userId
     * @return
     */
     List<String> listRoleSign(Integer userId);
    /**
     * 统计该角色关联用户数
     *
     * @param roleId
     * @return
     */
    Integer countByRoleId(Long roleId);

    /**
     * 分配指定角色给指定用户
     *
     * @param userId
     * @param roleId
     * @return
     */
    Integer assignRoleToUser(Integer userId, Integer roleId);

    /**
     * 分配指定角色给指定用户
     *
     * @param userId
     * @param roleSigns
     * @return
     */
    Integer assignRoleToUser(Integer userId, List<String> roleSigns);


    /**
     * 删除指定用户下的指定角色
     *
     * @param userId
     * @param roleId
     */
    void deleteRoleFromUser(Integer userId, Integer roleId);

}
