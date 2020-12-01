package com.wu.framework.easy.stereotype.upsert.component;

import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.upsert.process.ElasticsearchEasyDataProcess;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * description Elasticsearch
 *
 * @author Jia wei Wu
 * @date 2020/10/22 下午1:55
 */
@EasyUpsertStrategy(value = EasyUpsertType.ES)
@ConditionalOnProperty(prefix = "spring.elasticsearch.rest", value = "uris")
class ElasticsearchEasyUpsert implements IEasyUpsert {

    private final ElasticsearchRestClientProperties elasticsearchRestClientProperties;
    private final ElasticsearchEasyDataProcess elasticsearchEasyDataProcess;

    ElasticsearchEasyUpsert(ElasticsearchRestClientProperties elasticsearchRestClientProperties, ElasticsearchEasyDataProcess elasticsearchEasyDataProcess) {
        this.elasticsearchRestClientProperties = elasticsearchRestClientProperties;
        this.elasticsearchEasyDataProcess = elasticsearchEasyDataProcess;
    }

    /**
     * description 异步发送
     *
     * @param
     * @param elasticsearchPreProcessResult
     * @param dataPack
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:02
     */
    protected void asySend(ElasticsearchEasyDataProcess.ElasticsearchPreProcessResult elasticsearchPreProcessResult,
                           String uri,
                           ElasticsearchEasyDataProcess.ElasticsearchProcessResult dataPack) {
        WebClient webClient = WebClient.create(uri);
        Mono<String> bodyToMono = webClient
                .put()
                .uri("/{1}/{2}",
                        elasticsearchPreProcessResult.getIndex(),
                        elasticsearchPreProcessResult.getIndexType())  //服务请求路径，基于baseUrl
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dataPack)   //发送请求体
                .retrieve() // 获取响应体
                .bodyToMono(String.class);//响应数据类型转换
        System.out.println(bodyToMono.block());
    }

    @Override
    public <T> Object upsert(List<T> list) throws Exception {
        ElasticsearchEasyDataProcess.ElasticsearchPreProcessResult processResult =
                elasticsearchEasyDataProcess.classAnalyze(list.get(0).getClass());
        easyUpsertExecutor.execute(() -> {
            elasticsearchRestClientProperties.getUris().forEach(uri -> {
                for (T sourceData : list) {
                    ElasticsearchEasyDataProcess.ElasticsearchProcessResult dataPack =
                            elasticsearchEasyDataProcess.dataPack(sourceData);
                    asySend(processResult, uri, dataPack);
                }
            });
        });
        return true;
    }
}
