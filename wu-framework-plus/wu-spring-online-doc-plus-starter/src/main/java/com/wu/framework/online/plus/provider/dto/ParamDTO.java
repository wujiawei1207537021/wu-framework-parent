package com.wu.framework.online.plus.provider.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/10/13 14:40
 */
@Data
public class ParamDTO {
    // 参数类型
    @Schema(description = "参数类型")
    private Class<?> type;
    // 参数名称
    @Schema(description = "参数名称")
    private String name;
    // 参数名称对应需要展示的名称
    @Schema(description = "参数名称对应需要展示的名称")
    private String label;
}
