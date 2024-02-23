package com.wu.framework.shiro.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @ Description   :  返回页面封装的令牌
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/1/22 0022 9:32
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/1/22 0022 9:32
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */


public interface AccessTokenRO extends Serializable {
    String getAccessToken();

    void setAccessToken(String accessToken);

    String getScope();

    void setScope(String scope);

    String getRefreshToken();

    void setRefreshToken(String refreshToken);

    Long getExpiresIn();

    void setExpiresIn(Long expiresIn);

    Date getExpiresDate();

    void setExpiresDate(Date expiresDate);
}
