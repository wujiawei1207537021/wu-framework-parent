package com.wuframework.system.persistence.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wuframework.system.common.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据用户ID和角色ID绑定默认菜单ID
     *
     * @param roleId
     * @param menuId
     */
    void saveRoleDefaultPage(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 登录时根据用户ID和角色ID获取默认菜单页面ID
     *
     * @param userId
     * @return
     */
    List<SysRoleMenu> getRoleDefaultPage(Long userId);

}
