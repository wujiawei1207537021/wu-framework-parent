package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint;


import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTranslationAPI;
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
public class LazyTransferDataFieldEndpoint {
    /**
     * 转译的原始字段信息
     */
    private LazyTranslationFieldEndpoint lazyTranslationFieldEndpoint;
    /**
     * 转译数据
     */
    private ConcurrentMap<String, Object> transferDataMap;

    /**
     * 当前准一适配器
     */
    private LazyTranslationAPI targetLazyTranslationAPI;


    /**
     * 转译字段是个对象 包含多个字段
     */
    private List<LazyTransferDataFieldEndpoint> transferDataFieldEndpointList;

}
