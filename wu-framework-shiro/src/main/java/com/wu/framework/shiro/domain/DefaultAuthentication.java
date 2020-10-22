package com.wu.framework.shiro.domain;

import com.wu.framework.shiro.model.UserDetails;
import lombok.Data;

@Data
public class DefaultAuthentication implements Authentication {
    private UserDetails userDetails;

    private String scope;


}
