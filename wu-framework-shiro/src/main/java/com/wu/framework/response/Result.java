package com.wu.framework.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.wu.framework.response.mark.JsonViewType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonView(JsonViewType.Default.class)
public class Result<T> {

    @ApiModelProperty(value = "错误码", required = true)
    private Integer code;

    @ApiModelProperty(value = "信息描述", required = true)
    private String message;

    @ApiModelProperty(value = "返回数据", required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @ApiModelProperty(value = "其他附加信息", required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> ext;

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
