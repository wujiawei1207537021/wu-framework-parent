package com.wu.smart.acw.server.domain.model.model.database.instance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "database_instance",description = "")
public class DatabaseInstance {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 链接驱动
     */
    @Schema(description ="链接驱动",name ="driverClassName",example = "")
    private String driverClassName;

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
    private String id;

    /**
     * 
     * 初始化数据库到本地
     */
    @Schema(description ="初始化数据库到本地",name ="initializeToLocal",example = "")
    private Boolean initializeToLocal;

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
     * 链接地址
     */
    @Schema(description ="链接地址",name ="url",example = "")
    private String url;

    /**
     * 
     * 用户名
     */
    @Schema(description ="用户名",name ="username",example = "")
    private String username;

}