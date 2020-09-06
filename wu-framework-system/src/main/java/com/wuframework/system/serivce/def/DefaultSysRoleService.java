package com.wuframework.system.serivce.def;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.wuframework.system.common.entity.SysMenu;
import com.wuframework.system.common.entity.SysRole;
import com.wuframework.system.persistence.mapper.SysMenuMapper;
import com.wuframework.system.persistence.mapper.SysRoleMapper;
import com.wuframework.system.serivce.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Service
public class DefaultSysRoleService extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 根据构造器分页查询数据
     *
     * @param role
     * @param page
     * @return
     */
    @Override
    public Page getRoleListPage(SysRole role, Page page) {
        List<SysRole> roleList = sysRoleMapper.listRoleUnderRole(role, page);
        for (SysRole sysRole : roleList) {
            List<SysMenu> menuList = sysMenuMapper.selectSecondaryMenuByRoleId(sysRole.getRoleId());
            sysRole.setMenuList(menuList);
        }
        return page.setRecords(roleList);
    }

    /**
     * 根据用户ID获取相关角色
     *
     * @param userId 用户ID
     */
    @Override
    public List<SysRole> selectRoleByUserId(Long userId) {
        return this.sysRoleMapper.selectRoleByUserId(userId);
    }

    /**
     * 根据角色名称查询角色
     *
     * @param roleName 角色名称
     * @return
     */
    @Override
    public SysRole selectRoleByRoleName(String roleName) {
        SysRole role = new SysRole();
        role.setRoleName(roleName);
        return this.sysRoleMapper.selectOne(role);
    }

    /**
     * 根据ID删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer deleteRoleByRoleId(Long roleId) {
        List<SysRole> updateList = Lists.newArrayList();
        List<SysRole> sysRoleList = sysRoleMapper.selectList(null);
        //更新有该角色权限的角色
        for (SysRole sysRole : sysRoleList) {
            if (StringUtils.isNotBlank(sysRole.getManageableRole()) && sysRole.getManageableRole().contains(roleId.toString())) {
                String manageableRole = Arrays.stream(StringUtils.split(sysRole.getManageableRole(), ","))
                        .filter(id -> !id.equals(roleId.toString())).collect(Collectors.joining(","));
                sysRole.setManageableRole(manageableRole);
                updateList.add(sysRole);
            }
        }
        if (!ObjectUtils.isEmpty(updateList)) {
            this.updateBatchById(updateList);
        }
        return this.sysRoleMapper.deleteById(roleId);
    }

    /**
     * 根据角色ID逻辑删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    @Override
    public Integer batchDeleteRoleByRoleId(Integer[] roleId) {
        SysRole role = new SysRole();
        role.setStatus(0);
        int row = 0;
        for (Integer id : roleId) {
            role.setRoleId(id);
            row += this.sysRoleMapper.updateById(role);
        }
        return row;
    }

    @Override
    public SysRole selectRoleByRoleSign(String roleSign) {
        EntityWrapper<SysRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("role_sign", roleSign);
        return this.selectOne(entityWrapper);
    }
}
