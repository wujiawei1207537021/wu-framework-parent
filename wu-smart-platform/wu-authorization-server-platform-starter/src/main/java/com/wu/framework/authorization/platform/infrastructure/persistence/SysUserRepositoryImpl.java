package com.wu.framework.authorization.platform.infrastructure.persistence;


import com.wu.framework.authorization.platform.infrastructure.converter.SysUserConverter;
import com.wu.framework.authorization.platform.infrastructure.entity.MenuDO;
import com.wu.framework.authorization.platform.infrastructure.entity.RoleDO;
import com.wu.framework.authorization.platform.infrastructure.entity.RoleMenuDO;
import com.wu.framework.authorization.platform.infrastructure.entity.SysUserDO;
import com.wu.framework.authorization.platform.model.menu.Menu;
import com.wu.framework.authorization.platform.model.role.Role;
import com.wu.framework.authorization.platform.model.sys.user.SysUser;
import com.wu.framework.authorization.platform.model.sys.user.SysUserRepository;
import com.wu.framework.authorization.platform.model.user.role.UserRole;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class SysUserRepositoryImpl extends AbstractLazyCrudRepository<SysUserDO,SysUser, Integer> implements SysUserRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;


    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<SysUser> story(SysUser sysUser) {
        SysUserDO sysUserDO = SysUserConverter.fromSysUser(sysUser);
        lazyLambdaStream.upsert(sysUserDO);
        return ResultFactory.successOf(SysUserConverter.toSysUser(sysUserDO));
    }

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<SysUser> findOne(SysUser sysUser) {
        SysUserDO sysUserDO = SysUserConverter.fromSysUser(sysUser);
        SysUser sysUserOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(sysUserDO), SysUser.class);
        return ResultFactory.successOf(sysUserOne);
    }

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<List<SysUser>> findList(SysUser sysUser) {
        SysUserDO sysUserDO = SysUserConverter.fromSysUser(sysUser);
        List<SysUser> sysUserList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(sysUserDO), SysUser.class);
        sysUserList.stream().forEach(one->{
            // 获取用户角色
            List<Role> roleList = lazyLambdaStream.selectList(LazyWrappers.<UserRole>lambdaWrapper()
                    .eq(UserRole::getIsDeleted, false)
                    .eq(UserRole::getUserId, one.getId())
                    .internalJoin(LazyWrappers.<UserRole, RoleDO>lambdaWrapperJoin()
                            .eqo(UserRole::getUserId, one.getId())
                            .eqo(UserRole::getIsDeleted, false)
                            .eq(UserRole::getRoleId, RoleDO::getId)
                            .eqRighto(RoleDO::getIsDeleted,false)
                    )
                    .onlyUseAs()
                    .as(RoleDO.class), Role.class
            );

            // 获取角色菜单
            roleList.stream().forEach(role -> {
                List<Menu> menuList = lazyLambdaStream.selectList(LazyWrappers.<RoleMenuDO>lambdaWrapper()
                        .eq(RoleMenuDO::getIsDeleted, false)
                        .eq(RoleMenuDO::getRoleId, role.getId())
                        .internalJoin(LazyWrappers.<RoleMenuDO, MenuDO>lambdaWrapperJoin()
                                .eqo(RoleMenuDO::getIsDeleted, false)
                                .eqo(RoleMenuDO::getRoleId, role.getId())
                                .eq(RoleMenuDO::getMenuId, MenuDO::getId)
                                .eqRighto(MenuDO::getIsDeleted, false)
                        )
                        .onlyUseAs()
                        .as(Menu.class),
                        Menu.class
                );
                role.setMenuList(menuList);
            });
            one.setRoleList(roleList);
        });
        return ResultFactory.successOf(sysUserList);
    }
    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/21 11:16 晚上
     **/

    @Override
    public Result<LazyPage<SysUser>> findPage(int size, int current, SysUser sysUser) {
        SysUserDO sysUserDO = SysUserConverter.fromSysUser(sysUser);
        LazyPage lazyPage = new LazyPage(current,size);
        LazyPage<SysUser> sysUserLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(sysUserDO),lazyPage, SysUser.class);
        sysUserLazyPage.getRecord().stream().forEach(one -> {
            List<Role> roleList = lazyLambdaStream.selectList(LazyWrappers.<UserRole>lambdaWrapper()
                    .eq(UserRole::getIsDeleted, false)
                    .eq(UserRole::getUserId, one.getId())
                    .internalJoin(LazyWrappers.<UserRole, RoleDO>lambdaWrapperJoin()
                            .eqo(UserRole::getUserId, one.getId())
                            .eqo(UserRole::getIsDeleted, false)
                            .eq(UserRole::getRoleId, RoleDO::getId)
                            .eqRighto(RoleDO::getIsDeleted,false)
                    )
                    .onlyUseAs()
                    .as(RoleDO.class), Role.class
            );
            one.setRoleList(roleList);
        });
        return ResultFactory.successOf(sysUserLazyPage);
    }
    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<SysUser> delete(SysUser sysUser) {
        SysUserDO sysUserDO = SysUserConverter.fromSysUser(sysUser);
        //  lazyLambdaStream.remove(sysUserDO);
        return ResultFactory.successOf();
    }

}