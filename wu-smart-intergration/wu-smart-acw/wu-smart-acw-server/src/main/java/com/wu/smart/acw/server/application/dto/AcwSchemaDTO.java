package com.wu.smart.acw.server.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.smart.acw.server.translation.InstanceIdTranslation2NameField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe : 数据库库信息
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/5/6 21:48
 */
@Accessors(chain = true)
@Data
@LazyTable(comment = "ACW 数据库库信息")
public class AcwSchemaDTO {


    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    private String instanceId;

    /**
     * 数据库实例ID
     */
    @InstanceIdTranslation2NameField(translationSourceName = "instanceId")
    @Schema(description = "数据库实例ID", name = "instanceName")
    private String instanceName;
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
     * 数据库名称
     */
    @Schema(description = "null", name = "schemaName")
    private String schemaName;
    /**
     * null
     */
    @Schema(description = "null", name = "characterSet")
    private String characterSet;
    /**
     * null
     */
    @Schema(description = "null", name = "sortingRules")
    private String sortingRules;
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
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    private Boolean isDeleted;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
     * 初始化数据库到本地
     */
    @Schema(description = "initializeToLocal", example = "1")
    private Boolean initializeToLocal;

}