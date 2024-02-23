package com.wu.smart.acw.core.domain.qo;

import com.wu.framework.inner.layer.data.JavaBasicTypeDefaultValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 表配置
 */
@Data
@Accessors(chain = true)
public class TableConfigurationQo {

    @ApiModelProperty(value = "项目ID")
    private Long projectId = 1L;

    @ApiModelProperty(value = "表中文名称")
    private String tableZhName;

    @ApiModelProperty(allowEmptyValue = true, dataType = "List")
    private List<FieldConfig> fields;


    @Data
    public static class FieldConfig {
        private String name;
        private JavaBasicTypeDefaultValue type;
    }


}


