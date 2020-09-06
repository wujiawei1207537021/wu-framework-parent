package com.wuframework.pojo.po;


import io.swagger.annotations.*;
import lombok.Data;

@ApiModel(value = "", description = "统计执行结果")
@Data
public class StatisticalExecutionResultsPO {

    @ApiModelProperty(value = "执行总条数")
    private Integer total;

    @ApiModelProperty(value = "成功条数")
    private Integer success;

    @ApiModelProperty(value = "失败条数")
    private Integer fail;

    @ApiModelProperty(value = "描述信息")
    private String desc;
}
