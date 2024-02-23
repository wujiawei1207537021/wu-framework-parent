package com.wu.framework.easy.stereotype.upsert.component;

import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.analyze.ElasticsearchEasyDataProcessAnalyze;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
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
public class ElasticsearchEasyUpsert implements IEasyUpsert, ElasticsearchEasyDataProcessAnalyze, InitializingBean {

    protected final WebClient webClient = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer
                    .defaultCodecs()
                    .maxInMemorySize(16 * 1024 * 1024))
            .build())
            .build();
    private final ElasticsearchRestClientProperties elasticsearchRestClientProperties;

    private final UpsertConfig upsertConfig;

    ElasticsearchEasyUpsert(ElasticsearchRestClientProperties elasticsearchRestClientProperties, UpsertConfig upsertConfig) {
        this.elasticsearchRestClientProperties = elasticsearchRestClientProperties;
        this.upsertConfig = upsertConfig;
    }

    public static String fileSize(long size) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return size + "B";
        } else {
            size = size / 1024;
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (size < 1024) {
            return size + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            // 因为如果以MB为单位的话，要保留最后1位小数，
            // 因此，把此数乘以100之后再取余
            size = size * 100;
            return size / 100 + "." + String.valueOf((size % 100)) + "MB";
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return size / 100 + "." + size % 100 + "GB";
        }
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
            writeFileToLocal(ts, upsertConfig.getCacheFileAddress());
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
        try {
            send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send() {
        elasticsearchRestClientProperties.getUris().forEach(uri -> {
            // 指定类型文件
            File cacheFile = new File(upsertConfig.getCacheFileAddress());
            final File[] listFiles = cacheFile.listFiles((dir, name) -> {
                String fileName = name.toLowerCase();
                return fileName.endsWith(ElasticsearchEasyDataProcessAnalyze.ES_UPSERT_FILE_SUFFIX);
            });
            int steps = 1;
            // 发送数据
            for (File file : listFiles) {
                log.info("分步发送ES数据 【{}】 大小【{}】M  总步数:【{}】当前步数:【{}】", file, fileSize(file.length()), listFiles.length, steps);
                asySend(uri, file);
                steps++;
            }
        });
    }
}
