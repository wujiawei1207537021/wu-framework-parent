package com.wu.framework.inner.lazy.hbase.expland.analyze;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.stereotype.Layer;
import com.wu.framework.inner.lazy.hbase.expland.persistence.stereotype.HBaseTable;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;

/**
 * description 分析class中的HBaseTable注解
 *
 * @author Jia wei Wu
 * @date 2021/4/7 下午5:12
 */
public interface HBaseClassAnalyzeAdapter<P> extends Layer<P> {

    default HBaseTable analyzeClass(Class clazz) {
         HBaseTable mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(clazz, HBaseTable.class);
        if (ObjectUtils.isEmpty(mergedAnnotation)) {
            mergedAnnotation =new HBaseTable(){

                /**
                 * Returns the annotation type of this annotation.
                 *
                 * @return the annotation type of this annotation
                 */
                @Override
                public Class<? extends Annotation> annotationType() {
                    return HBaseTable.class;
                }

                @Override
                public String namespace() {
                    return "default";
                }

                @Override
                public String tableName() {
                    return CamelAndUnderLineConverter.humpToLine2(clazz.getName());
                }

                @Override
                public String columnFamily() {
                    return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName().replaceAll("\\.","_"));
                }

                @Override
                public boolean perfectTable() {
                    return true;
                }
            };
        }
        return mergedAnnotation;
    }

}
