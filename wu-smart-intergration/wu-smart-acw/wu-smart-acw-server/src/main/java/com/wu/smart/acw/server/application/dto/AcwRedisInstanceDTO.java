package com.wu.smart.acw.server.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/07/26 18:52
 */
@Data
public class AcwRedisInstanceDTO {

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
    /**
     * 类型 0：实例、 1：database、2： key
     */
    private String type = "instance";
}
