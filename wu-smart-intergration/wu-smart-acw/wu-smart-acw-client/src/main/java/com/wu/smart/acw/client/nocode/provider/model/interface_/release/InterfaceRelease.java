package com.wu.smart.acw.client.nocode.provider.model.interface_.release;

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
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "interface_release",description = "Dataway API 发布历史。")
public class InterfaceRelease {


    /**
     * 
     * 所属API ID
     */
    @Schema(description ="所属API ID",name ="pubApiId",example = "")
    private Integer pubApiId;

    /**
     * 
     * Publish ID
     */
    @Schema(description ="Publish ID",name ="pubId",example = "")
    private Integer pubId;

    /**
     * 
     * HttpMethod：GET、PUT、POST
     */
    @Schema(description ="HttpMethod：GET、PUT、POST",name ="pubMethod",example = "")
    private String pubMethod;

    /**
     * 
     * 拦截路径
     */
    @Schema(description ="拦截路径",name ="pubPath",example = "")
    private String pubPath;

    /**
     * 
     * 发布时间（下线不更新）
     */
    @Schema(description ="发布时间（下线不更新）",name ="pubReleaseTime",example = "")
    private LocalDateTime pubReleaseTime;

    /**
     * 
     * 请求/响应/请求头样本数据
     */
    @Schema(description ="请求/响应/请求头样本数据",name ="pubSample",example = "")
    private String pubSample;

    /**
     * 
     * 接口的请求/响应数据结构
     */
    @Schema(description ="接口的请求/响应数据结构",name ="pubSchema",example = "")
    private String pubSchema;

    /**
     * 
     * 查询脚本：xxxxxxx
     */
    @Schema(description ="查询脚本：xxxxxxx",name ="pubScript",example = "")
    private String pubScript;

    /**
     * 
     * 原始查询脚本，仅当类型为SQL时不同
     */
    @Schema(description ="原始查询脚本，仅当类型为SQL时不同",name ="pubScriptOri",example = "")
    private String pubScriptOri;

    /**
     * 
     * 状态：0有效，1无效（可能被下线）
     */
    @Schema(description ="状态：0有效，1无效（可能被下线）",name ="pubStatus",example = "")
    private Integer pubStatus;

    /**
     * 
     * 脚本类型：SQL、DataQL
     */
    @Schema(description ="脚本类型：SQL、DataQL",name ="pubType",example = "")
    private String pubType;

}