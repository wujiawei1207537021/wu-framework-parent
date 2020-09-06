package com.wuframework.shiro.domain;

import com.wuframework.shiro.model.UserDetails;
import lombok.Data;

@Data
public class DefaultAuthentication implements Authentication {
    private UserDetails userDetails;

    private String scope;



}
