package com.wu.framework.easy.temple.domain;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;


public class Demo {

    public static void main(String[] args) throws IOException {
        WebClient webClient = WebClient.create("http://127.0.0.1:9200/_bulk");
        FileSystemResource resource = new FileSystemResource("/Users/xx/Desktop/a.json1");
        Mono<String> bodyToMono = webClient
                .post()
                .uri("/_bulk")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(resource)
                .retrieve() // 获取响应体
                .bodyToMono(String.class);//响应数据类型转换
        System.out.println(bodyToMono.block());

    }


}
