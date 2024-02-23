package com.wu.framework.play.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * describe : 音乐
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/18 20:44
 */
@LazyTable(comment = "音乐")
@Data
public class MusicUo {

    /**
     * 主键
     */
    @Schema(description = "主键", name = "id")
    @LazyTableFieldId(name = "id", comment = "主键")
    private Long id;

    @LazyTableField(comment = "音乐名称")
    private String name;

    @LazyTableField(comment = "音乐数据", columnType = "longblob")
    private byte[] musicData;

    @LazyTableField(comment = "音乐地址")
    private String musicUrl;

    @LazyTableField(comment = "歌手")
    private String singer;

    @LazyTableField(comment = "专辑")
    private String album;

    @LazyTableField(comment = "时长")
    private String duration;

    @LazyTableField(comment = "文件类型")
    private String contentType;

    @LazyTableField(comment = "文件大小(粗略)")
    private String roughlySize;

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
