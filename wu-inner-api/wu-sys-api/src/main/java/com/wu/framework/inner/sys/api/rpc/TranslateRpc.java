package com.wu.framework.inner.sys.api.rpc;

import com.wu.framework.inner.sys.api.ConvertApi;
import com.wu.framework.inner.sys.api.TranslateApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * @author 吴佳伟
 * @description
 * @company 杭州来回科技有限公司
 * @date 2021/9/15$ 11:15 上午$
 */
@ConditionalOnMissingBean(TranslateApi.class)
public class TranslateRpc implements TranslateApi {
    /**
     * 中文转换成英文
     *
     * @param keyword
     * @param type
     * @return
     */
    @Override
    public String translate(String keyword, LanguageType type) {
        return keyword;
    }
}
