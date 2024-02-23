package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint;


import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTranslationAPI;
import lombok.Data;

import java.util.List;

/**
 * description 转译数据
 *
 * @author 吴佳伟
 * @date 2023/09/25 14:53
 */
@Data
public class LazyTransferDataClassEndpoint {
    /**
     * 转译的原始字段信息
     */
    private List<LazyTransferDataFieldEndpoint> transferDataFieldEndpointList;

    /**
     * 当前准一适配器
     */
    private LazyTranslationAPI targetLazyTranslationAPI;

}
