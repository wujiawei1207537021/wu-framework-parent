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

    private String name;

    @ApiModelProperty(allowEmptyValue = true, dataType = "List")
    private List<FieldConfig> fields;


    @Data
    public static class FieldConfig {
        private String name;
        private JavaBasicTypeDefaultValue type;
    }


}


