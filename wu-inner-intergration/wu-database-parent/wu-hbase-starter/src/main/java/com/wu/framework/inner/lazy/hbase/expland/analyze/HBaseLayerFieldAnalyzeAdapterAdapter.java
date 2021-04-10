package com.wu.framework.inner.lazy.hbase.expland.analyze;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.stereotype.analyze.AnalyzeField;
import com.wu.framework.inner.lazy.hbase.expland.persistence.stereotype.HBaseTableUnique;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/4/8 7:07 下午
 */
public interface HBaseLayerFieldAnalyzeAdapterAdapter<P> extends HBaseClassAnalyzeAdapter<P> {


    default List<AnalyzeField> analyzeField(Class clazz) {
        List<AnalyzeField> analyzeFieldList = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            HBaseTableUnique layerField = AnnotationUtils.findAnnotation(field, HBaseTableUnique.class);
            if (ObjectUtils.isEmpty(layerField) || layerField.exist()) {
                AnalyzeField analyzeField = AnalyzeField.builder().fieldName(field.getName()).convertedFieldName(CamelAndUnderLineConverter.humpToLine2(field.getName())).build();
                if (!ObjectUtils.isEmpty(layerField)) {
                    analyzeField.setFieldIndexType(layerField.indexType());
                }
                analyzeFieldList.add(analyzeField);
            }
        }
        return analyzeFieldList;
    }
}
