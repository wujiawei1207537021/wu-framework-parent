package com.wu.framework.authorization.platform.infrastructure.persistence;


import com.wu.framework.authorization.platform.infrastructure.converter.UserRoleConverter;
import com.wu.framework.authorization.platform.infrastructure.entity.UserRoleDO;
import com.wu.framework.authorization.platform.model.sys.user.SysUser;
import com.wu.framework.authorization.platform.model.user.role.UserRole;
import com.wu.framework.authorization.platform.model.user.role.UserRoleRepository;
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
 * describe 用户角色关联关系
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class UserRoleRepositoryImpl extends AbstractLazyCrudRepository<UserRoleDO, UserRole, Integer> implements UserRoleRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;


    /**
     * describe 新增用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<UserRole> story(UserRole userRole) {
        UserRoleDO userRoleDO = UserRoleConverter.fromUserRole(userRole);
        lazyLambdaStream.upsert(userRoleDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<UserRole> findOne(UserRole userRole) {
        UserRoleDO userRoleDO = UserRoleConverter.fromUserRole(userRole);
        UserRole userRoleOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(userRoleDO), UserRole.class);
        return ResultFactory.successOf(userRoleOne);
    }

    /**
     * describe 查询多个用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<List<UserRole>> findList(UserRole userRole) {
        UserRoleDO userRoleDO = UserRoleConverter.fromUserRole(userRole);
        List<UserRole> userRoleList = lazyLambdaStream.selectList(
                LazyWrappers.lambdaWrapperBean(userRoleDO)
                .eq(UserRoleDO::getIsDeleted,false), UserRole.class);
        return ResultFactory.successOf(userRoleList);
    }

    /**
     * describe 删除用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<UserRole> delete(UserRole userRole) {
        UserRoleDO userRoleDO = UserRoleConverter.fromUserRole(userRole);
        //  lazyLambdaStream.remove(userRoleDO);
        return ResultFactory.successOf();
    }

    /**
     * description: 批量存储用户角色
     *
     * @param userId       用户ID
     * @param userRoleList 用户角色数据
     * @return
     * @author 吴佳伟
     * @date: 21.5.23 22:42
     */
    @Override
    public void batchStoryUserRole(Long userId, List<UserRole> userRoleList) {
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserId(userId);
        userRoleDO.setIsDeleted(true);
        lazyLambdaStream.update(userRoleDO, LazyWrappers.<UserRoleDO>lambdaWrapper()
                .eq(UserRoleDO::getUserId, userId)
        );
        List<UserRoleDO> userRoleDOList = userRoleList.stream().map(UserRoleConverter::fromUserRole).collect(Collectors.toList());
        lazyLambdaStream.upsert(userRoleDOList);
    }
}