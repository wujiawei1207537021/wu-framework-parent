package com.wu.framework.inner.lazy.database.expand.database.persistence.util;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTable;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/8/8 2:35 下午
 */
public class LazyTableUtil {

    public static String getTableName(Class clazz) {
        LazyTable lazyTable = AnnotatedElementUtils.findMergedAnnotation(clazz, LazyTable.class);
        if (null == lazyTable) {
            String className = clazz.getSimpleName();
            return CamelAndUnderLineConverter.humpToLine2(className);
        }
        return lazyTable.tableName();
    }
}
