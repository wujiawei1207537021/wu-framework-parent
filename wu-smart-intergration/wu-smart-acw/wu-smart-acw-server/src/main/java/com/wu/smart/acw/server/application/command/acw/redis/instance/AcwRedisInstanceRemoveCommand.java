package com.wu.smart.acw.server.application.command.acw.redis.instance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe Redis服务器 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_redis_instance_remove_command",description = "Redis服务器")
public class AcwRedisInstanceRemoveCommand {


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
     * 
     */
    @Schema(description ="",name ="id",example = "")
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