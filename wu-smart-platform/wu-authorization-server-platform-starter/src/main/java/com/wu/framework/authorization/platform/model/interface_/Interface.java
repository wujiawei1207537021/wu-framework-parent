package com.wu.framework.authorization.platform.model.interface_;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.List;

/**
 * describe 接口
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain
 **/
@Data
@Accessors(chain = true)
@Schema(title = "interface", description = "接口")
public class Interface {


    /**
     * 应用名称
     */
    @Schema(description = "应用名称", name = "applicationName")
    private String applicationName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;

    /**
     * 接口描述
     */
    @Schema(description = "接口描述", name = "description")
    private String description;

    /**
     * 父路径
     */
    @Schema(description = "父路径", name = "parentPath")
    private List<String> parentPath;

    /**
     * 路径
     */
    @Schema(description = "路径", name = "path")
    private List<String> path;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法", name = "requestMethods")
    private List<RequestMethod> requestMethods;

    /**
     * 接口标签
     */
    @Schema(description = "接口标签", name = "tag")
    private List<String> tag;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

}