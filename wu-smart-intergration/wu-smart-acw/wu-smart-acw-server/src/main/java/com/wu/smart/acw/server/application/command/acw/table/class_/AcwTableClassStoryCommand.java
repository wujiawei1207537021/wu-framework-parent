package com.wu.smart.acw.server.application.command.acw.table.class_;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 表和class的关联表 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyStoryCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_table_class_story_command",description = "表和class的关联表")
public class AcwTableClassStoryCommand {


    /**
     * 
     * 
     */
    @Schema(description ="",name ="schema",example = "")
    private String schema;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="classId",example = "")
    private Long classId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="id",example = "")
    private Long id;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="instanceId",example = "")
    private String instanceId;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="projectId",example = "")
    private Long projectId;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="tableName",example = "")
    private String tableName;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}