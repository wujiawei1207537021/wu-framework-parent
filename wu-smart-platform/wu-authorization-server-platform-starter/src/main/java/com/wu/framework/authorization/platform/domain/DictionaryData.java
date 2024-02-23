package com.wu.framework.authorization.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@LazyTable(tableName = "dictionary_data",schema = "acw")
@ApiModel(value = "dictionary_data",description = "字典数据")
public class DictionaryData {

    /**
     * 
     * id
     */
    @ApiModelProperty(value ="id",name ="id",example = "")
    @LazyTableField(name ="id",comment = "id")
    private Long id;
    /**
     * 
     * 数据编码
     */
    @ApiModelProperty(value ="数据编码",name ="code",example = "")
    @LazyTableField(name ="code",comment = "数据编码")
    private String code;
    /**
     * 
     * 数据名称
     */
    @ApiModelProperty(value ="数据名称",name ="name",example = "")
    @LazyTableField(name ="name",comment = "数据名称")
    private String name;
    /**
     * 
     * 描述
     */
    @ApiModelProperty(value ="描述",name ="description",example = "")
    @LazyTableField(name ="description",comment = "描述")
    private String description;
    /**
     * 
     * 字典编码
     */
    @ApiModelProperty(value ="字典编码",name ="dictionaryCode",example = "")
    @LazyTableField(name ="dictionary_code",comment = "字典编码")
    private String dictionaryCode;
    /**
     * 
     * 排序值
     */
    @ApiModelProperty(value ="排序值",name ="sortValue",example = "")
    @LazyTableField(name ="sort_value",comment = "排序值")
    private String sortValue;
    /**
     * 
     * 父节点编码
     */
    @ApiModelProperty(value ="父节点编码",name ="pcode",example = "")
    @LazyTableField(name ="pcode",comment = "父节点编码")
    private String pcode;
    /**
     * 
     * 更新时间
     */
    @ApiModelProperty(value ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name ="update_time",comment = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 
     * 创建时间
     */
    @ApiModelProperty(value ="创建时间",name ="createTime",example = "")
    @LazyTableField(name ="create_time",comment = "创建时间")
    private LocalDateTime createTime;
}