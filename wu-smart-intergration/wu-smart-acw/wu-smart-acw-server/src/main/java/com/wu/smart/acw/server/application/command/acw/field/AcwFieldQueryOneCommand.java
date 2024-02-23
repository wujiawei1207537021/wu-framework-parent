package com.wu.smart.acw.server.application.command.acw.field;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 字段对象 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyQueryOneCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_field_query_one_command",description = "字段对象")
public class AcwFieldQueryOneCommand {


    /**
     * 
     * 
     */
    @Schema(description ="",name ="classId",example = "")
    private Long classId;

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
     * 
     */
    @Schema(description ="",name ="name",example = "")
    private String name;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="type",example = "")
    private String type;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}