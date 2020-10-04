package com.wu.framework.shiro.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ Description : 默认access_token返回值 @ Author : wujiawei @ CreateDate : 2019/12/9 0009 17:58 @
 * UpdateUser : wujiawei @ UpdateDate : 2019/12/9 0009 17:58 @ UpdateRemark : 修改内容 @ Version : 1.0
 */
@Data
public class DefaultAccessToken implements AccessToken, Serializable {

    /**
     * 令牌
     */
    private String accessToken;

    /**
     * 令牌类型
     */
    private String scope = "web";

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 令牌有效时长
     */
    private Long expiresIn;

    /**
     * 过期时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date expiresDate;

    //  @Override
//  public Long getExpiresIn() {
//    ShiroProperties shiroProperties= (ShiroProperties) ShiroContextUtil.getBean("shiroProperties");
//    if(ObjectUtils.isEmpty(shiroProperties))
//    {
//      shiroProperties=new ShiroProperties();
//    }
////    return shiroProperties.getExpireTime();
//    return this.expiresDate.getTime()-System.currentTimeMillis();
//  }

//  public Date initExpiresDate() {
//    ShiroProperties shiroProperties= (ShiroProperties) ShiroContextUtil.getBean("shiroProperties");
//    if(ObjectUtils.isEmpty(shiroProperties))
//    {
//      shiroProperties=new ShiroProperties();
//    }
//    this.expiresDate= new Date(System.currentTimeMillis() + shiroProperties.getExpireTime());
//    return expiresDate;
//  }


}
