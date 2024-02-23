package com.wu.framework.authorization.login;

import com.wu.framework.authorization.domain.LoginUserBO;
import com.wu.framework.authorization.model.User;
import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ Description : 当前框架默认实现方法 @ Author : wujiawei @ CreateDate : 2019/12/17 0017 11:46 @ UpdateUser
 * : wujiawei @ UpdateDate : 2019/12/17 0017 11:46 @ UpdateRemark : 修改内容 @ Version : 1.0
 */
@ConditionalOnMissingBean(UserDetailsService.class)
public class DefaultUserDetailsService implements UserDetailsService {


    private final LazyLambdaStream lazyLambdaStream;
    private final PasswordEncoder passwordEncoder;

    public DefaultUserDetailsService(LazyLambdaStream lazyLambdaStream, PasswordEncoder passwordEncoder) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        final User user = lazyLambdaStream.of(LoginUserBO.class).select(LazyWrappers.<LoginUserBO>lambdaWrapper().eq(LoginUserBO::getUsername, userName)).collectOne(User.class);
        return user;
    }

    /**
     * 创建用户
     *
     * @param loginUserBO@return
     * @author Jiawei Wu
     * @date 2021/1/6 8:37 下午
     **/
    @Override
    public void createUser(LoginUserBO loginUserBO) {
        String md5Password = passwordEncoder.encode(loginUserBO.getPassword());
        loginUserBO.setPassword(md5Password);
        lazyLambdaStream.smartUpsert(loginUserBO);
    }

    @Override
    public UserDetails loadUserById(String userId) {
        final User user = lazyLambdaStream.of(LoginUserBO.class).select(LazyWrappers.<LoginUserBO>lambdaWrapper().eq(LoginUserBO::getId, userId)).collectOne(User.class);
        return user;
    }
}
