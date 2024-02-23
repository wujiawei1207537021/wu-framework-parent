package com.wu.smart.acw.core.domain.qo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * API  返回结果对象
 *
 * @author Jia wei Wu
 */
@Data
@Accessors(chain = true)
public class ApiResultQo {
    /**
     * id
     */
    private Long id;
    /**
     * 数据库字段ID
     */
    private String columnName;


    /**
     * apiId
     */
    private Long apiId;

    /**
     * 别名
     */
    private String aliasName;

}
