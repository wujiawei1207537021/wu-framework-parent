package com.wu.framework.authorization.platform.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "dictionary",  comment = "字典")
@Schema(title = "dictionary", description = "")
public class DictionaryDO {


    /**
     * 字典编码
     */
    @Schema(description = "字典编码", name = "code")
    @LazyTableField(name = "code", comment = "字典编码", columnType = "varchar(255)")
    private String code;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     * 描述
     */
    @Schema(description = "描述", name = "description")
    @LazyTableField(name = "description", comment = "描述", columnType = "varchar(255)")
    private String description;

    /**
     * id
     */
    @Schema(description = "id", name = "id")
    @LazyTableFieldId(name = "id", comment = "id")
    private Long id;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称", name = "name")
    @LazyTableField(name = "name", comment = "字典名称", columnType = "varchar(255)")
    private String name;

    /**
     * 类型
     */
    @Schema(description = "类型", name = "type")
    @LazyTableField(name = "type", comment = "类型", columnType = "varchar(255)")
    private String type;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}