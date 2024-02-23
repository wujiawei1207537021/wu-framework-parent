package com.wu.smart.acw.client.nocode.provider.application.command;

import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand;
import com.wu.framework.response.mark.ValidType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * describe 接口保存
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "interface_info_command", description = "Dataway 中的API")
public class InterfaceInfoSaveSQLCommand {


    /**
     * 注释
     */
    @Schema(description = "注释", name = "apiComment", example = "接口注释：如获取集合信息")
    private String apiComment;

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
     * ID
     */
    @Null(groups = {ValidType.Create.class}, message = "新增API主键必须为空")
    @Schema(description = "ID", name = "apiId", example = "1")
    private Integer apiId;

    /**
     * HttpMethod：GET、PUT、POST
     */
    @Schema(description = "HttpMethod：GET、PUT、POST", name = "apiMethod", example = "GET")
    private String apiMethod;

    /**
     * 拦截路径
     */
    @Schema(description = "拦截路径", name = "apiPath", example = "/demo")
    private String apiPath;

    /**
     * api返回结果类型 0单个对象，1集合对象，2分页对象
     */
    @Schema(description = "api返回结果类型 0单个对象，1集合对象，2分页对象", name = "apiResultType", example = "0")
    private Integer apiResultType;

    /**
     * 状态：0草稿，1发布，2有变更，3禁用
     */
    @Schema(description = "状态：0草稿，1发布，2有变更，3禁用", name = "apiStatus", example = "0")
    private Integer apiStatus;

    /**
     * 脚本类型：SQL、DataQL
     */
    @Schema(description = "脚本类型：SQL、DataQL", name = "apiType")
    private String apiType;


    /**
     * 需要执行的sql 可执行、不可执行
     */
    @Schema(description = "需要执行的sql 可执行、不可执行", name = "sql")
    private String sql;

}