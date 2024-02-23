package com.wu.smart.acw.client.nocode.provider.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe Dataway API 发布历史。 
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "interface_release",comment = "Dataway API 发布历史。")
@Schema(title = "interface_release",description = "Dataway API 发布历史。")
public class InterfaceReleaseDO {


    /**
     * 
     * 所属API ID
     */
    @Schema(description ="所属API ID",name ="pubApiId",example = "")
    @LazyTableField(name="pub_api_id",comment="所属API ID",notNull=true,columnType="int")
    private Integer pubApiId;

    /**
     * 
     * Publish ID
     */
    @Schema(description ="Publish ID",name ="pubId",example = "")
    @LazyTableFieldId(name = "pub_id", comment = "Publish ID")
    private Integer pubId;

    /**
     * 
     * HttpMethod：GET、PUT、POST
     */
    @Schema(description ="HttpMethod：GET、PUT、POST",name ="pubMethod",example = "")
    @LazyTableField(name="pub_method",comment="HttpMethod：GET、PUT、POST",notNull=true,columnType="varchar(12)")
    private String pubMethod;

    /**
     * 
     * 拦截路径
     */
    @Schema(description ="拦截路径",name ="pubPath",example = "")
    @LazyTableField(name="pub_path",comment="拦截路径",notNull=true,columnType="varchar(512)")
    private String pubPath;

    /**
     * 
     * 发布时间（下线不更新）
     */
    @Schema(description ="发布时间（下线不更新）",name ="pubReleaseTime",example = "")
    @LazyTableField(name="pub_release_time",comment="发布时间（下线不更新）",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra="")
    private LocalDateTime pubReleaseTime;

    /**
     * 
     * 请求/响应/请求头样本数据
     */
    @Schema(description ="请求/响应/请求头样本数据",name ="pubSample",example = "")
    @LazyTableField(name="pub_sample",comment="请求/响应/请求头样本数据",columnType="mediumtext")
    private String pubSample;

    /**
     * 
     * 接口的请求/响应数据结构
     */
    @Schema(description ="接口的请求/响应数据结构",name ="pubSchema",example = "")
    @LazyTableField(name="pub_schema",comment="接口的请求/响应数据结构",columnType="mediumtext")
    private String pubSchema;

    /**
     * 
     * 查询脚本：xxxxxxx
     */
    @Schema(description ="查询脚本：xxxxxxx",name ="pubScript",example = "")
    @LazyTableField(name="pub_script",comment="查询脚本：xxxxxxx",notNull=true,columnType="mediumtext")
    private String pubScript;

    /**
     * 
     * 原始查询脚本，仅当类型为SQL时不同
     */
    @Schema(description ="原始查询脚本，仅当类型为SQL时不同",name ="pubScriptOri",example = "")
    @LazyTableField(name="pub_script_ori",comment="原始查询脚本，仅当类型为SQL时不同",notNull=true,columnType="mediumtext")
    private String pubScriptOri;

    /**
     * 
     * 状态：0有效，1无效（可能被下线）
     */
    @Schema(description ="状态：0有效，1无效（可能被下线）",name ="pubStatus",example = "")
    @LazyTableField(name="pub_status",comment="状态：0有效，1无效（可能被下线）",notNull=true,columnType="int")
    private Integer pubStatus;

    /**
     * 
     * 脚本类型：SQL、DataQL
     */
    @Schema(description ="脚本类型：SQL、DataQL",name ="pubType",example = "")
    @LazyTableField(name="pub_type",comment="脚本类型：SQL、DataQL",notNull=true,columnType="varchar(24)")
    private String pubType;
    /**
     *
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",defaultValue="'0'",columnType="tinyint(1)")
    private Boolean isDeleted;

}