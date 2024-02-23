package com.wu.smart.acw.server.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@LazyTable(comment = "客户端实例", tableName = "acw_client_instance")
@Data
public class AcwClientInstanceDO {

    @LazyTableFieldId
    private Long id;

    /**
     * 客户端IP
     */
    @LazyTableField(comment = "客户端IP")
    private String ip;

    /**
     * 客户端端口
     */
    @LazyTableField(comment = "客户端端口")
    private String port;

    /**
     * 客户端路径
     */
    @LazyTableField(comment = "客户端路径")
    private String path;
    /**
     * 客户端ID 当前客户端自己的ID如果为空默认是ip
     */
    @LazyTableFieldUnique(comment = "客户端ID 当前客户端自己的ID如果为空默认是ip")
    private String clientId;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime", example = "")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP", upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime", example = "")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP", upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime updateTime;
}
