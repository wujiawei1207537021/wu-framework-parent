package com.wu.smart.acw.client.nocode.provider.application.dto;

import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * describe Dataway 中的API 
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDTO 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "interface_info_command",description = "Dataway 中的API")
public class InterfaceInfoDTO {


    /**
     * 
     * 注释
     */
    @Schema(description ="注释",name ="apiComment",example = "")
    private String apiComment;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

    /**
     * 
     * ID
     */
    @Schema(description ="ID",name ="apiId",example = "")
    private Integer apiId;

    /**
     * 
     * HttpMethod：GET、PUT、POST
     */
    @Schema(description ="HttpMethod：GET、PUT、POST",name ="apiMethod",example = "")
    private String apiMethod;

    /**
     * 
     * 拦截路径
     */
    @Schema(description ="拦截路径",name ="apiPath",example = "")
    private String apiPath;

    /**
     *
     * api返回结果类型 0单个对象，1集合对象，2分页对象
     */
    @Schema(description ="api返回结果类型 0单个对象，1集合对象，2分页对象",name ="apiResultType",example = "")
    private Integer apiResultType;

    /**
     * 
     * 状态：0草稿，1发布，2有变更，3禁用
     */
    @Schema(description ="状态：0草稿，1发布，2有变更，3禁用",name ="apiStatus",example = "")
    private Integer apiStatus;

    /**
     * 分组
     */
    @Schema(description = "分组", name = "tag", example = "分组")
    private String tag;
    /**
     * 使用的数据库
     */
    @Schema(description = "使用的数据库", name = "schema", example = "使用的数据库")
    private String schema;
    /**
     * 
     * 脚本类型：SQL、DataQL
     */
    @Schema(description ="脚本类型：SQL、DataQL",name ="apiType",example = "")
    private String apiType;
    /**
     * 执行类型: select、update、delete、insert、upsert
     */
    @Schema(description = "执行类型: select、update、delete、insert、upsert", name = "executeType")
    private String executeType;

    /**
     * api 关联的表
     */
    @Schema(description = "api 关联的表", name = "interfaceTableDTOList")
    private List<InterfaceTableDTO> interfaceTableDTOList;
    /**
     * api 入参
     */
    @Schema(description = "api 入参", name = "interfaceInParamDTOList")
    private List<InterfaceInParamDTO> interfaceInParamDTOList;
    /**
     * api 出参
     */
    @Schema(description = "api 出参", name = "interfaceOutParamDTOList")
    private List<InterfaceOutParamDTO> interfaceOutParamDTOList;
}