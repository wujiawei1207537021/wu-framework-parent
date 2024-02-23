package com.wu.framework.inner.lazy.hbase.expland.analyze;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.stereotype.analyze.AnalyzeField;
import com.wu.framework.inner.layer.stereotype.analyze.AnalyzeParameter;
import com.wu.framework.inner.layer.stereotype.analyze.LayerAnalyze;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : Hbase 字段解析
 * @date : 2021/4/8 7:07 下午
 */
public class HBaseFieldAnalyze implements LayerAnalyze {


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

    /**
     * description  supportParameter 结果返回为true 时解析数据为指定类型的数据
     * 解析字段
     *
     * @param analyzeParameter@return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/27 3:05 下午
     */
    @Override
    public Object analyze(AnalyzeParameter analyzeParameter) {
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
