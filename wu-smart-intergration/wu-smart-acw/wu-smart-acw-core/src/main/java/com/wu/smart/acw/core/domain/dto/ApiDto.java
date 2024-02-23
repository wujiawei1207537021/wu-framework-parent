package com.wu.smart.acw.core.domain.dto;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import com.wu.framework.response.mark.ValidType;
import com.wu.smart.acw.core.domain.qo.ApiParamQo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * API  uo
 */
@Data
@Accessors(chain = true)
@LazyTable(comment = "API")
public class ApiDto {
    /**
     * 主键
     */
    @LazyTableFieldId
    private Long id;

    /**
     * 应用id
     */
    @LazyTableFieldUnique
    @NotNull(message = "应用id 不能为空", groups = ValidType.Create.class)
    private Long applicationId;

    /**
     * 标签 默认根据表进行分组
     */
    @LazyTableFieldUnique
    private String tag;

    /**
     * 请求类型
     */
    @LazyTableFieldUnique
    @NotNull(message = "请求类型 不能为空", groups = ValidType.Create.class)
    private RequestMethod method = RequestMethod.GET;

    /**
     * api 路径
     */
    @LazyTableFieldUnique
    @NotNull(message = "api 路径 不能为空", groups = ValidType.Create.class)
    private String path;


    /**
     * 接口描述
     */
    @LazyTableField(comment = "接口描述")
    private String description;

    /**
     * table ids
     */
    private List<String> tableNameList;

    /**
     * 路径参数
     */
    private List<ApiParamQo> apiParamQoList;


}
