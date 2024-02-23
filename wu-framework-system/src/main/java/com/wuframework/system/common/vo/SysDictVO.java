package com.wuframework.system.common.vo;


import com.wuframework.system.common.entity.SysDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author uther: Xiongxz
 * @Date: 2018/7/11 13:13
 * @Description:
 */
@Data
@ApiModel("数据字典")
public class SysDictVO {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    @ApiModelProperty("标签名")
    private String name;

    @ApiModelProperty("数据值")
    private String value;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("排序（升序）")
    private BigDecimal sort;

    @ApiModelProperty("父级编号")
    private Long parentId;

    @ApiModelProperty("创建者")
    private Integer createBy;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("更新者")
    private Long updateBy;

    @ApiModelProperty("更新时间")
    private Date updateDate;

    @ApiModelProperty("备注信息")
    private String remarks;

    @ApiModelProperty("删除标记")
    private String delFlag;

    @ApiModelProperty("字典类型，前端自定义")
    private SysDict dictType;
}
