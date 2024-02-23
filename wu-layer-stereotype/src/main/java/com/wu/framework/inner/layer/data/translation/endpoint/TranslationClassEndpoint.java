package com.wu.framework.inner.layer.data.translation.endpoint;

import lombok.Data;

import java.util.List;

/**
 * description 转译class
 *
 * @author 吴佳伟
 * @date 2023/09/22 15:57
 */
@Data
public class TranslationClassEndpoint {
    /**
     * 转译class
     */
    private Class<?> translationClass;
    /**
     * 当前转译class 中的转译字段
     */
    private List<TranslationFieldEndpoint> translationFieldEndpointList;
}
