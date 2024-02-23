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
 * describe : 字段对象
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/15 5:14 下午
 */
@Data
@Accessors(chain = true)
@LazyTable(comment = "字段对象")
public class AcwFieldUo {

    /**
     * 主键
     */
    @LazyTableFieldId
    private Long id;
    /**
     * class id
     */
    @LazyTableFieldUnique
    private Long classId;

    /**
     * 类型
     */
    @LazyTableField(columnType = "text")
    private AcwClassUo type;
    /**
     * 字段名称
     */
    @LazyTableFieldUnique
    private String name;
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
