package com.wu.smart.acw.core.domain.qo;

import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import com.wu.framework.response.mark.ValidType;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * API 下联 Method 表
 */
@Data
@Accessors(chain = true)
public class ApiDownLinkMethodQo {

    /**
     * 主键
     */
    private Long id;
    /**
     * api Id
     */
    @LazyTableFieldUnique
    @NotNull(message = "api Id 不能为空", groups = ValidType.Create.class)
    private Long apiId;

    /**
     * 项目id
     */
    @LazyTableFieldUnique
    @NotNull(message = "项目id 不能为空", groups = ValidType.Create.class)
    private Long projectId;
    /**
     * 项目id
     */
    @LazyTableFieldUnique
    @NotNull(message = "项目id 不能为空", groups = ValidType.Create.class)
    private Long methodId;

    /**
     * table name
     */
    @NotNull(message = "table name 不能为空", groups = ValidType.Create.class)
    private String tableName;

}
