package com.wu.framework.online.plus.provider.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

/**
 * description 应用对应的接口
 *
 * @author 吴佳伟
 * @date 2023/10/13 14:46
 */
@Data
public class ApplicationPathDTO {
    // 路径
    @Schema(description = "路径")
    private String path;

    @Schema(description = "类名称")
    private String className;

    @Schema(description = "方法名称")
    private String methodName;
    /**
     * 请求方法
     */
    @Schema(description = "请求方法")
    private Set<RequestMethod> requestMethods;

    /**
     * 路径描述
     */
    @Schema(description = "路径描述")
    private String title;
    /**
     * 路径分组
     */
    @Schema(description = "路径分组")
    private String group;

    /**
     * 解析后出入参数
     */
    @Schema(description = "解析后出入参数")
    private AnalysisPathDTO analysisPathDTO;
}
