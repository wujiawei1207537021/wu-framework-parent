package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe : 参数参数
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/8 23:44
 */
@Accessors(chain = true)
@Data
@LazyTable(comment = "api参数")
public class AcwApplicationApiParamUo {


    /**
     * id
     */
    @LazyTableFieldId(comment = "api参数主键")
    private Long id;
    /**
     * 参数名称
     */
    @LazyTableField(comment = "参数名称")
    private String name;
    /**
     * 数据库字段ID
     */
    @LazyTableFieldUnique(comment = "数据库字段ID")
    private String columnName;

    /**
     * 条件
     */
    @LazyTableFieldUnique(comment = "条件")
    private String term;

    /**
     * apiId
     */
    @LazyTableFieldUnique(comment = "apiId")
    private Long apiId;

    /**
     * 类型 路径参数、请求参数、请求体参数
     */
    @LazyTableField(comment = "类型 路径参数、请求参数、请求体参数")
    private String type;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除")
    private Boolean isDeleted;

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


}
