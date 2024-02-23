package com.wu.smart.acw.server.infrastructure.entity;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import com.wu.framework.inner.lazy.stereotype.field.LazyFieldCondition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * describe 表关联关系
 *
 * @author Jia wei Wu
 * @date 2023/11/01 10:32 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "acw_table_association_relation", comment = "表关联关系")
@Schema(title = "acw_table_association_relation", description = "表关联关系")
public class AcwTableAssociationRelationDO {


    /**
     * 数据库实例schema
     */
    @Schema(description = "数据库实例schema", name = "schema")
    @LazyTableField(name = "schema", comment = "数据库实例schema", lazyTableIndexs = {@LazyTableIndex(indexName = "s_i_r_r_s_s_t", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(14)")
    private String schema;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP", upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime createTime;

    /**
     *
     */
    @Schema(description = "", name = "id")
    @LazyTableField(name = "id", comment = "", key = true, lazyTableIndexs = {@LazyTableIndex(indexName = "i", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(255)")
    private String id = UUID.randomUUID().toString();

    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    @LazyTableField(name = "instance_id", comment = "数据库实例ID", lazyTableIndexs = {@LazyTableIndex(indexName = "s_i_r_r_s_s_t", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(50)")
    private String instanceId;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
    private Boolean isDeleted;

    /**
     * 关系，最大是100
     */
    @LazyFieldCondition(ignore = true)
    @Schema(description = "关系，最大是100", name = "relation")
    @LazyTableField(name = "relation", comment = "关系，最大是100", columnType = "double")
    private double relation;

    /**
     * 关系表
     */
    @Schema(description = "关系表", name = "relationTable")
    @LazyTableField(name = "relation_table", comment = "关系表", lazyTableIndexs = {@LazyTableIndex(indexName = "s_i_r_r_s_s_t", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(50)")
    private String relationTable;

    /**
     * 关系表字段
     */
    @Schema(description = "关系表字段", name = "relationTableColumn")
    @LazyTableField(name = "relation_table_column", comment = "关系表字段", lazyTableIndexs = {@LazyTableIndex(indexName = "s_i_r_r_s_s_t", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(50)")
    private String relationTableColumn;

    /**
     * 原始表字段
     */
    @Schema(description = "原始表字段", name = "sourceTableColumn")
    @LazyTableField(name = "source_table_column", comment = "原始表字段", lazyTableIndexs = {@LazyTableIndex(indexName = "s_i_r_r_s_s_t", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(50)")
    private String sourceTableColumn;

    /**
     * 原始表
     */
    @Schema(description = "原始表", name = "sourceTable")
    @LazyTableField(name = "source_table", comment = "原始表", lazyTableIndexs = {@LazyTableIndex(indexName = "s_i_r_r_s_s_t", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(50)")
    private String sourceTable;

    /**
     * 类型(1 自动产生的、2 手动添加的)
     */
    @Schema(description = "类型(1 自动产生的、2 手动添加的)", name = "type")
    @LazyTableField(name = "type", comment = "类型(1 自动产生的、2 手动添加的)", lazyTableIndexs = {@LazyTableIndex(indexName = "s_i_r_r_s_s_t", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "int")
    private Integer type;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP", upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime updateTime;

}