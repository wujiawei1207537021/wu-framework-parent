package com.wu.framework.authorization.platform.infrastructure.persistence;


import com.wu.framework.authorization.platform.infrastructure.converter.MenuConverter;
import com.wu.framework.authorization.platform.infrastructure.converter.RoleConverter;
import com.wu.framework.authorization.platform.infrastructure.entity.MenuDO;
import com.wu.framework.authorization.platform.infrastructure.entity.RoleDO;
import com.wu.framework.authorization.platform.infrastructure.entity.RoleMenuDO;
import com.wu.framework.authorization.platform.model.role.Role;
import com.wu.framework.authorization.platform.model.role.RoleRepository;
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
 * describe 角色
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class RoleRepositoryImpl extends AbstractLazyCrudRepository<RoleDO,Role, Integer> implements RoleRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;


    /**
     * describe 新增角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Role> story(Role role) {
        RoleDO roleDO = RoleConverter.fromRole(role);
        lazyLambdaStream.upsert(roleDO);
        return ResultFactory.successOf(RoleConverter.toRole(roleDO));
    }

    /**
     * describe 查询单个角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Role> findOne(Role role) {
        RoleDO roleDO = RoleConverter.fromRole(role);
        Role roleOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(roleDO), Role.class);
        // 获取角色对应的菜单
        List<MenuDO> menuDOList = lazyLambdaStream.selectList(
                LazyWrappers.<RoleMenuDO>lambdaWrapper()
                        .eq(RoleMenuDO::getIsDeleted, false)
                        .eq(RoleMenuDO::getRoleId, roleOne.getId())
                        .leftJoin(
                                LazyWrappers.<RoleMenuDO, MenuDO>lambdaWrapperJoin()
                                        .eq(RoleMenuDO::getMenuId,MenuDO::getId)
                                        .eqRighto(MenuDO::getIsDeleted,false)
                        )
                        .onlyUseAs()
                        .as(MenuDO.class),
                MenuDO.class
        );
        roleOne.setMenuList(menuDOList.stream().map(MenuConverter::toMenu).collect(Collectors.toList()));
        return ResultFactory.successOf(roleOne);
    }

    /**
     * describe 查询多个角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<List<Role>> findList(Role role) {
        RoleDO roleDO = RoleConverter.fromRole(role);
        List<Role> roleList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(roleDO), Role.class);
        return ResultFactory.successOf(roleList);
    }

    /**
     * describe 删除角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Role> delete(Role role) {
        RoleDO roleDO = RoleConverter.fromRole(role);
        //  lazyLambdaStream.remove(roleDO);
        return ResultFactory.successOf();
    }

}