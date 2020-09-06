//package com.wuframework.shiro;
//
//import com.wuframework.shiro.model.User;
//import com.wuframework.shiro.model.UserDetails;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.annotation.Resource;
//
///**
// * @ Description : 当前框架默认实现方法 @ Author : wujiawei @ CreateDate : 2019/12/17 0017 11:46 @ UpdateUser
// * : wujiawei @ UpdateDate : 2019/12/17 0017 11:46 @ UpdateRemark : 修改内容 @ Version : 1.0
// */
//
////@Service("defaultUserDetailsService")
//public class DefaultUserDetailsService implements UserDetailsService {
//
//    private static final BeanPropertyRowMapper<User> USER_ROWMAPPDER = BeanPropertyRowMapper.newInstance(User.class);
//
//    @Resource
//    JdbcTemplate jdbcTemplate;
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) {
//        User user = jdbcTemplate.queryForObject("select * from sys_user su where su.username=?",USER_ROWMAPPDER,userName );
//        return user;
//    }
//}
