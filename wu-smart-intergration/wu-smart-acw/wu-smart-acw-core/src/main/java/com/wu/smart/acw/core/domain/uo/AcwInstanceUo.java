package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.layer.data.encryption.EncryptionField;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * description 数据库服务器
 *
 * @author Jia wei Wu
 * @date 2021/12/27$ 4:46 下午$
 */
@Accessors(chain = true)
@Data
@LazyTable(comment = "数据库服务器")
public class AcwInstanceUo {

    /**
     * 主键
     */
    @Schema(name = "id")
    @LazyTableField(lazyTableIndexs = @LazyTableIndex(indexType = LayerField.LayerFieldType.UNIQUE))
    private String id;
    /**
     * Name of the datasource. Default to "testdb" when using an embedded database.
     */
    @LazyTableField()
    @Schema(name = "instanceName", example = "normal")
    private String instanceName;

    /**
     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
     */
    @Schema(description = "driverClassName", example = "com.mysql.cj.jdbc.Driver")
    @LazyTableField(comment = "链接驱动")
    private String driverClassName;

    /**
     * JDBC URL of the database.
     */
    @Schema(description = "url", example = "jdbc:mysql://127.0.0.1:3306/upsert?useUnicode=true&characterEncoding=utf-8&useSSL=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai")
    @LazyTableField(comment = "链接地址",dataType = "varchar(500)")
    private String url;

    /**
     * Login username of the database.
     */
    @Schema(description = "username", example = "root")
    @LazyTableField(comment = "用户名")
    private String username;

    /**
     * Login password of the database.
     */
    @EncryptionField
    @Schema(description = "password", example = "wujiawei")
    @LazyTableField(comment = "密码")
    private String password;
    /**
     * 状态
     */
    @Schema(description = "status", example = "1")
    @LazyTableField(comment = "状态")
    private Integer status;
    /**
     * 状态
     */
    @Schema(description = "sort", example = "1")
    @LazyTableField(comment = "排序",defaultValue = "0")
    private Integer sort;
    /**
     * getHost
     */
    @LazyTableField(comment = "getHost")
    @Schema(description = "getHost", example = "1")
    private String host;
    /**
     * 端口
     */
    @LazyTableField(comment = "端口")
    @Schema(description = "getPort", example = "1")
    private Integer port;
    /**
     * 初始化数据库到本地
     */
    @LazyTableField(comment = "初始化数据库到本地")
    @Schema(description = "initializeToLocal", example = "1")
    private Boolean initializeToLocal;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
    private Boolean isDeleted = false;

    /**
     * 数据库类型
     */
    private LazyDataSourceType lazyDataSourceType;

}
