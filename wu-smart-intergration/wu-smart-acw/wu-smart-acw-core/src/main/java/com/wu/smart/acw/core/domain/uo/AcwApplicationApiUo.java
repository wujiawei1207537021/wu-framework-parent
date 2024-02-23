package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import com.wu.framework.response.mark.ValidType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

/**
 * API  uo
 */
@Data
@Accessors(chain = true)
@LazyTable(comment = "应用API")
public class AcwApplicationApiUo {
    /**
     * 主键
     */
    @LazyTableFieldId
    private Long id;

    /**
     * 应用id
     */
    @LazyTableFieldUnique
    @NotNull(message = "应用id 不能为空", groups = ValidType.Create.class)
    private Long applicationId;

    /**
     * 标签 默认根据表进行分组
     */
    @LazyTableFieldUnique
    private String tag;

    /**
     * 请求类型
     */
    @LazyTableFieldUnique
    @NotNull(message = "请求类型 不能为空", groups = ValidType.Create.class)
    private RequestMethod method;

    /**
     * api 路径
     */
    @LazyTableFieldUnique
    @NotNull(message = "api 路径 不能为空", groups = ValidType.Create.class)
    private String path;


    /**
     * 接口描述
     */
    @LazyTableField(comment = "接口描述")
    private String description;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", defaultValue = "'0'")
    private Boolean isDeleted;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}
