package com.wu.framework.play.platform.domain;

import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/10/1 21:11
 */
@Data
@LazyTable(comment = "翻译数据")
public class TranslateUo {
    @LazyTableFieldId(comment = "数据ID")
    private Long id;

    @LazyTableFieldUnique(comment = "源文本类型", columnType = "varchar(10)")
    private String from;

    @LazyTableFieldUnique(comment = "目标文本类型", columnType = "varchar(10)")
    private String to;

    @LazyTableFieldUnique(comment = "源文本", columnType = "varchar(255)")
    private String sourceWord;

    @LazyTableField(comment = "翻译后的文本", columnType = "text")
    private String translateWord;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableFieldCreateTime(name = "create_time", comment = "创建时间", columnType = "datetime")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableFieldUpdateTime(name = "update_time", comment = "更新时间", columnType = "datetime")
    private LocalDateTime updateTime;
}
