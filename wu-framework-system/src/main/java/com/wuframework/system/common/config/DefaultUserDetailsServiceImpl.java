package com.wuframework.system.common.config;


import com.wuframework.shiro.UserDetailsService;
import com.wuframework.shiro.model.UserDetails;
import com.wuframework.system.persistence.mapper.DefaultSysUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 框架中用户登录逻辑实现(默认实现)
 */

@Service("defaultUserDetailsService")
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private DefaultSysUserMapper defaultSysUserMapper;

    /**
     * 通过用户账号获取用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String userName) {
        return defaultSysUserMapper.getUserByUsername(userName);
    }
}
