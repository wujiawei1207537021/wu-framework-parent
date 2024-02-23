package com.wu.framework.inner.sys.api.rpc;

import com.wu.framework.inner.sys.api.TranslateApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

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
