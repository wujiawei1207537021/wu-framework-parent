package com.wu.smart.acw.client;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class WebClientFactory {


    /**
     * 获取 webclient 的代理对象
     *
     * @param baseUrl           访问路径 如：http://127.0.0.1:8080
     * @param webClientApiClass apiclass
     * @param <T>               T代理对象
     * @return 返回 T 的代理对象
     */
    public static  <T> T acwWebClientProxyApi(String baseUrl, Class<T> webClientApiClass) {
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return httpServiceProxyFactory.createClient(webClientApiClass);
    }
}
