package com.wu.smart.acw.client.nocode.provider.model.interface_.out.param;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 接口输出参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "interface_out_param",description = "接口输出参数")
public class InterfaceOutParam {


    /**
     * 
     * 接口ID
     */
    @Schema(description ="接口ID",name ="apiId",example = "")
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
    @Schema(description ="主键",name ="id",example = "")
    private String id;

    /**
     * 
     * 输出参数映射名称 如：age  默认等于name
     */
    @Schema(description ="输出参数映射名称 如：age  默认等于name",name ="mappingName",example = "")
    private String mappingName;

    /**
     * 
     * 输出参数名称 如：age
     */
    @Schema(description ="输出参数名称 如：age",name ="name",example = "")
    private String name;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}