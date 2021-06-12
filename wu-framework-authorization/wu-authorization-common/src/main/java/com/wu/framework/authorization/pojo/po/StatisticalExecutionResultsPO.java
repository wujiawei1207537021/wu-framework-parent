package com.wu.framework.authorization.pojo.po;


import lombok.Data;

@Data
public class StatisticalExecutionResultsPO {


    private Integer total;

    private Integer success;

    private Integer fail;

    private String desc;
}
