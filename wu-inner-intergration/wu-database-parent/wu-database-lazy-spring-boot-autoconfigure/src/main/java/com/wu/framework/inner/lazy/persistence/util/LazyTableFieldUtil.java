package com.wu.framework.inner.lazy.persistence.util;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.conf.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/8 2:35 下午
 */
public class LazyTableFieldUtil {

    public static final Logger log = LoggerFactory.getLogger(LazyTableFieldUtil.class);


    /**
     * 解析 class 中字段为数据schema
     *
     * @param clazz
     * @param tableFileIndexType
     * @param <T>
     * @return
     */
    public static <T> List<LazyTableFieldEndpoint> analyzeFieldOnAnnotation(Class<T> clazz, LayerField.LayerFieldType tableFileIndexType) {
        // 有主键
        boolean haveId = false;
        List<LazyTableFieldEndpoint> convertedFieldList = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            LazyTableField lazyTableField = AnnotatedElementUtils.findMergedAnnotation(declaredField, LazyTableField.class);
            String convertedFieldName = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            FieldLazyTableFieldEndpoint convertedField = new FieldLazyTableFieldEndpoint();
            if (!ObjectUtils.isEmpty(lazyTableField)) {
                convertedField.setExist(lazyTableField.exist());
                if (!lazyTableField.exist()) {
                    continue;
                }
                // 判断是否是我想要的类型
                if (!ObjectUtils.isEmpty(tableFileIndexType) && !tableFileIndexType.equals(lazyTableField.indexType())) {
                    continue;
                }
                if (!ObjectUtils.isEmpty(lazyTableField.value())) {
                    convertedFieldName = lazyTableField.value();
                }
                convertedField.setComment(lazyTableField.comment());
                convertedField.setFieldIndexType(lazyTableField.indexType());
                convertedField.setColumnType(lazyTableField.columnType());
            } else {
                convertedField.setExist(true);
            }
            // 判断并获取主键ID
            if (!haveId) {
                final LazyTableFieldId lazyTableFieldId = AnnotatedElementUtils.findMergedAnnotation(declaredField, LazyTableFieldId.class);
                if (!ObjectUtils.isEmpty(lazyTableFieldId)) {
                    haveId = true;
                    final LazyTableFieldId.IdType idType = lazyTableFieldId.idType();
                    convertedField.setIdType(idType);
                }
            }
            convertedField.setField(declaredField);
            convertedField.setColumnName(convertedFieldName);
            convertedField.setName(declaredField.getName());
            convertedField.setClazz(declaredField.getType());
            convertedFieldList.add(convertedField);
        }
        return convertedFieldList;
    }
}
