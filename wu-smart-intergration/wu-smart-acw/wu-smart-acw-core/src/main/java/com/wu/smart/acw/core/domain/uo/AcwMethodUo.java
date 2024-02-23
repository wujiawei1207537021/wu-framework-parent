package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 方法编码
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/9/19 3:40 下午
 */
@Accessors(chain = true)
@Data
@LazyTable(comment = "ACW 方法")
public class AcwMethodUo {
    /**
     * 主键
     */
    @LazyTableFieldId
    private Long id;
    /**
     * class id
     */
    private Long classId;
    /**
     * 方法名
     */
    private String name;
    /**
     * 出参数
     */
    private String outParams;
    /**
     * 入参
     */
    private String inParams;
    /**
     * 方法体
     */
    private String body;
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
