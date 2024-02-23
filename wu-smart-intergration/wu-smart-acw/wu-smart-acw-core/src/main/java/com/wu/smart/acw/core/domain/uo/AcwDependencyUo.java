package com.wu.smart.acw.core.domain.uo;


import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :依赖
 * @date : 2021/9/19 11:48 上午
 */
@Accessors(chain = true)
@Data
@LazyTable(comment = "依赖")
public class AcwDependencyUo {
    @LazyTableFieldId
    private Long id;

    @LazyTableFieldUnique
    @Schema(description = "groupId", example = "top.wu2020")
    private String groupId;

    @LazyTableFieldUnique
    @Schema(description = "artifactId", example = "wu-database-lazy-starter")
    private String artifactId;

    @LazyTableFieldUnique
    @Schema(description = "version", example = "1.0.6")
    private String version;

    @Schema(description = "columnType", example = "pom")
    private String type;

    @Schema(description = "scope", example = "compile")
    private String scope;

    @Schema(description = "optional", example = "true")
    private boolean optional;
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
