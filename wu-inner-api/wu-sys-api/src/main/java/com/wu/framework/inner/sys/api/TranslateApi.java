package com.wu.framework.inner.sys.api;


/**
 * 翻译
 */
public interface TranslateApi {


    /**
     * 中文转换成英文
     * @param keyword
     * @return
     */
    String translate(String keyword,LanguageType type);


    /**
     * 语言类型
     */
    enum LanguageType{
        zh,
        en
    }

}
