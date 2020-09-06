package com.wu.framework.inner.template.https;

import com.wu.framework.inner.template.https.config.HttpsAuthorization;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;

import java.util.List;

public interface HttpsRestOperations {

     /**
      * 自定义 数据返回格式
      * @param url
      * @param request
      * @param responseType
      * @param uriVariables
      * @param <T>
      * @return
      * @throws RestClientException
      */
     <T> List<T> postForXm(String url, @Nullable Object request, Class<T> responseType,
                           Object... uriVariables) throws RestClientException;


     HttpsAuthorization getHttpsAuthorization();
}
