package com.wu.framework.inner.layer.stereotype.analyze;

import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2021/4/26 4:42 下午
 */
@Accessors(chain = true)
@Data
public class AnalyzeParameter {
    private Class clazz;
    public Method method;
}
