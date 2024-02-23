package com.wu.smart.acw.server.application.command;

import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe Redis服务器 
 *
 * @author Jia wei Wu
 * @date 2023/07/26 04:09 下午
 * @see DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_redis_instance_command",description = "Redis服务器")
public class AcwRedisInstanceCommand {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * getHost
     */
    @Schema(description ="getHost",name ="getHost",example = "")
    private String host;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="id",example = "")
    private Integer id;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="instanceName",example = "")
    private String instanceName;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 密码
     */
    @Schema(description ="密码",name ="password",example = "")
    private String password;

    /**
     * 
     * 端口
     */
    @Schema(description ="端口",name ="getPort",example = "")
    private Integer port;

    /**
     * 
     * 状态
     */
    @Schema(description ="状态",name ="status",example = "")
    private Integer status;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

    /**
     * 
     * 用户名
     */
    @Schema(description ="用户名",name ="username",example = "")
    private String username;

}