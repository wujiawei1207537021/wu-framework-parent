package com.wu.smart.acw.domain;


import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ClassCode {

    private Long id;
    private String projectName;

    private String packageName;
    private String name;


}
