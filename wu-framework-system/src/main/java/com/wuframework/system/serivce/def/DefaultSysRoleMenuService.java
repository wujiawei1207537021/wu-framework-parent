package com.wuframework.system.serivce.def;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.system.common.entity.SysRoleMenu;
import com.wuframework.system.persistence.mapper.SysRoleMenuMapper;
import com.wuframework.system.serivce.SysRoleMenuService;
import com.wuframework.system.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色与菜单对应关系 服务实现类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Service
public class DefaultSysRoleMenuService extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 添加或修改角色的菜单权限
     *
     * @param roleId 角色ID
     * @param menuId 菜单列表
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer saveRoleMenu(Long roleId, List<String> menuId) {
        List<SysRoleMenu> roleMenuList = this.listByRoleId(roleId);
        List<String> oldMenuId = roleMenuList.stream().map(r -> String.valueOf(r.getMenuId())).collect(Collectors.toList());
        Map<String, List<String>> map = CommonUtils.different(oldMenuId, menuId);
        int row = 0;
        List<String> addList = map.get("add");
        List<String> commonList = map.get("common");
        SysRoleMenu roleMenuAdd = new SysRoleMenu();
        for (String menuIds : addList) {
            roleMenuAdd.setRoleId(roleId);
            roleMenuAdd.setMenuId(Long.valueOf(menuIds));
            row += this.sysRoleMenuMapper.insert(roleMenuAdd);
        }

        List<String> delList = map.get("del");
        SysRoleMenu roleMenuDel = new SysRoleMenu();
        EntityWrapper<SysRoleMenu> entityWrapperDel = new EntityWrapper<>();
        for (String menuIds : delList) {
            roleMenuDel.setRoleId(roleId);
            roleMenuDel.setMenuId(Long.valueOf(menuIds));
            entityWrapperDel.setEntity(roleMenuDel);
            this.sysRoleMenuMapper.delete(entityWrapperDel);
        }
        return row + commonList.size();
    }

    /**
     * 根据角色ID和用户ID绑定默认菜单ID
     *
     * @param roleId
     * @param menuId
     */
    @Override
    public Result saveRoleDefaultPage(Long roleId, Long menuId) {
        if (Objects.isNull(roleId) || Objects.isNull(menuId)) {
            return ResultFactory.of(DefaultResultCode.PARAMETER_ERROR, "所有参数不能为空");
        }
        sysRoleMenuMapper.saveRoleDefaultPage(roleId, menuId);
        return ResultFactory.successOf();
    }

    /**
     * 获取指定角色下的菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<SysRoleMenu> listByRoleId(Long roleId) {
        EntityWrapper<SysRoleMenu> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("role_id", roleId);
        return this.sysRoleMenuMapper.selectList(entityWrapper);
    }
}
