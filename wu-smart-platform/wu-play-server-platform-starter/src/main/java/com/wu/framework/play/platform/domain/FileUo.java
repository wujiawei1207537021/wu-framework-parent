package com.wu.framework.play.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * describe : 文件信息
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/22 23:23
 */
@LazyTable(comment = "文件信息")
@Data
public class FileUo {

    /**
     * 主键
     */
    @Schema(description = "主键", name = "id")
    @LazyTableFieldId(name = "id", comment = "主键")
    private Long id;

    @LazyTableFieldUnique(comment = "文件uid")
    private String uid;

    @LazyTableField(comment = "文件名称")
    private String name;

    @LazyTableField(comment = "文件类型")
    private String type;

    @LazyTableField(comment = "文件数据", columnType = "longblob")
    private byte[] data;

    @LazyTableField(comment = "数据大小")
    private String length;

    @LazyTableField(comment = "文件描述")
    private String describe;
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
