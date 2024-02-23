package com.wu.smart.acw.server.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 数据库备份信息 
 *
 * @author Jia wei Wu
 * @date 2023/07/09 11:24 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(comment = "数据库备份信息",tableName = "instance_back_ups")
@Schema(title = "instance_back_ups",description = "数据库备份信息")
public class AcwInstanceBackUpsDO {


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
     * 完成数据库数量
     */
    @Schema(description ="完成数据库数量",name ="schemaNum",example = "")
    @LazyTableField(name="schema_num",comment="完成数据库数量",columnType="int")
    private Integer schemaNum;

    /**
     * 
     * 状态：进行中、完成、失败
     */
    @Schema(description ="状态：进行中、完成、失败",name ="status",example = "")
    @LazyTableField(name="status",comment="状态：进行中、完成、失败",columnType="int")
    private Integer status;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableFieldUpdateTime
    private LocalDateTime updateTime;

}