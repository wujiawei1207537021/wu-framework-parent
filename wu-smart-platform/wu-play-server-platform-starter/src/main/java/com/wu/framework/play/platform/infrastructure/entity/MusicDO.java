package com.wu.framework.play.platform.infrastructure.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.stereotype.LayerField.LayerFieldType;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.String;
import java.time.LocalDateTime;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import java.lang.Long;
/**
 * describe 音乐 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "music",comment = "音乐")
@Schema(title = "music",description = "音乐")
public class MusicDO {


    /**
     * 
     * 专辑
     */
    @Schema(description ="专辑",name ="album",example = "")
    @LazyTableField(name="album",comment="专辑",columnType="varchar(255)")
    private String album;

    /**
     * 
     * 文件类型
     */
    @Schema(description ="文件类型",name ="contentType",example = "")
    @LazyTableField(name="content_type",comment="文件类型",columnType="varchar(255)")
    private String contentType;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER,columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     * 
     * 时长
     */
    @Schema(description ="时长",name ="duration",example = "")
    @LazyTableField(name="duration",comment="时长",columnType="varchar(255)")
    private String duration;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    @LazyTableFieldId(name = "id", comment = "主键")
    private Long id;

    /**
     * 
     * 音乐数据
     */
    @Schema(description ="音乐数据",name ="musicData",example = "")
    @LazyTableField(name="music_data",comment="音乐数据",columnType="longblob")
    private byte[] musicData;

    /**
     * 
     * 音乐地址
     */
    @Schema(description ="音乐地址",name ="musicUrl",example = "")
    @LazyTableField(name="music_url",comment="音乐地址",columnType="varchar(255)")
    private String musicUrl;

    /**
     * 
     * 音乐名称
     */
    @Schema(description ="音乐名称",name ="name",example = "")
    @LazyTableField(name="name",comment="音乐名称",columnType="varchar(255)")
    private String name;

    /**
     * 
     * 文件大小(粗略)
     */
    @Schema(description ="文件大小(粗略)",name ="roughlySize",example = "")
    @LazyTableField(name="roughly_size",comment="文件大小(粗略)",columnType="varchar(255)")
    private String roughlySize;

    /**
     * 
     * 歌手
     */
    @Schema(description ="歌手",name ="singer",example = "")
    @LazyTableField(name="singer",comment="歌手",columnType="varchar(255)")
    private String singer;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",defaultValue="CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER,columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}