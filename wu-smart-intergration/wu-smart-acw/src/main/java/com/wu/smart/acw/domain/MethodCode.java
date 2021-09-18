package com.wu.smart.acw.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class MethodCode {
    private Long classId;
    private String name;

}
