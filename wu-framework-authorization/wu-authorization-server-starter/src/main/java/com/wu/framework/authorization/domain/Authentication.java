package com.wu.framework.authorization.domain;

import com.wu.framework.authorization.model.UserDetails;

import java.io.Serializable;

public interface Authentication extends Serializable {
    UserDetails getUserDetails();

    void setUserDetails(UserDetails userDetails);

    String getScope();

    void setScope(String scope);

    /**
     * 签名string
     *
     * @return
     */
    default String tosin() {
        return "Authentication(UserDetails=" + this.getUserDetails().tosin() + ",getScope=" + this.getScope() + ")";
    }


}
