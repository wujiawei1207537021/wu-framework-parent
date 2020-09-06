package com.wu.freamwork.domain;

import lombok.Data;

/**
 * description 数据返回实体
 *
 * @author 吴佳伟
 * @date 2020/6/22 10:50 上午
 */
@Data
public class ResultPo<T> {

    private String success;

    private Integer code;

    private String info;

    private String dataType;

    private T data;
}
