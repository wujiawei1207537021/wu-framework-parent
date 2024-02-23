package com.wu.smart.acw.server.domain.model.model.acw.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;
/**
 * describe ACW 行 code 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_code",description = "ACW 行 code")
public class AcwCode {


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