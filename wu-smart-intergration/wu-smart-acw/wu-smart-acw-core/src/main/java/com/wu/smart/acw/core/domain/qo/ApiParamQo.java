package com.wu.smart.acw.core.domain.qo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * describe : 参数参数
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/8 23:44
 */
@Accessors(chain = true)
@Data
public class ApiParamQo {

    /**
     * id
     */
    private Long id;
    /**
     * 数据库字段ID
     */
    private String columnName;

    /**
     * 条件
     */
    private String term;

    /**
     * apiId
     */
    private Long apiId;

    /**
     * 类型 路径参数、请求参数、请求体参数
     */
    private String type;
    /**
     * 参数名称
     */
    private String name;


}
