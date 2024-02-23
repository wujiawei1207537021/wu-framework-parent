package com.wu.smart.acw.server.application.command.acw.redis.instance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * describe Redis服务器
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyQueryOneCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_redis_instance_query_one_command", description = "Redis服务器")
public class AcwRedisInstanceQueryKeyValuesCommand {


    /**
     * redis实例ID
     */
    @Schema(description = "redis实例ID", name = "instanceId")
    private String instanceId;

    /**
     * 连接的数据库
     */
    @Schema(description = "连接的数据库")
    private int database;

    private String key;



}