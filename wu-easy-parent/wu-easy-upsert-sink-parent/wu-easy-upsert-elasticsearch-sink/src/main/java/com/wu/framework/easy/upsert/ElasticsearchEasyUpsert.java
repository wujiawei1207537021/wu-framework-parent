package com.wu.framework.easy.upsert;


import com.wu.framework.easy.upsert.analyze.ElasticsearchEasyDataProcessAnalyze;
import com.wu.framework.easy.upsert.autoconfigure.config.SpringBootElasticsearchConfigProperties;
import com.wu.framework.easy.upsert.autoconfigure.config.SpringUpsertAutoConfigure;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.core.dynamic.IEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.exception.UpsertException;
import com.wu.framework.inner.layer.data.ClassSchema;
import com.wu.framework.inner.layer.util.ByteSizeUtil;
import com.wu.framework.inner.layer.util.LazyListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Role;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * description Elasticsearch
 *
 * @author Jia wei Wu
 * @date 2020/10/22 下午1:55
 */
@Slf4j
@EasyUpsertStrategy(value = EasyUpsertType.ES)
@ConditionalOnProperty(prefix = SpringBootElasticsearchConfigProperties.ELASTICSEARCH_PROPERTIES_PREFIX, value = "uris")
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ElasticsearchEasyUpsert implements IEasyUpsert, ElasticsearchEasyDataProcessAnalyze, InitializingBean {

    protected final WebClient webClient = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                    .codecs(configurer -> configurer
                            .defaultCodecs()
                            .maxInMemorySize(16 * 1024 * 1024))
                    .build())
            .build();
    private final SpringBootElasticsearchConfigProperties elasticsearchProperties;

    private final SpringUpsertAutoConfigure springUpsertAutoConfigure;

    ElasticsearchEasyUpsert(SpringBootElasticsearchConfigProperties elasticsearchProperties, SpringUpsertAutoConfigure springUpsertAutoConfigure) {
        this.elasticsearchProperties = elasticsearchProperties;
        this.springUpsertAutoConfigure = springUpsertAutoConfigure;
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
    public <T> Object upsert(List<T> list, ClassSchema classSchema) throws UpsertException, ExecutionException, InterruptedException {

        Integer total = (list.size() + springUpsertAutoConfigure.getBatchLimit() - 1) / springUpsertAutoConfigure.getBatchLimit();
        log.info("计划处理写入文件 【{}】 个", total);
        int stepCount = 1;
        // 文件写入本地
        List<List<T>> splitList = LazyListUtils.splitList(list, springUpsertAutoConfigure.getBatchLimit());
        for (List<T> ts : splitList) {
            log.info("处理步写入文件 【{}】 步 ,总文件 【{}】", stepCount, total);
            writeFileToLocal(ts, springUpsertAutoConfigure.getCacheFileAddress());
            stepCount++;
        }
        log.info("分步写入本地文件完成✅");
        EASY_UPSERT_EXECUTOR.execute(() -> {
            send();
        });
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send() {
        elasticsearchProperties.getUris().forEach(uri -> {
            // 指定类型文件
            File cacheFile = new File(springUpsertAutoConfigure.getCacheFileAddress());
            final File[] listFiles = cacheFile.listFiles((dir, name) -> {
                String fileName = name.toLowerCase();
                return fileName.endsWith(ElasticsearchEasyDataProcessAnalyze.ES_UPSERT_FILE_SUFFIX);
            });
            int steps = 1;
            // 发送数据
            for (File file : listFiles) {
                log.info("分步发送ES数据 【{}】 大小【{}】M  总步数:【{}】当前步数:【{}】", file, ByteSizeUtil.convertSize(file.length()), listFiles.length, steps);
                asySend(uri, file);
                steps++;
            }
        });
    }
}
