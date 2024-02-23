package com.wu.smart.acw.client.config;


import com.wu.smart.acw.client.advanced.GenerateLocalJavaAdvanced;
import com.wu.smart.acw.client.properties.AcwServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description 配置参数
 *
 * @author 吴佳伟
 * @date 2023/10/20 13:16
 */
@Configuration
@EnableConfigurationProperties(value = AcwServerProperties.class)
public class AcwClientEnableAutoConfiguration {

//    @Bean
//    public WebClient webClient(AcwServerProperties acwServerProperties) {
//        return WebClient.builder()
//                .baseUrl(acwServerProperties.getServerUrl())
//                .build();
//    }
//
//    @Bean
//    public AcwServerClientInstanceApi acwServerClientInstanceApi(WebClient webClient) {
//        HttpServiceProxyFactory httpServiceProxyFactory =
//                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
//                        .build();
//        return httpServiceProxyFactory.createClient(AcwServerClientInstanceApi.class);
//    }

    @Bean
    public GenerateLocalJavaAdvanced generateLocalJavaAdvanced() {
        return new GenerateLocalJavaAdvanced();
    }

}
