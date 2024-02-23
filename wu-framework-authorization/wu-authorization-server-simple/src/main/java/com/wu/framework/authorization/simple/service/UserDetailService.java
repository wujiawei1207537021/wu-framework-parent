package com.wu.framework.authorization.simple.service;

import com.wu.framework.authorization.domain.LoginUserBO;
import com.wu.framework.authorization.login.UserDetailsService;
import com.wu.framework.authorization.model.AuthorizationUser;
import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import org.springframework.stereotype.Service;


@Service
public class UserDetailService implements UserDetailsService {
    private final LazyLambdaStream lazyLambdaStream;

    public UserDetailService(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        if (userName.contains("@")) {
            String[] str = userName.split("@");
            return lazyLambdaStream.executeSQLForBean(String.format("select * from tenant_user  where login_name='%s' and um_domain_name='%s'  ", str[0], str[1]), AuthorizationUser.class);
        } else {
            throw new RuntimeException("账号错误");
        }
    }

    @Override
    public void createUser(LoginUserBO loginUserBO) {


    }

    @Override
    public UserDetails loadUserById(String userId) {
        return null;
    }
}
