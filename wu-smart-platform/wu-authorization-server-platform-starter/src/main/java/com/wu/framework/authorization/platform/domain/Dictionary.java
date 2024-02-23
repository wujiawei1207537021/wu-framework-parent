package com.wu.framework.authorization.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@LazyTable(tableName = "dictionary",schema = "acw")
@ApiModel(value = "dictionary",description = "字典")
public class Dictionary {

    /**
     * 
     * id
     */
    @ApiModelProperty(value ="id",name ="id",example = "")
    @LazyTableField(name ="id",comment = "id")
    private Long id;
    /**
     * 
     * 字典名称
     */
    @ApiModelProperty(value ="字典名称",name ="name",example = "")
    @LazyTableField(name ="name",comment = "字典名称")
    private String name;
    /**
     * 
     * 字典编码
     */
    @ApiModelProperty(value ="字典编码",name ="code",example = "")
    @LazyTableField(name ="code",comment = "字典编码")
    private String code;
    /**
     * 
     * 类型
     */
    @ApiModelProperty(value ="类型",name ="type",example = "")
    @LazyTableField(name ="type",comment = "类型")
    private BigDecimal type;
    /**
     * 
     * 描述
     */
    @ApiModelProperty(value ="描述",name ="description",example = "")
    @LazyTableField(name ="description",comment = "描述")
    private String description;
    /**
     * 
     * 创建时间
     */
    @ApiModelProperty(value ="创建时间",name ="createTime",example = "")
    @LazyTableField(name ="create_time",comment = "创建时间")
    private LocalDateTime createTime;
    /**
     * 
     * 更新时间
     */
    @ApiModelProperty(value ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name ="update_time",comment = "更新时间")
    private LocalDateTime updateTime;
}