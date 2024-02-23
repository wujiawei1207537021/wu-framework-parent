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
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户权限
     */
    private List permissionList;

    /**
     * 用户角色标示
     */
    private List<String> roleSignList;

}
