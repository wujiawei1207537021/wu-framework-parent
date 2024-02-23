package com.wu.framework.inner.layer.stereotype.analyze;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.stereotype.LayerField;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * description layer 解析适配器
 *
 * @author Jia wei Wu
 * @date 2021/4/1 下午3:32
 */
public class LayerFieldAnalyze implements LayerAnalyze {


    /**
     * description 是否支持
     *
     * @param analyzeParameter@return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/26 4:31 下午
     */
    @Override
    public boolean supportParameter(AnalyzeParameter analyzeParameter) {
        return true;
    }

    @Override
    public List<AnalyzeField> analyze(AnalyzeParameter analyzeParameter) {
        List<AnalyzeField> analyzeFieldList = new ArrayList<>();
        for (Field field : analyzeParameter.getClazz().getDeclaredFields()) {
            LayerField layerField = AnnotatedElementUtils.findMergedAnnotation(field, LayerField.class);
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
