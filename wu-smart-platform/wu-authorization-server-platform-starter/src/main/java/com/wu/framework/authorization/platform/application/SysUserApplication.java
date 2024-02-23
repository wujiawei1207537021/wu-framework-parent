package com.wu.framework.authorization.platform.application;

import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.authorization.platform.application.command.SysUserCommand;
import com.wu.framework.authorization.platform.model.menu.Menu;
import com.wu.framework.authorization.platform.model.sys.user.SysUser;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface SysUserApplication {


    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<SysUser> save(SysUserCommand sysUserCommand);

    /**
     * describe 更新
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<SysUser> update(SysUserCommand sysUserCommand);

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<SysUser> findOne(SysUserCommand sysUserCommand);

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<SysUser>> findList(SysUserCommand sysUserCommand);

    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/21 11:16 晚上
     **/

    Result<LazyPage<SysUser>> findPage(int size, int current, SysUserCommand sysUserCommand);

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<SysUser> delete(SysUserCommand sysUserCommand);

    /**
     * 查询用户菜单
     *
     * @param userDetails
     * @return
     */
    Result<List<Menu>> findUserMenuList(UserDetails userDetails);
}