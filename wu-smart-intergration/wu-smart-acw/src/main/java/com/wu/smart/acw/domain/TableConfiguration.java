package com.wu.smart.acw.domain;

import com.wu.framework.inner.layer.data.JavaBasicType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 表配置
 */
@Data
@Accessors(chain = true)
public class TableConfiguration {

    private String name;

    @ApiModelProperty(allowEmptyValue = true, dataType = "List")
    private List<FieldConfig> fields;


    @Data
    public static class FieldConfig {
        private String name;
        private JavaBasicType type;
    }


}


