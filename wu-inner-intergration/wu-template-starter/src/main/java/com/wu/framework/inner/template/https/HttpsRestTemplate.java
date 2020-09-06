package com.wu.framework.inner.template.https;

import com.wu.framework.inner.template.https.factory.HttpsClientRequestFactory;
import com.wu.framework.inner.template.https.config.HttpsAuthorization;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestClientException;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpsRestTemplate extends HttpsRestTemplateAbstract implements HttpsRestOperations {


    private final HttpsAuthorization httpsAuthorization;


    public HttpsRestTemplate(HttpsClientRequestFactory httpsClientRequestFactory, HttpsAuthorization httpsAuthorization) {
        super(httpsClientRequestFactory);
        this.httpsAuthorization = httpsAuthorization;
        List<HttpMessageConverter<?>> messageConverters = this.getMessageConverters();
        messageConverters.remove(1);// 移除原先的转换器
        HttpMessageConverter<?> httpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        messageConverters.set(1, httpMessageConverter);
        this.setMessageConverters(messageConverters);
    }


    @Override
    public <T> List<T> postForXm(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return null;
    }


    @Override
    public HttpsAuthorization getHttpsAuthorization() {
        return httpsAuthorization;
    }

}
