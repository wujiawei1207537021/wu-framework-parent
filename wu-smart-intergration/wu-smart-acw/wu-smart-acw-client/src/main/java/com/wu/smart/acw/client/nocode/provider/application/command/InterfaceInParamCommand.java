package com.wu.smart.acw.client.nocode.provider.application.command;

import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 接口输入参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "interface_in_param_command",description = "接口输入参数")
public class InterfaceInParamCommand {


    /**
     * 
     * 参数条件
     */
    @Schema(description ="参数条件 大于、等于、小于",name ="condition",example = ">")
    private String term;

    /**
     * 参数类型 路径参数、请求参数、请求体参数
     */
    @Schema(description ="参数类型 路径参数、请求参数、请求体参数",name ="type",example = "")
    private String type;
    /**
     * 
     * 接口ID
     */
    @Schema(description ="接口ID",name ="apiId",example = "1")
    private Integer apiId;

    /**
     *
     * 参数名称 如：age
     */
    @Schema(description ="参数名称 如：age",name ="name",example = "api_path")
    private String name;

    /**
     * 参数数据类型
     */
    @Schema(description ="参数数据类型",name ="dataType",example = "")
    private String dataType;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 参数默认值 参数默认值类型  1:静态参数 如 1，2，3，4，5，6，7，8，9  2:动态参数 如 ${age} 3:关联参数 如user.name
     */
    @Schema(description ="参数默认值 参数默认值类型  1:静态参数 如 1，2，3，4，5，6，7，8，9  2:动态参数 如 ${age} 3:关联参数 如user.name",
            name ="defaultValue",
            example = "/demo"
    )
    private String defaultValue;

    /**
     * 
     * 参数默认值类型 1:静态参数，2:动态参数
     */
    @Schema(description ="参数默认值类型 1:静态参数，2:动态参数",name ="defaultValueType",example = "1")
    private Integer defaultValueType;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "1")
    private String id;



    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}