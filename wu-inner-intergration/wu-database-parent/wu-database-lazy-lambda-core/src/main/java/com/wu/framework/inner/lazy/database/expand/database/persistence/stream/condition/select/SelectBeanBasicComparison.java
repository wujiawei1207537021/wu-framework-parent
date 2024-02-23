package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.select;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.LambdaBasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.Condition;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.stereotype.field.LazyFieldCondition;
import com.wu.framework.inner.lazy.stereotype.field.LazyFieldSortOrder;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 对象参数
 * @date : 2021/8/23 8:36 下午
 */
public class SelectBeanBasicComparison<T> extends LambdaBasicComparison<T> {

    /**
     * 查询参数实体
     */
    private final T bean;

    public SelectBeanBasicComparison(T bean) {
        this.bean = bean;
    }

    /**
     * @return describe 获取条件集合
     * @author Jia wei Wu
     * @date 2021/8/21 7:57 下午
     **/
    @Override
    public SqlPart getSqlPart() {
        this.sqlPart = super.getSqlPart();
        if (null != bean) {
            // TODO 获取主表
            Class<?> classT = bean.getClass();
            String tableName = LazyTableUtil.getTableName(classT);
            Map<String, String> masterFieldMap = Arrays.stream(classT.getDeclaredFields()).map(Field::getName).collect(Collectors.toMap(s -> s, Function.identity()));
            Class<?> beanClass = bean.getClass();
            for (Field declaredField : beanClass.getDeclaredFields()) {
                String fieldName = declaredField.getName();
                if (masterFieldMap.containsKey(fieldName)) {
                    try {
                        declaredField.setAccessible(true);
                        Object value = declaredField.get(bean);
                        String columnName = CamelAndUnderLineConverter.humpToLine2(fieldName);

                        LazyFieldSortOrder lazyFieldSortOrder = AnnotatedElementUtils.findMergedAnnotation(declaredField, LazyFieldSortOrder.class);

                        if (null != lazyFieldSortOrder) {
                            // 排序
                            LazyFieldSortOrder.SortOrder sortOrder = lazyFieldSortOrder.sortOrder();
                            if (!LazyFieldSortOrder.SortOrder.NONE.equals(sortOrder)) {
                                // 添加排序规则
                                Condition condition = new Condition();
                                condition.setRowName(columnName);
                                condition.setType(sortOrder.name());
                                sqlPart.orderBy(condition);
                            }
                        }

                        LazyFieldCondition lazyFieldCondition = AnnotatedElementUtils.findMergedAnnotation(declaredField, LazyFieldCondition.class);

                        if (null == lazyFieldCondition) {
                            if (!ObjectUtils.isEmpty(value)) {
                                sqlPart.put(tableName + NormalUsedString.DOT + columnName,
                                        NormalUsedString.EQUALS,
                                        value);
                            }
                        } else {
                            if (lazyFieldCondition.ignore()) {
                                continue;
                            }
                            // 忽略空值
                            if (lazyFieldCondition.ignoreEmpty() && null == value) {
                                continue;
                            }
                            sqlPart.put(columnName,
                                    lazyFieldCondition.condition(),
                                    value);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }


        return sqlPart;
    }

    /**
     * 获取T 的class
     *
     * @return
     */
    @Override
    public Class<T> getClassT() {
        return (Class<T>) bean.getClass();
    }


}
