package com.wu.framework.inner.layer.data.translation.endpoint;

import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import lombok.Data;

import java.util.List;

/**
 * description 转译数据
 *
 * @author 吴佳伟
 * @date 2023/09/25 14:53
 */
@Data
public class TransferDataClassEndpoint {
    /**
     * 转译的原始字段信息
     */
    private List<TransferDataFieldEndpoint> transferDataFieldEndpointList;

    /**
     * 当前准一适配器
     */
    private TranslationAPI targetTranslationAPI;

}
