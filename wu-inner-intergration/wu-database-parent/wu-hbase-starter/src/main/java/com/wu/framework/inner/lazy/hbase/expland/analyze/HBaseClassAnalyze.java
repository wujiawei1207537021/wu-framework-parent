package com.wu.framework.inner.lazy.hbase.expland.analyze;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.inner.layer.stereotype.analyze.LayerAnalyzeAdapter;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2021/4/7 下午5:12
 */
public class HBaseClassAnalyze extends LayerAnalyzeAdapter<EasySmart> {


    @Override
    public EasySmart analyze(Class clazz) {
        return AnnotationUtils.findAnnotation(clazz,EasySmart.class);
    }
}
