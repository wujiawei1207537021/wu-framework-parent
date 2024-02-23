package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * describe :使用的代码注解
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/9/25 3:38 下午
 */
@Accessors(chain = true)
@Data
@LazyTable(comment = "ACW 使用的代码注解")
public class AcwAnnotationCode {

    @LazyTableField(notNull = true, defaultValue = "'哈哈哈'")
    private String className;

    private Map<String, Object> value;

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
