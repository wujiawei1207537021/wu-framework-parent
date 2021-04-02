package com.wu.framework.easy.stereotype.upsert.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/3/5 10:06 下午
 */
@Accessors(chain = true)
@Data
public class JavaVerification {
    private boolean isJava;
    private String oldName;
    private String newName;
}
