package com.wu.framework.easy.stereotype.upsert.process;

import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import com.wu.framework.easy.stereotype.upsert.converter.CamelAndUnderLineConverter;
import lombok.Data;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * description Elasticsearch 数据预处理
 *
 * @author Jia wei Wu
 * @date 2020/10/22 下午2:26
 */
public class ElasticsearchEasyDataProcess implements DataProcess {
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
        String index = clazz.getSimpleName();
        EasyTable table = AnnotationUtils.getAnnotation(clazz, EasyTable.class);
        if (null != table) {
            String prefix = table.indexPrefix();
            String format = table.indexFormat();
            String suffix = table.indexSuffix();
            if (ObjectUtils.isEmpty(format)) {
                index = prefix + suffix;
            } else {
                index = prefix + LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)) + suffix;
            }
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
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:23
     */
    @Override
    public ElasticsearchProcessResult dataPack(Object sourceData) {
        Assert.notNull(sourceData, "sourceData must not be null.");
        Field[] declaredFields = sourceData.getClass().getDeclaredFields();
        ElasticsearchProcessResult<String,Object> source = new ElasticsearchProcessResult<>();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            EasyTableField tableField = AnnotationUtils.getAnnotation(field, EasyTableField.class);
            String fieldName = CamelAndUnderLineConverter.humpToLine2(field.getName());
            Object fieldValue = new Object();
            try {
                fieldValue = field.get(sourceData);
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
     * description Elasticsearch 预处理结果
     *
     * @author Jia wei Wu
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
     * @author Jia wei Wu
     * @date 2020/10/22 下午3:16
     */
    @Data
    public class ElasticsearchProcessResult<K,V> extends HashMap<K,V>
            implements Map<K,V>, ProcessResult {


    }

}
