package com.wu.framework.easy.stereotype.upsert.process;

import com.alibaba.fastjson.JSONObject;
import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.converter.CamelAndUnderLineConverter;
import com.wu.framework.easy.stereotype.upsert.converter.JavaBasicTypeConversion;
import com.wu.framework.easy.stereotype.upsert.util.FileUtil;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description Elasticsearch 数据预处理
 *
 * @author Jia wei Wu
 * @date 2020/10/22 下午2:26
 */
public class ElasticsearchEasyDataProcess implements DataProcess {
    public static final String ES_UPSERT_FILE_SUFFIX = ".upes";
    protected static final Map<Class, String> CLAZZ_INDEX = new ConcurrentHashMap<>();
    private final static String INDEX_FORMAT = "{\"index\":{\"_index\":\"%s\",\"_type\":\"%s\"}}";
    private final UpsertConfig upsertConfig;

    public ElasticsearchEasyDataProcess(UpsertConfig upsertConfig) {
        this.upsertConfig = upsertConfig;
    }

    /**
     * description 类解析
     *
     * @param clazz
     * @return Object
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:20
     */
    @Override
    public ElasticsearchPreProcessResult classAnalyze(Class clazz) {
        ElasticsearchPreProcessResult elasticsearchPreProcessResult = new ElasticsearchPreProcessResult();
        String index = CamelAndUnderLineConverter.humpToMidLine2(clazz.getSimpleName());
        String indexType = "doc";
        EasySmart table = AnnotationUtils.getAnnotation(clazz, EasySmart.class);
        if (null != table) {
            String prefix = table.indexPrefix();
            String format = table.indexFormat();
            String suffix = table.indexSuffix();
            indexType = table.indexType();
            if (ObjectUtils.isEmpty(format)) {
                index = prefix + suffix;
            } else {
                index = prefix + LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)) + suffix;
            }
        }
        elasticsearchPreProcessResult.setIndex(index);
        elasticsearchPreProcessResult.setIndexType(indexType);
        return elasticsearchPreProcessResult;
    }


    /**
     * description 数据包装
     *
     * @param sourceData 源数据
     * @return ProcessResult
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:23
     */
    @Override
    public ElasticsearchProcessResult dataPack(Object sourceData) {
        Assert.notNull(sourceData, "sourceData must not be null.");
        Field[] declaredFields = sourceData.getClass().getDeclaredFields();
        ElasticsearchProcessResult<String, Object> source = new ElasticsearchProcessResult<>();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            EasySmartField tableField = AnnotationUtils.getAnnotation(field, EasySmartField.class);
            String fieldName = CamelAndUnderLineConverter.humpToLine2(field.getName());
            Object fieldValue = new Object();
            try {
                fieldValue = JavaBasicTypeConversion.toString(field.get(sourceData));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!ObjectUtils.isEmpty(tableField)) {
                if (!tableField.exist()) {
                    continue;
                }
                fieldName = tableField.name();
            }
            source.put(fieldName, fieldValue);
        }
        return source;
    }

    /**
     * @param
     * @return
     * @describe 获取类格式化后的 bulk索引文档
     * @author Jia wei Wu
     * @date 2020/12/6 5:29 下午
     **/
    protected String getBulkIndexDoc(Class clazz) {
        if (!CLAZZ_INDEX.containsKey(clazz)) {
            ElasticsearchPreProcessResult elasticsearchPreProcessResult = classAnalyze(clazz);
            CLAZZ_INDEX.put(clazz, String.format(INDEX_FORMAT, elasticsearchPreProcessResult.getIndex()
                    , elasticsearchPreProcessResult.getIndexType()));
        }
        return CLAZZ_INDEX.get(clazz);
    }

    /**
     * @return
     * @describe 写入文件到本地
     * @params
     * @author Jia wei Wu
     * @date 2020/12/6 5:36 下午
     **/
    @SneakyThrows
    public <T> void writeFileToLocal(List<T> source) {
        BufferedWriter bufferedWriter = FileUtil.createFile(
                upsertConfig.getCacheFileAddress(),
                LocalDate.now().toString(),
                ES_UPSERT_FILE_SUFFIX,
                String.valueOf(System.currentTimeMillis()));
        for (T t : source) {
            bufferedWriter.write(getBulkIndexDoc(t.getClass()));
            bufferedWriter.newLine();
            ElasticsearchProcessResult elasticsearchProcessResult = dataPack(t);
            String s = JSONObject.toJSONString(elasticsearchProcessResult);
            bufferedWriter.write(s);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    /**
     * description Elasticsearch 预处理结果
     *
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:58
     */
    @Data
    public class ElasticsearchPreProcessResult implements ProcessResult {

        /**
         * Elasticsearch 索引
         */
        private String index;

        /**
         * 索引类型
         */
        private String indexType;
    }

    /**
     * description Elasticsearch 处理结果
     *
     * @author Jia wei Wu
     * @date 2020/10/22 下午3:16
     */
    @Data
    public class ElasticsearchProcessResult<K, V> extends HashMap<K, V> implements Map<K, V>, ProcessResult {

    }

}
