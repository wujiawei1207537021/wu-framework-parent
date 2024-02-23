package com.wu.smart.acw.server.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe Redis服务器 
 *
 * @author Jia wei Wu
 * @date 2023/07/26 04:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "acw_redis_instance",comment = "Redis服务器")
@Schema(title = "acw_redis_instance",description = "Redis服务器")
public class AcwRedisInstanceDO {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     * 
     * getHost
     */
    @Schema(description ="getHost",name ="getHost",example = "")
    @LazyTableField(name="getHost",comment="getHost",columnType="varchar(255)")
    private String host;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="id",example = "")
    @LazyTableFieldId(name="id")
    private Integer id;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="instanceName",example = "")
    @LazyTableField(name="instance_name",comment="",columnType="varchar(255)")
    private String instanceName;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",columnType="tinyint(1)")
    private Boolean isDeleted;

    /**
     * 
     * 密码
     */
    @Schema(description ="密码",name ="password",example = "")
    @LazyTableField(name="password",comment="密码",columnType="varchar(255)")
    private String password;

    /**
     * 
     * 端口
     */
    @Schema(description ="端口",name ="getPort",example = "")
    @LazyTableField(name="getPort",comment="端口",columnType="int")
    private Integer port;

    /**
     * 
     * 状态
     */
    @Schema(description ="状态",name ="status",example = "")
    @LazyTableField(name="status",comment="状态",columnType="int")
    private Integer status;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

    /**
     * 
     * 用户名
     */
    @Schema(description ="用户名",name ="username",example = "")
    @LazyTableField(name="username",comment="用户名",columnType="varchar(255)")
    private String username;

}