package com.wu.smart.acw.server.application.command.acw.schema.back.ups;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 数据库备份信息 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyUpdateCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_schema_back_ups_update_command",description = "数据库备份信息")
public class AcwSchemaBackUpsUpdateCommand {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="id",example = "")
    private String id;

    /**
     * 
     * 实例ID
     */
    @Schema(description ="实例ID",name ="instanceId",example = "")
    private String instanceId;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 备份文件地址
     */
    @Schema(description ="备份文件地址",name ="path",example = "")
    private String path;

    /**
     * 
     * 数据库名称
     */
    @Schema(description ="数据库名称",name ="schemaName",example = "")
    private String schemaName;

    /**
     * 
     * 状态：进行中、完成、失败
     */
    @Schema(description ="状态：进行中、完成、失败",name ="status",example = "")
    private Integer status;

    /**
     * 
     * 数据库实例中对应的表数量
     */
    @Schema(description ="数据库实例中对应的表数量",name ="tableNum",example = "")
    private Integer tableNum;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}