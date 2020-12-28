package com.wu.framework.inner.lazy.database.converter;

import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import com.wu.framework.easy.stereotype.upsert.converter.CamelAndUnderLineConverter;
import com.wu.framework.easy.stereotype.upsert.converter.EasyAnnotationConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import lombok.SneakyThrows;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :  Jia wei Wu
 * @version 1.0
 * @describe : 持久化转换工具
 * @date : 2020/11/21 下午11:04
 */
public class PersistenceConverter {


    /**
     * 去除null的字段
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/21 下午8:55
     **/
    @SneakyThrows
    public static Persistence activeInsertPrepared(Object object) {
        // 数据库列
        List<String> columnList = new ArrayList<>();
        // 列对应值
        List<String> columnValueList = new ArrayList<>();
        // 表名
        String tableName = EasyAnnotationConverter.getTableName(object.getClass());
        for (Field declaredField : object.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            Object o = declaredField.get(object);
            if (o == null) {
                continue;
            }
            EasySmartField tableField = AnnotatedElementUtils.findMergedAnnotation(declaredField, EasySmartField.class);
            if (tableField == null || !tableField.exist()) {
                continue;
            }
            String column = ObjectUtils.isEmpty(tableField.name()) ?
                    CamelAndUnderLineConverter.humpToLine2(declaredField.getName()) : tableField.name();
            columnList.add(column);
            columnValueList.add(o.toString());
        }
        Persistence persistence = new Persistence();
        persistence.setExecutionEnum(Persistence.ExecutionEnum.INSERT);
        persistence.setTableName(tableName);
        persistence.setColumnList(columnList);
        persistence.setCondition(String.join(",", columnValueList));
        return persistence;
    }

}
