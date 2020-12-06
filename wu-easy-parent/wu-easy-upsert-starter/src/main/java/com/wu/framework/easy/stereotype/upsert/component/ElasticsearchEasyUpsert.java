package com.wu.framework.easy.stereotype.upsert.component;

import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.upsert.process.ElasticsearchEasyDataProcess;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description Elasticsearch
 *
 * @author Jia wei Wu
 * @date 2020/10/22 下午1:55
 */
@EasyUpsertStrategy(value = EasyUpsertType.ES)
@ConditionalOnProperty(prefix = "spring.elasticsearch.rest", value = "uris")
class ElasticsearchEasyUpsert implements IEasyUpsert , InitializingBean {

    private final ElasticsearchRestClientProperties elasticsearchRestClientProperties;
    private final ElasticsearchEasyDataProcess elasticsearchEasyDataProcess;
    private final UpsertConfig upsertConfig;
    protected final Map<String, WebClient> WEB_CLIENT_MAP = new HashMap<>();
    protected final WebClient webClient = WebClient.builder().build();

    ElasticsearchEasyUpsert(ElasticsearchRestClientProperties elasticsearchRestClientProperties, ElasticsearchEasyDataProcess elasticsearchEasyDataProcess, UpsertConfig upsertConfig) {
        this.elasticsearchRestClientProperties = elasticsearchRestClientProperties;
        this.elasticsearchEasyDataProcess = elasticsearchEasyDataProcess;
        this.upsertConfig = upsertConfig;
    }

    /**
     * description 异步发送
     *
     * @param
     * @param dataPack
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:02
     */
    protected void asySend(String uri, File file) {
        Mono<String> bodyToMono = webClient
                .post()
                .uri(uri + "/_bulk")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(new FileSystemResource(file))//发送请求体
                .retrieve() // 获取响应体
                .bodyToMono(String.class);//响应数据类型转换
        System.out.println(bodyToMono.block());
        file.delete();
    }

    @Override
    public <T> Object upsert(List<T> list) throws Exception {
        ElasticsearchEasyDataProcess.ElasticsearchPreProcessResult processResult =
                elasticsearchEasyDataProcess.classAnalyze(list.get(0).getClass());
        easyUpsertExecutor.execute(() -> {
            // 文件写入本地
            List<List<T>> splitList = splitList(list, upsertConfig.getBatchLimit());
            for (List<T> ts : splitList) {
                elasticsearchEasyDataProcess.writeFileToLocal(ts);
            }
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
            File file = new File(upsertConfig.getCacheFileAddress());
            final File[] listFiles = file.listFiles((dir, name) -> {
                String fileName = name.toLowerCase();
                return fileName.endsWith(ElasticsearchEasyDataProcess.ES_UPSERT_FILE_SUFFIX);
            });
            // 发送数据
            for (File listFile : listFiles) {
                asySend(uri, listFile);
            }
        });
    }
}
