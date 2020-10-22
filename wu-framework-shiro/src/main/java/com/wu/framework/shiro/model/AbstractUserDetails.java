package com.wu.framework.shiro.model;

import lombok.Data;

import java.util.List;

/**
 * 实现用户实体抽象类
 */
@Data
public abstract class AbstractUserDetails implements UserDetails {

    /**
     * 用户名
     */
    protected String username;

    /**
     * 用户密码
     */
    protected String password;

    /**
     * 用户权限
     */
    protected List permissionList;

    /**
     * 用户角色标示
     */
    protected List<String> roleSignList;

}
