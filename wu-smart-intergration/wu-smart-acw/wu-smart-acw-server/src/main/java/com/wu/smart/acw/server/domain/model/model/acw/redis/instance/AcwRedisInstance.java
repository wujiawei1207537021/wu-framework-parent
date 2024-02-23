package com.wu.smart.acw.server.domain.model.model.acw.redis.instance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe Redis服务器
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_redis_instance", description = "Redis服务器")
public class AcwRedisInstance {


    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;

    /**
     * getHost
     */
    @Schema(description = "getHost", name = "getHost")
    private String host;

    /**
     * null
     */
    @Schema(description = "null", name = "id")
    private Integer id;

    /**
     *
     */
    @Schema(description = "", name = "instanceName")
    private String instanceName;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    private Boolean isDeleted;

    /**
     * 密码
     */
    @Schema(description = "密码", name = "password")
    private String password;

    /**
     * 端口
     */
    @Schema(description = "端口", name = "getPort")
    private Integer port;

    /**
     * 状态
     */
    @Schema(description = "状态", name = "status")
    private Integer status;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

    /**
     * 用户名
     */
    @Schema(description = "用户名", name = "username")
    private String username;

}