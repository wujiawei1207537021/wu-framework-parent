package com.wu.smart.acw.webflux.simple;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/07/17 12:27
 */

@SpringBootApplication
public class AcwWebFluxApplication {
    public static void main(String[] args) {
        SpringApplication.run(AcwWebFluxApplication.class, args);
    }

    @Bean
    public GroupedOpenApi tweetsOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = { "/tweets/**" };
        return GroupedOpenApi.builder().
                group("tweets")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Tweets API").version(appVersion)))
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi streamOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = { "/stream/**" };
        String[] packagedToMatch = { "org.springdoc.demo.app3" };
        return GroupedOpenApi.builder().group("x-stream")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Stream API").version(appVersion)))
                .pathsToMatch(paths).packagesToScan(packagedToMatch)
                .build();
    }

}
