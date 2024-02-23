package com.wu.smart.acw.server.application.command.acw.line.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * describe acw_line_code 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyStoryCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_line_code_story_command",description = "")
public class AcwLineCodeStoryCommand {


    /**
     * 
     * null
     */
    @Schema(description ="null",name ="code",example = "")
    private String code;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="line",example = "")
    private Long line;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="version",example = "")
    private Double version;

}