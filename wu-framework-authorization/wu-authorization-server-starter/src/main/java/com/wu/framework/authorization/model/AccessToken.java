package com.wu.framework.authorization.model;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;


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
@Accessors(chain = true)
@LazyTable(tableName = "access_token", comment = "令牌信息")
@Schema(title = "access_token", description = "")
public class AccessToken {

    /**
     *
     */
    @Schema(description = "", name = "authentication")
    @LazyTableField(name = "authentication", comment = "", columnType = "blob")
    private byte[] authentication;
    /**
     *
     */
    @Schema(description = "", name = "authenticationId")
    @LazyTableField(name = "authentication_id", comment = "", columnType = "varchar(128)")
    private String authenticationId;
    /**
     *
     */
    @Schema(description = "", name = "clientId")
    @LazyTableField(name = "client_id", comment = "", columnType = "varchar(256)")
    private String clientId;
    /**
     *
     */
    @Schema(description = "", name = "refreshToken")
    @LazyTableField(name = "refresh_token", comment = "", columnType = "varchar(256)")
    private String refreshToken;
    /**
     *
     */
    @Schema(description = "", name = "token")
    @LazyTableField(name = "token", comment = "", columnType = "blob")
    private byte[] token;
    /**
     *
     */
    @Schema(description = "", name = "tokenId")
    @LazyTableFieldUnique(name = "token_id", comment = "", notNull = true, columnType = "varchar(256)")
    private String tokenId;
    /**
     *
     */
    @Schema(description = "", name = "userName")
    @LazyTableField(name = "user_name", comment = "", columnType = "varchar(256)")
    private String userName;
}
