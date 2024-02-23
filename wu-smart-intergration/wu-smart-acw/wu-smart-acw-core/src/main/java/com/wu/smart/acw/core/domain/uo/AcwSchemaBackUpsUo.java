package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * description 数据库备份信息
 *
 * @author Jia wei Wu
 * @date 2021/12/27$ 4:46 下午$
 */
@Accessors(chain = true)
@Data
@LazyTable(comment = "数据库备份信息")
public class AcwSchemaBackUpsUo {

    /**
     * 主键
     */
    @Schema(name = "id")
    private String id;
    /**
     * Name of the datasource. Default to "testdb" when using an embedded database.
     */
    @LazyTableField(comment = "实例ID")
    @Schema(name = "instanceId", example = "normal")
    private String instanceId;

    /**
     * 数据库名称
     */
    @LazyTableField(comment = "数据库名称")
    @Schema(name = "schemaName", example = "1")
    private String schemaName;

    /**
     * 数据库实例中对应的表数量
     */
    @LazyTableField(comment = "数据库实例中对应的表数量")
    @Schema(name = "tableNum", example = "1")
    private int tableNum;

    /**
     * 状态：进行中、完成、失败
     */
    @LazyTableField(comment = "状态：进行中、完成、失败")
    @Schema(name = "status", example = "1")
    private int status;


    /**
     * 备份文件地址
     */
    @LazyTableField(comment = "备份文件地址")
    @Schema(name = "path", example = "/var/local/backups.sql")
    private String path;

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

}
