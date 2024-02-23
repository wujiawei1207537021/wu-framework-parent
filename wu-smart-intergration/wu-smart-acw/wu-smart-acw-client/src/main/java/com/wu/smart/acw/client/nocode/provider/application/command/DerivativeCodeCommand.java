package com.wu.smart.acw.client.nocode.provider.application.command;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.config.enums.WebArchitecture;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 生成接口代码
 */
@Data
public class DerivativeCodeCommand {

    /**
     *
     * ID
     */
    @Schema(description ="ID",name ="apiId",example = "")
    private Integer apiId;


    /**
     *
     * web架构
     */
    @Schema(description ="web架构",name ="webArchitecture",example = "")
    private WebArchitecture webArchitecture;


    /**
     *
     * ORM 架构
     */
    @Schema(description ="ORM 架构",name ="ormArchitecture",example = "")
    private OrmArchitecture ormArchitecture;
}
