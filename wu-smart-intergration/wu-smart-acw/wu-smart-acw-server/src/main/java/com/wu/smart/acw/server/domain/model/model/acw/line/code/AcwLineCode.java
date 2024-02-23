package com.wu.smart.acw.server.domain.model.model.acw.line.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_line_code",description = "代码行信息")
public class AcwLineCode {


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