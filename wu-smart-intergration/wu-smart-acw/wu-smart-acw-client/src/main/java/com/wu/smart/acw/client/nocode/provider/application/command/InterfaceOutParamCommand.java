package com.wu.smart.acw.client.nocode.provider.application.command;

import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 接口输出参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "interface_out_param_command",description = "接口输出参数")
public class InterfaceOutParamCommand {


    /**
     * 
     * 接口ID
     */
    @Schema(description ="接口ID",name ="apiId",example = "1")
    private Integer apiId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "1")
    private String id;

    /**
     * 
     * 输出参数映射名称 如：age  默认等于name
     */
    @Schema(description ="输出参数映射名称 如：age  默认等于name",name ="mappingName",example = "apiMethod")
    private String mappingName;

    /**
     * 
     * 输出参数名称 如：age
     */
    @Schema(description ="输出参数名称 如：age",name ="name",example = "api_method")
    private String name;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}