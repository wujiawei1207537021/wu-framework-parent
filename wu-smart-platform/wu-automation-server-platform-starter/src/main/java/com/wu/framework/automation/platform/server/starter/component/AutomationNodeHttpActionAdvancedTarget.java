package com.wu.framework.automation.platform.server.starter.component;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class AutomationNodeHttpActionAdvancedTarget extends AbstractAutomationNodeActionAdvanced implements AutomationNodeActionAdvanced {


    // 忽略的请求头
    final static List<String> IGNORED_HEARD = Arrays.asList("content-type", "transfer-encoding", "content-length");

    /**
     * 是否支持
     *
     * @param automationNodeAction
     * @return
     */
    @Override
    public boolean support(AutomationNodeAction automationNodeAction) {
        return AutomationNodeActionType.HTTP.equals(automationNodeAction.getAutomationNodeActionType());
    }

    /**
     * 节点执行
     *
     * @param action 执行
     */
    @Override
    public Result<Map<String, Object>> execute(AutomationNodeAction action) {
        // 初始化http信息
        String method = action.getHttpMethod();
//        // 发起请求
//
        HttpMethod httpMethod = HttpMethod.valueOf(method); // 请求方式
        String url = action.getUrl();

        Map<String, List<String>> headers = new HashMap<>();

        Map<String, String> actionHeaders = action.getHeaders();
        if (!ObjectUtils.isEmpty(actionHeaders)) {
            headers = actionHeaders
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            stringStringEntry -> List.of(stringStringEntry.getValue()))); // 请求头信息、当前请求的信息也需要获取并且传递过来

        }
        Map<String, String> params = action.getParams(); // 请求参数
        String body = action.getBody(); // 请求体

        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(1024 * 10*20))//  //修改maxInMemorySize的缓存值，默认是256k。修改为10MB
                .build();

        // 设置请求接口地址
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);

        if (!ObjectUtils.isEmpty(params)) {
            // 设置参数
            params.forEach(uriComponentsBuilder::queryParam);
        }

        URI uri = uriComponentsBuilder.build().toUri();

        WebClient.RequestBodySpec requestBodySpec = webClient.method(httpMethod)
                .uri(uri);

        if(!ObjectUtils.isEmpty(headers)){
            // 设置请求头-----------------------
            headers.forEach((key, values) -> {
                if (!ObjectUtils.isEmpty(values) && !IGNORED_HEARD.contains(key.toLowerCase())) {
                    requestBodySpec.header(key, values.stream().filter(s -> !ObjectUtils.isEmpty(s)).toList().toArray(new String[0]));
                }
            });
        }
        // 设置请求体
        if (!ObjectUtils.isEmpty(body)) {
            requestBodySpec.body(BodyInserters.fromValue(body));
        }
        // 设置请求 content 类型
        requestBodySpec.contentType(MediaType.APPLICATION_JSON);
        // 返回数据类型

        // 返回数据
        Mono<?> bodyToMono = requestBodySpec.retrieve()
                .bodyToMono(Map.class)
                ;
        Object block = bodyToMono.block();
        return ResultFactory.successOf((Map<String, Object>) block);
    }


}
