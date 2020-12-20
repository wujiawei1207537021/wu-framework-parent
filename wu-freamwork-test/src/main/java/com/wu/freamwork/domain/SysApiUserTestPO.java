package com.wu.freamwork.domain;

import com.wu.inner.sys.adapter.stereotype.ConvertField;
import lombok.Data;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/11/7 下午4:42
 */
@Data
public class SysApiUserTestPO {

    private String name;
    private Integer age;

    @ConvertField(convertItem = "SEX", defaultValue = "未知的多尴尬")
    private Integer sex;

    private String sexName;

}
