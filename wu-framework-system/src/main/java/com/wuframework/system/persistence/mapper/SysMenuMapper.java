package com.wuframework.system.persistence.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wuframework.system.common.entity.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据用户查询相关菜单
     *
     * @param userId 用户编号
     * @return
     */
    List<SysMenu> selectMenuListByUserId(Integer userId);

    /**
     * 根据角色编号查询相关菜单
     *
     * @param roleId 角色编号
     * @return
     */
    List<SysMenu> selectMenuListByRoleId(Long roleId);

    /**
     * 根据用户ID查询相关菜单
     *
     * @param userId 用户编号
     * @return
     */
    List<SysMenu> selectMenuListByUser(Integer userId);

    /**
     * 根据用户ID查询相关二级菜单
     *
     * @param userId
     * @return
     */
    List<SysMenu> selectSecondaryMenuListByUserId(Integer userId);

    /**
     * 根据角色菜单查询所有的二级菜单
     *
     * @param roleId
     * @return
     */
    List<SysMenu> selectSecondaryMenuByRoleId(Integer roleId);
}
