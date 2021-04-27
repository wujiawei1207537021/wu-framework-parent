package com.wu.framework.inner.layer.stereotype.analyze;

import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;

/**
 * description 分析参数对象
 *
 * @author 吴佳伟
 * @date 2021/4/26 4:42 下午
 */
@Accessors(chain = true)
@Data
public class AnalyzeParameter {
    private Class clazz;
    public Method method;

    private AnalyzeParameter() {
    }

    public static AnalyzeParameter create() {
        return new AnalyzeParameter();
    }
}
