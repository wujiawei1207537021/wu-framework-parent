package com.wu.framework.play.platform.application.command.file;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.lang.String;
import java.lang.Long;
/**
 * describe file 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyUpdateCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "file_update_command",description = "")
public class FileUpdateCommand {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 文件数据
     */
    @Schema(description ="文件数据",name ="data",example = "")
    private byte[] data;

    /**
     * 
     * 文件描述
     */
    @Schema(description ="文件描述",name ="describe",example = "")
    private String describe;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    private Long id;

    /**
     * 
     * 数据大小
     */
    @Schema(description ="数据大小",name ="length",example = "")
    private String length;

    /**
     * 
     * 文件名称
     */
    @Schema(description ="文件名称",name ="name",example = "")
    private String name;

    /**
     * 
     * 文件类型
     */
    @Schema(description ="文件类型",name ="type",example = "")
    private String type;

    /**
     * 
     * 文件uid
     */
    @Schema(description ="文件uid",name ="uid",example = "")
    private String uid;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}