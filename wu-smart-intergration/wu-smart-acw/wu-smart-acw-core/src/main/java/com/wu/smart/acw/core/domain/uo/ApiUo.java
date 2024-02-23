package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import com.wu.framework.response.mark.ValidType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotNull;

/**
 * API  uo
 */
@Data
@Accessors(chain = true)
public class ApiUo {
    /**
     * 主键
     */
    @LazyTableField(indexType = LayerField.LayerFieldType.ID)
    private Long id;

    /**
     * 项目id
     */
    @LazyTableFieldUnique
    @NotNull(message = "项目id 不能为空", groups = ValidType.Create.class)
    private Long projectId;

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
     * table name
     */
    private String tableName;


}
