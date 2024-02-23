package com.wu.framework.inner.lazy.database.test.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/5/15 2:45 下午
 */
@Data
public class Address implements Serializable {


    private Long id;

    private String name;

    private double latitude;
    ;

    private double longitude;
}
