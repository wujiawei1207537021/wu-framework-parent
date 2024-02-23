package com.wu.framework.authorization.platform.infrastructure.persistence;


import com.wu.framework.authorization.platform.infrastructure.converter.RoleMenuConverter;
import com.wu.framework.authorization.platform.infrastructure.entity.MenuDO;
import com.wu.framework.authorization.platform.infrastructure.entity.RoleMenuDO;
import com.wu.framework.authorization.platform.model.menu.Menu;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenu;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenuRepository;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 角色菜单
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class RoleMenuRepositoryImpl extends AbstractLazyCrudRepository<RoleMenuDO,RoleMenu, Integer> implements RoleMenuRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;


    /**
     * describe 新增角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<RoleMenu> story(RoleMenu roleMenu) {
        RoleMenuDO roleMenuDO = RoleMenuConverter.fromRoleMenu(roleMenu);
        lazyLambdaStream.upsert(roleMenuDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 新增角色菜单
     *
     * @param roleMenuList@return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    @Override
    public Result batchStory(List<RoleMenu> roleMenuList) {
        List<RoleMenuDO> roleMenuDOList = roleMenuList.stream().map(RoleMenuConverter::fromRoleMenu).collect(Collectors.toList());
        lazyLambdaStream.upsert(roleMenuDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<RoleMenu> findOne(RoleMenu roleMenu) {
        RoleMenuDO roleMenuDO = RoleMenuConverter.fromRoleMenu(roleMenu);
        RoleMenu roleMenuOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(roleMenuDO), RoleMenu.class);
        return ResultFactory.successOf(roleMenuOne);
    }

    /**
     * describe 查询多个角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<List<RoleMenu>> findList(RoleMenu roleMenu) {
        RoleMenuDO roleMenuDO = RoleMenuConverter.fromRoleMenu(roleMenu);
        List<RoleMenu> roleMenuList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(roleMenuDO), RoleMenu.class);
        return ResultFactory.successOf(roleMenuList);
    }

    /**
     * 获取菜单数据
     *
     * @param roleIds
     * @return
     */
    @Override
    public Result<List<Menu>> findRoleMenuList(List<Long> roleIds) {
        List<Menu> menuList = lazyLambdaStream.selectList(LazyWrappers.<RoleMenuDO>lambdaWrapper()
                .in(RoleMenuDO::getRoleId, roleIds)
//                        .distinct(RoleMenuDO::getMenuId)
                .internalJoin(LazyWrappers.<RoleMenuDO, MenuDO>lambdaWrapperJoin()
                        .eq(RoleMenuDO::getMenuId,MenuDO::getId)
                        .eqo(RoleMenuDO::getIsDeleted,false)
                        .eqRighto(MenuDO::getIsDeleted,false)
                )

                ,Menu.class
        );
        return ResultFactory.successOf(menuList);
    }

    /**
     * describe 删除角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<RoleMenu> remove(RoleMenu roleMenu) {
        RoleMenuDO roleMenuDO = RoleMenuConverter.fromRoleMenu(roleMenu);
        roleMenuDO.setIsDeleted(true);
        lazyLambdaStream.upsert(roleMenuDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 删除角色菜单
     *
     * @param roleId
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    @Override
    public void removeRoleMenus(Long roleId) {
        RoleMenuDO roleMenuDO = new RoleMenuDO();
        roleMenuDO.setIsDeleted(true);
        lazyLambdaStream.update(roleMenuDO, LazyWrappers.<RoleMenuDO>lambdaWrapper()
                .eq(RoleMenuDO::getRoleId, roleId)
        );
    }
}