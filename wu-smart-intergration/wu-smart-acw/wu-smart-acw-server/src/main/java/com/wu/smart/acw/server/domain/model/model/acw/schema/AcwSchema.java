package com.wu.smart.acw.server.domain.model.model.acw.schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe ACW 数据库库信息
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_schema", description = "ACW 数据库库信息")
public class AcwSchema {


    /**
     * null
     */
    @Schema(description = "null", name = "characterSet")
    private String characterSet;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;


    /**
     * null
     */
    @Schema(description = "null", name = "ext")
    private String ext;

    /**
     * 主键
     */
    @Schema(description = "主键", name = "id")
    private Long id;

    /**
     * 初始化数据库到本地
     */
    @Schema(description = "初始化数据库到本地", name = "initializeToLocal")
    private Boolean initializeToLocal;
    /**
     * 数据大小
     */
    @Schema(description = "数据大小")
    private String dataLength;

    /**
     * 索引大小
     */
    @Schema(description = "索引大小")
    private String indexLength;
    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    private String instanceId;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    private Boolean isDeleted;

    /**
     * 数据库名称
     */
    @Schema(description = "数据库名称", name = "schemaName")
    private String schemaName;

    /**
     * null
     */
    @Schema(description = "null", name = "sortingRules")
    private String sortingRules;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}