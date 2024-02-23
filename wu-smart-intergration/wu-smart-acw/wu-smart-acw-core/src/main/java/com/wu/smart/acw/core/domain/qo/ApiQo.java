package com.wu.smart.acw.core.domain.qo;

import com.wu.framework.response.mark.ValidType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * API  uo
 *
 * @author Jia wei Wu
 */
@Data
@Accessors(chain = true)
public class ApiQo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 应用id
     */
    @NotNull(message = "应用id 不能为空", groups = ValidType.Create.class)
    private Long applicationId;

    /**
     * 标签 默认根据表进行分组
     */
    private String tag;

    /**
     * 请求类型
     */
    @NotNull(message = "请求类型 不能为空", groups = ValidType.Create.class)
    private RequestMethod method = RequestMethod.GET;

    /**
     * api 路径
     */
    @NotNull(message = "api 路径 不能为空", groups = ValidType.Create.class)
    private String path;


    /**
     * 参数
     */
    private List<ApiParamQo> apiParamQoList;

    /**
     * api 返回结果
     */
    private List<ApiResultQo> apiResultQoList;


    /**
     * table names
     */
    @NotNull(message = "table names 不能为空", groups = ValidType.Create.class)
    private List<String> tableNameList;

    /**
     * 接口描述
     */
    private String description;

}
