package com.wu.framework.easy.stereotype.upsert.process;

import com.wu.framework.easy.stereotype.upsert.EasyTable;
import lombok.Data;
import org.springframework.core.annotation.AnnotationUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * description Elasticsearch 数据预处理
 *
 * @author 吴佳伟
 * @date 2020/10/22 下午2:26
 */
public class ElasticsearchEasyDataProcess implements DataProcess {
    /**
     * description 类解析
     *
     * @param clazz
     * @return Object
     * @exception/throws
     * @author 吴佳伟
     * @date 2020/10/22 下午2:20
     */
    @Override
    public ProcessResult classAnalyze(Class clazz) {
        ElasticsearchPreProcessResult elasticsearchPreProcessResult = new ElasticsearchPreProcessResult();
        String index = clazz.getSimpleName();
        EasyTable table = AnnotationUtils.getAnnotation(clazz, EasyTable.class);
        if (null != table) {
            String prefix = table.indexPrefix();
            String format = table.indexFormat();
            String suffix = table.indexSuffix();
            index = prefix + LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)) + suffix;
        }
        elasticsearchPreProcessResult.setIndex(index);
        return elasticsearchPreProcessResult;
    }

    /**
     * description 数据包装
     *
     * @param sourceData 源数据
     * @return ProcessResult
     * @exception/throws
     * @author 吴佳伟
     * @date 2020/10/22 下午2:23
     */
    @Override
    public ProcessResult dataPack(Object sourceData) {
        return null;
    }


    /**
     * description Elasticsearch 预处理结果
     *
     * @author 吴佳伟
     * @date 2020/10/22 下午2:58
     */
    @Data
    public class ElasticsearchPreProcessResult implements DataProcess.ProcessResult {

        /**
         * Elasticsearch 索引
         */
        private String index;
    }

    /**
     * description Elasticsearch 处理结果
     *
     * @author 吴佳伟
     * @date 2020/10/22 下午3:16
     */
    @Data
    public class ElasticsearchProcessResult implements DataProcess.ProcessResult {


    }

}
