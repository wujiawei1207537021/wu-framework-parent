package com.wu.framework.shiro.login;

import com.wu.framework.shiro.model.User;
import com.wu.framework.shiro.model.UserDetails;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ Description : 当前框架默认实现方法 @ Author : wujiawei @ CreateDate : 2019/12/17 0017 11:46 @ UpdateUser
 * : wujiawei @ UpdateDate : 2019/12/17 0017 11:46 @ UpdateRemark : 修改内容 @ Version : 1.0
 */
@ConditionalOnMissingBean(UserDetailsService.class)
public class DefaultUserDetailsService implements UserDetailsService {

    private static final BeanPropertyRowMapper<User> USER_ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);


    private final JdbcTemplate jdbcTemplate;

    public DefaultUserDetailsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = jdbcTemplate.queryForObject("select * from sys_user su where su.username=?", USER_ROW_MAPPER, userName);
        return user;
    }
}
