package com.wu.smart.acw.core.rpc;

import com.wu.framework.inner.layer.data.dictionary.api.TranslateApi;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class TranslateRpc implements TranslateApi {

    public final static String URL = "https://api.66mz8.com/api/translation.php?info={1}";
    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    /**
     * 中文转换成英文
     *
     * @param keyword
     * @param type
     * @return
     */
    @Override
    public String translate(String keyword, LanguageType type) {
        final Map<String, String> map = restTemplate.getForObject(URL, Map.class, keyword);
        return map.getOrDefault("fanyi", keyword);
    }

}
