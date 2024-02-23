package com.wu.smart.acw.server.application.command.acw.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;
/**
 * describe ACW 行 code 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyUpdateCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_code_update_command",description = "ACW 行 code")
public class AcwCodeUpdateCommand {


    /**
     * 
     * null
     */
    @Schema(description ="null",name ="acwLineCodeList",example = "")
    private Map acwLineCodeList;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="fileType",example = "")
    private String fileType;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="name",example = "")
    private String name;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="packageName",example = "")
    private String packageName;

}