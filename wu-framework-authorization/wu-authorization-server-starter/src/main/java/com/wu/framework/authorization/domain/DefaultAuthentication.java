package com.wu.framework.authorization.domain;

import com.wu.framework.authorization.model.UserDetails;
import lombok.Data;

@Data
public class DefaultAuthentication implements Authentication {
    private UserDetails userDetails;

    private String scope;


}
