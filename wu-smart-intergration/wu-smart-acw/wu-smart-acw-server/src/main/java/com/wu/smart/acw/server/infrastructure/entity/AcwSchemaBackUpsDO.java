package com.wu.smart.acw.server.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldCreateTime;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUpdateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 数据库备份信息
 *
 * @author Jia wei Wu
 * @date 2023/07/09 08:49 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(comment = "数据库备份信息",tableName = "schema_back_ups")
@Schema(title = "schema_back_ups",description = "数据库备份信息")
public class AcwSchemaBackUpsDO {


    /**
     *
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableFieldCreateTime
    private LocalDateTime createTime;

    /**
     *
     * null
     */
    @Schema(description ="null",name ="id",example = "")
    @LazyTableField(name="id",comment="null",columnType="varchar(255)")
    private String id;

    /**
     *
     * 实例ID
     */
    @Schema(description ="实例ID",name ="instanceId",example = "")
    @LazyTableField(name="instance_id",comment="实例ID",columnType="varchar(255)")
    private String instanceId;

    /**
     *
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",columnType="tinyint(1)")
    private Boolean isDeleted;

    /**
     *
     * 备份文件地址
     */
    @Schema(description ="备份文件地址",name ="path",example = "")
    @LazyTableField(name="path",comment="备份文件地址",columnType="varchar(255)")
    private String path;

    /**
     *
     * 数据库名称
     */
    @Schema(description ="数据库名称",name ="schemaName",example = "")
    @LazyTableField(name="schema_name",comment="数据库名称",columnType="varchar(255)")
    private String schemaName;

    /**
     *
     * 状态：进行中、完成、失败
     */
    @Schema(description ="状态：进行中、完成、失败",name ="status",example = "")
    @LazyTableField(name="status",comment="状态：进行中、完成、失败",columnType="int")
    private Integer status;

    /**
     *
     * 数据库实例中对应的表数量
     */
    @Schema(description ="数据库实例中对应的表数量",name ="tableNum",example = "")
    @LazyTableField(name="table_num",comment="数据库实例中对应的表数量",columnType="int")
    private Integer tableNum;

    /**
     *
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableFieldUpdateTime
    private LocalDateTime updateTime;

}