package com.wu.framework.authorization.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * describe : 角色
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 18:35
 */
@LazyTable(comment = "角色")
@Data
public class Role {


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id", example = "")
    @LazyTableField(name = "id", comment = "主键")
    private Long id;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除", name = "isDeleted", example = "")
    @LazyTableField(name = "is_deleted", comment = "是否删除")
    private Boolean isDeleted;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "")
    @LazyTableField(name = "create_time", comment = "创建时间")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "")
    @LazyTableField(name = "update_time", comment = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 角色code
     */
    @LazyTableField(comment = "角色code")
    private String code;

    /**
     * 菜单名称
     */
    @LazyTableField(comment = "角色名称")
    private String name;
    /**
     * 角色父编码
     */
    @LazyTableField(comment = "角色父编码")
    private String parentCode;

}
