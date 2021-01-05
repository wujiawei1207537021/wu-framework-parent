package com.wu.framework.shiro.model;

import lombok.Data;


/**
 * @ Description   :  数据库令牌参数
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/1/22 0022 9:32
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/1/22 0022 9:32
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */


@Data
public class AccessToken {

    private String tokenId;


    private byte[] token;

    private String authenticationId;

    private String userName;

    private String clientId;

    private byte[] authentication;

    private String refreshToken;

}
