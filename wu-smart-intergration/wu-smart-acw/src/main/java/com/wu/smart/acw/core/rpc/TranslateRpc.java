package com.wu.smart.acw.core.rpc;

import com.wu.framework.inner.layer.data.api.TranslateApi;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TranslateRpc implements TranslateApi {

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    public final static String url = "https://api.66mz8.com/api/translation.php?info={1}";

    /**
     * 中文转换成英文
     *
     * @param keyword
     * @param type
     * @return
     */
    @Override
    public String translate(String keyword, LanguageType type) {
        final Map<String,String> map = restTemplate.getForObject(url, Map.class, keyword);
        return map.getOrDefault("fanyi",keyword);
    }

}
