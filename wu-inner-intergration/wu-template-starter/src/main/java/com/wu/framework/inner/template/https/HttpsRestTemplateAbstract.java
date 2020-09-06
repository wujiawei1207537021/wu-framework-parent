package com.wu.framework.inner.template.https;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Map;

/**
 * 处理请求加密
 */
public abstract class HttpsRestTemplateAbstract extends RestTemplate implements HttpsRestOperations {


     /**
      * Create a new instance of the {@link RestTemplate} based on the given {@link ClientHttpRequestFactory}.
      *
      * @param requestFactory the HTTP request factory to use
      * @see SimpleClientHttpRequestFactory
      * @see HttpComponentsClientHttpRequestFactory
      */
     public HttpsRestTemplateAbstract(ClientHttpRequestFactory requestFactory) {
          super(requestFactory);
     }

     @Override
     protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
          Class requestCallbackClass = requestCallback.getClass();
          if(ObjectUtils.isEmpty(getHttpsAuthorization().getBaseAuthorization())){
              logger.info("could not find HttpsAuthorization in your config used default  RestTemplate");
               return super.doExecute(url, method, requestCallback, responseExtractor);

          }
          try {
               Field field = requestCallbackClass.getDeclaredField("requestEntity");
               if (!field.isAccessible()) {
                    field.setAccessible(true);
               }
               HttpEntity templhttpEntity = (HttpEntity) field.get(requestCallback);
               Object body=templhttpEntity.getBody();
               HttpHeaders templhttpHeaders = templhttpEntity.getHeaders();
               HttpHeaders httpHeaders = new HttpHeaders();
               for (Map.Entry<String, String> stringStringEntry : templhttpHeaders.toSingleValueMap().entrySet()) {
                    httpHeaders.add(stringStringEntry.getKey(), stringStringEntry.getValue());
               }
               httpHeaders.add("Authorization", getHttpsAuthorization().getBaseAuthorization());
               httpHeaders.setContentType(MediaType.APPLICATION_JSON);
               HttpEntity httpEntity = new HttpEntity(body,httpHeaders);
               field.set(requestCallback, httpEntity);
               return super.doExecute(url, method, requestCallback, responseExtractor);
          } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException("请求发送失败");
          }

     }


}
