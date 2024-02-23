package com.wu.framework.authorization.platform.model.role.menu;

import com.wu.framework.authorization.platform.infrastructure.entity.RoleMenuDO;
import com.wu.framework.authorization.platform.model.menu.Menu;
import com.wu.framework.response.Result;
import com.wu.framework.response.repository.LazyCrudRepository;

import java.util.List;

/**
 * describe 角色菜单
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository
 **/

public interface RoleMenuRepository extends LazyCrudRepository<RoleMenuDO,RoleMenu, Integer> {


    /**
     * describe 新增角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<RoleMenu> story(RoleMenu roleMenu);

    /**
     * describe 新增角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result batchStory(List<RoleMenu> roleMenuList);

    /**
     * describe 查询单个角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<RoleMenu> findOne(RoleMenu roleMenu);

    /**
     * describe 查询多个角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<RoleMenu>> findList(RoleMenu roleMenu);


    /**
     * 获取菜单数据
     * @param roleIds
     * @return
     */
    Result<List<Menu>> findRoleMenuList(List<Long> roleIds);
    /**
     * describe 删除角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<RoleMenu> remove(RoleMenu roleMenu);


    /**
     * describe 删除角色菜单
     *
     * @param
     * @param roleId
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    void removeRoleMenus(Long roleId);
}