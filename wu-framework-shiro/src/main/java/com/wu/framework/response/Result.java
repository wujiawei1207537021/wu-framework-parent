package com.wu.framework.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.wu.framework.response.mark.JsonViewType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonView(JsonViewType.Default.class)
public class Result<T> {

    private Integer code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


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
