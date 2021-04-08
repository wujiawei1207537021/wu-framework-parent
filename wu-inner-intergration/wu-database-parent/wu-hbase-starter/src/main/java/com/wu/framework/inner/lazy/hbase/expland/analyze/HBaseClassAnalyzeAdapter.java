package com.wu.framework.inner.lazy.hbase.expland.analyze;

import com.wu.framework.inner.layer.stereotype.Layer;
import com.wu.framework.inner.lazy.hbase.expland.persistence.stereotype.HBaseTable;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2021/4/7 下午5:12
 */
public interface HBaseClassAnalyzeAdapter<P> extends Layer<P> {

    default HBaseTable analyzeClass(Class clazz) {
        return AnnotationUtils.findAnnotation(clazz, HBaseTable.class);
    }

}
