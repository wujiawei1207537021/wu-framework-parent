package com.wuframework.system.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "MenuNode对象", description = "tree菜单结构")
public class MenuNode implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icon;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuNode> children;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String isLeaf;

}
