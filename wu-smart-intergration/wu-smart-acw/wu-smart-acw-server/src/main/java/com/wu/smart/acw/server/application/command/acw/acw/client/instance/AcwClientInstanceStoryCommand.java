package com.wu.smart.acw.server.application.command.acw.acw.client.instance;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.lang.Long;
import java.lang.String;
/**
 * describe 客户端实例 
 *
 * @author Jia wei Wu
 * @date 2023/12/05 09:32 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyStoryCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_client_instance_story_command",description = "客户端实例")
public class AcwClientInstanceStoryCommand {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="id",example = "")
    private Long id;

    /**
     * 
     * 客户端IP
     */
    @Schema(description ="客户端IP",name ="ip",example = "")
    private String ip;

    /**
     * 
     * 客户端路径
     */
    @Schema(description ="客户端路径",name ="path",example = "")
    private String path;

    /**
     * 
     * 客户端端口
     */
    @Schema(description ="客户端端口",name ="getPort",example = "")
    private Integer port;
    /**
     * 客户端ID 当前客户端自己的ID如果为空默认是ip
     */
    private String clientId;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}