package com.wu.framework.response.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @author wujiawei
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum DefaultMySQLEnum {
    /**
     * 正确，是的，有
     */
    TRUE(1, "正确，是的，有"),
    /**
     * 错误，不是的，没有
     */
    FALSE(0, "错误，不是的，没有");
    private Integer code;
    private String msg;

}