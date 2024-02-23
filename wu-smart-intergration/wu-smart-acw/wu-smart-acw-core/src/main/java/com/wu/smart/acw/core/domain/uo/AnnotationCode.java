package com.wu.smart.acw.core.domain.uo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/9/25 3:38 下午
 */
@Accessors(chain = true)
@Data
public class AnnotationCode {

    private String className;
    private Map<String, Object> value;

}
