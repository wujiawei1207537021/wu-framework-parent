package com.wu.framework.tts.server.platform.starter.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.Long;
import java.lang.String;
import java.lang.Boolean;
import java.time.LocalDateTime;
/**
 * describe tts 音色 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDTO 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "tts_timbre_command_dto",description = "tts 音色")
public class TtsTimbreDTO {


    /**
     * 
     * 主键ID
     */
    @Schema(description ="主键ID",name ="id",example = "")
    private Long id;

    /**
     * 
     * 音色名称
     */
    @Schema(description ="音色名称",name ="name",example = "")
    private String name;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description ="修改时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

    /**
     * 
     * 音色code
     */
    @Schema(description ="音色code",name ="code",example = "")
    private String code;

}