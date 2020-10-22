package com.wuframework.system.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeNode<P> implements Serializable {
    private String key;
    private String title;
    private String value;
    private Boolean disabled;
    @JsonInclude(Include.NON_NULL)
    private String disableCheckbox;
    @JsonInclude(Include.NON_NULL)
    private String icon;
    @JsonInclude(Include.NON_NULL)
    private Boolean isLeaf;
    @JsonInclude(Include.NON_NULL)
    private String selectable;
    @JsonInclude(Include.NON_NULL)
    @JsonUnwrapped(
            prefix = "data_"
    )
    private P data;
    @JsonInclude(Include.NON_EMPTY)
    private List<TreeNode> children;

    private boolean leaf;
}
