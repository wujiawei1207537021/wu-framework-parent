package com.wu.framework.inner.layer.data.translation.endpoint;

import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import lombok.Data;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * description 转译数据
 *
 * @author 吴佳伟
 * @date 2023/09/25 14:53
 */
@Data
public class TransferDataFieldEndpoint {
    /**
     * 转译的原始字段信息
     */
    private TranslationFieldEndpoint translationFieldEndpoint;
    /**
     * 转译数据
     */
    private ConcurrentMap<String, Object> transferDataMap;

    /**
     * 当前准一适配器
     */
    private TranslationAPI targetTranslationAPI;


    /**
     * 转译字段是个对象 包含多个字段
     */
    private List<TransferDataFieldEndpoint> transferDataFieldEndpointList;

}
