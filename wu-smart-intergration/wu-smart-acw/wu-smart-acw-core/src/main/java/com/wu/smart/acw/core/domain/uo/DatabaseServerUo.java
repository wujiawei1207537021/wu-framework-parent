package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * description 数据库服务器
 *
 * @author 吴佳伟
 * @date 2021/12/27$ 4:46 下午$
 */
@Accessors(chain = true)
@Data
public class DatabaseServerUo {

    /**
     * 主键
     */
    @LazyTableFieldId
    @ApiModelProperty(name = "id")
    private Long id;
    /**
     * Name of the datasource. Default to "testdb" when using an embedded database.
     */
    @ApiModelProperty(name = "name", example = "normal")
    private String name;

    /**
     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
     */
    @ApiModelProperty(value = "driverClassName", example = "com.mysql.cj.jdbc.Driver")
    private String driverClassName;

    /**
     * JDBC URL of the database.
     */
    @ApiModelProperty(value = "url", example = "jdbc:mysql://127.0.0.1:3306/upsert?useUnicode=true&characterEncoding=utf-8&useSSL=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai")
    private String url;

    /**
     * Login username of the database.
     */
    @ApiModelProperty(value = "username", example = "root")
    private String username;

    /**
     * Login password of the database.
     */
    @ApiModelProperty(value = "password", example = "wujiawei")
    private String password;


}
