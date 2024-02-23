package com.wu.framework.data.relay.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;


import java.time.LocalDateTime;

/**
 * describe : 数据中转对象
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/13 22:39
 */
@Accessors(chain = true)
@Data
public class RelayUo {

    @LazyTableFieldUnique(comment = "数据库")
    private String schemaName;

    @LazyTableFieldUnique(comment = "表")
    private String tableName;

    @LazyTableFieldUnique(comment = "适配器")
    private String adapter;


    /**
     *
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",defaultValue="'0'",columnType="tinyint(1)")
    private Boolean isDeleted;

    /**
     *
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     *
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;
}
