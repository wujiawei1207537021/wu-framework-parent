package com.wu.framework.shiro.model;

import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
@Entity
@Table(appliesTo = "access_token", comment = "令牌")
public class AccessToken {
    @Id
    @Column(name = "token_id", nullable = false, length = 256)
    private String tokenId;

    @Basic
    @Column(name = "token", nullable = true, columnDefinition = "blob  COMMENT '令牌'")
    private byte[] token;
    @Basic
    @Column(name = "authentication_id", nullable = true, length = 128)
    private String authenticationId;
    @Basic
    @Column(name = "user_name", nullable = true, length = 256)
    private String userName;
    @Basic
    @Column(name = "client_id", nullable = true, length = 256)
    private String clientId;

    @Basic
    @Column(name = "authentication", nullable = true, columnDefinition = "blob  COMMENT '授权'")
    private byte[] authentication;
    @Basic
    @Column(name = "refresh_token", nullable = true, length = 256)
    private String refreshToken;

}
