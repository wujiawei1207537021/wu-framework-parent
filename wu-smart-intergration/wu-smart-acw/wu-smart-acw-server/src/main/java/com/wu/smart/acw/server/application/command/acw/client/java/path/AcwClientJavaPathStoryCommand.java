package com.wu.smart.acw.server.application.command.acw.client.java.path;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.String;
import java.time.LocalDateTime;
/**
 * describe 客户端使用创建Java代码常用路径 
 *
 * @author Jia wei Wu
 * @date 2023/12/08 06:04 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyStoryCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_client_java_path_story_command",description = "客户端使用创建Java代码常用路径")
public class AcwClientJavaPathStoryCommand {


    /**
     * 
     * 使用的路径
     */
    @Schema(description ="使用的路径",name ="absolutePath",example = "")
    private String absolutePath;

    /**
     * 
     * 客户端ID
     */
    @Schema(description ="客户端ID",name ="clientId",example = "")
    private String clientId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    private String id;

    /**
     * 
     * 常用的包名称
     */
    @Schema(description ="常用的包名称",name ="packageName",example = "")
    private String packageName;

    /**
     * 
     * 常用的数据库
     */
    @Schema(description ="常用的数据库",name ="schemaName",example = "")
    private String schemaName;

    /**
     * 
     * 实例ID
     */
    @Schema(description ="实例ID",name ="instanceId",example = "")
    private String instanceId;

}