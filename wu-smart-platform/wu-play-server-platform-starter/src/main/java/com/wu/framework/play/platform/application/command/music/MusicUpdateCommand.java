package com.wu.framework.play.platform.application.command.music;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.String;
import java.time.LocalDateTime;
import java.lang.Long;
/**
 * describe 音乐 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyUpdateCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "music_update_command",description = "音乐")
public class MusicUpdateCommand {


    /**
     * 
     * 专辑
     */
    @Schema(description ="专辑",name ="album",example = "")
    private String album;

    /**
     * 
     * 文件类型
     */
    @Schema(description ="文件类型",name ="contentType",example = "")
    private String contentType;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 时长
     */
    @Schema(description ="时长",name ="duration",example = "")
    private String duration;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    private Long id;

    /**
     * 
     * 音乐数据
     */
    @Schema(description ="音乐数据",name ="musicData",example = "")
    private byte[] musicData;

    /**
     * 
     * 音乐地址
     */
    @Schema(description ="音乐地址",name ="musicUrl",example = "")
    private String musicUrl;

    /**
     * 
     * 音乐名称
     */
    @Schema(description ="音乐名称",name ="name",example = "")
    private String name;

    /**
     * 
     * 文件大小(粗略)
     */
    @Schema(description ="文件大小(粗略)",name ="roughlySize",example = "")
    private String roughlySize;

    /**
     * 
     * 歌手
     */
    @Schema(description ="歌手",name ="singer",example = "")
    private String singer;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}