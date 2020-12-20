package com.wu.framework.easy.stereotype.upsert.component;

import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.upsert.process.ElasticsearchEasyDataProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.List;

/**
 * description Elasticsearch
 *
 * @author Jia wei Wu
 * @date 2020/10/22 下午1:55
 */
@Slf4j
@EasyUpsertStrategy(value = EasyUpsertType.ES)
@ConditionalOnProperty(prefix = "spring.elasticsearch.rest", value = "uris")
public class ElasticsearchEasyUpsert implements IEasyUpsert, InitializingBean {

    private final ElasticsearchRestClientProperties elasticsearchRestClientProperties;
    private final ElasticsearchEasyDataProcess elasticsearchEasyDataProcess;
    private final UpsertConfig upsertConfig;
    protected final WebClient webClient = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer
                    .defaultCodecs()
                    .maxInMemorySize(16 * 1024 * 1024))
            .build())
            .build();

    ElasticsearchEasyUpsert(ElasticsearchRestClientProperties elasticsearchRestClientProperties, ElasticsearchEasyDataProcess elasticsearchEasyDataProcess, UpsertConfig upsertConfig) {
        this.elasticsearchRestClientProperties = elasticsearchRestClientProperties;
        this.elasticsearchEasyDataProcess = elasticsearchEasyDataProcess;
        this.upsertConfig = upsertConfig;
    }

    /**
     * description 异步发送
     *
     * @param uri
     * @param file
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:02
     */
    protected void asySend(String uri, File file) {
        Mono<String> bodyToMono = webClient
                .post()
                .uri(uri + "/_bulk")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FileSystemResource(file))//发送请求体
                .exchange()
                .doOnSuccess(clientResponse -> System.out.println("clientResponse.statusCode() = " + clientResponse.statusCode()))
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
        bodyToMono.block();
//        System.out.println(bodyToMono.block());
        file.delete();
    }

    @Override
    public <T> Object upsert(List<T> list) throws Exception {

        Integer total = (list.size() + upsertConfig.getBatchLimit() - 1) / upsertConfig.getBatchLimit();
        log.info("计划处理写入文件 【{}】 个", total);
        int stepCount = 1;
        // 文件写入本地
        List<List<T>> splitList = splitList(list, upsertConfig.getBatchLimit());
        for (List<T> ts : splitList) {
            log.info("处理步写入文件 【{}】 步 ,总文件 【{}】", stepCount, total);
            elasticsearchEasyDataProcess.writeFileToLocal(ts);
            stepCount++;
        }
        log.info("分步写入本地文件完成✅");
        easyUpsertExecutor.execute(() -> {
            send();
        });
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        send();
    }

    private void send() {
        elasticsearchRestClientProperties.getUris().forEach(uri -> {
            // 指定类型文件
            File cacheFile = new File(upsertConfig.getCacheFileAddress());
            final File[] listFiles = cacheFile.listFiles((dir, name) -> {
                String fileName = name.toLowerCase();
                return fileName.endsWith(ElasticsearchEasyDataProcess.ES_UPSERT_FILE_SUFFIX);
            });
            // 发送数据
            for (File file : listFiles) {
                log.info("分步发送ES数据 【{}】 个", file);
                asySend(uri, file);
            }
        });
    }
}
