package com.wu.framework.inner.lazy.database.expand.database.persistence.factory;

import com.wu.framework.inner.lazy.persistence.conf.AbstractLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableStructure;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LazyTableStructure 对象转换工厂
 */
public class LazyTableStructureConverterFactory {


    /**
     * 将对象转换成 Persistence 对象
     *
     * @param source 原始数据 数组、对象、Map
     * @return
     */
    public static LazyTableStructure dataStructure(Object source) {
        Assert.notNull(source, "source 数据不能为空");
        final Class<?> sourceClass = source.getClass();
        if (sourceClass.isArray()) {
            return LazyTableStructureConverterFactory.wrapperIterable((Iterable) source);
        } else if (EasyHashMap.class.isAssignableFrom(sourceClass)) {
            return LazyTableStructureConverterFactory.wrapperEasyHashMap((EasyHashMap) source);
        } else {
            return LazyTableStructureConverterFactory.wrapperBean(source);
        }
    }

    /**
     * 解析 转换数组
     *
     * @param iterable
     * @return
     */
    public static LazyTableStructure wrapperIterable(Iterable iterable) {
        final Object next = iterable.iterator().next();
        final Class<?> nextClass = next.getClass();
        List<List<Object>> fieldValueList = new ArrayList<>();
        // 首次计算
        boolean hasCalculation = false;
        // 数据schema
        LazyTableEndpoint lazyTableEndpoint = null;
        if (EasyHashMap.class.isAssignableFrom(nextClass)) {
            for (Object o : iterable) {

                final LazyTableStructure lazyTableStructure = wrapperEasyHashMap((EasyHashMap) o);
                if (!hasCalculation) {
                    hasCalculation = true;
                    lazyTableEndpoint = lazyTableStructure.schema();
                }
                fieldValueList.add(lazyTableStructure.payload().get(0));
            }
        } else if (Object.class.isAssignableFrom(nextClass)) {
            for (Object o : iterable) {
                final LazyTableStructure lazyTableStructure = wrapperBean(o);
                if (!hasCalculation) {
                    hasCalculation = true;
                    lazyTableEndpoint = lazyTableStructure.schema();
                }
                fieldValueList.add(lazyTableStructure.payload().get(0));
            }
        }

        return new LazyTableStructure(lazyTableEndpoint, fieldValueList);
    }


    /**
     * 解析 EasyHashMap 为 LazyTableStructure数据结构
     *
     * @param easyHashMap
     * @return
     */
    public static LazyTableStructure wrapperEasyHashMap(EasyHashMap easyHashMap) {
        final ClassLazyTableEndpoint classLazyTableEndpoint = easyHashMap.toEasyTableAnnotation();

        final List<Object> fieldValueList = new ArrayList<>(easyHashMap.values());
        return new LazyTableStructure(classLazyTableEndpoint, List.of(fieldValueList));
    }

    /**
     * 解析对象
     *
     * @param o
     * @return
     */
    public static LazyTableStructure wrapperBean(Object o) {
        Assert.notNull(o, "source 数据不能为空");
        Class<?> oClass = o.getClass();
        ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(oClass);
        List<Object> fieldValueList = classLazyTableEndpoint.getFieldEndpoints().stream().
                filter(AbstractLazyTableFieldEndpoint::isExist).
                filter(fieldLazyTableFieldEndpoint -> !LazyTableFieldId.IdType.AUTOMATIC_ID.equals(fieldLazyTableFieldEndpoint.getIdType())).
                map(fieldLazyTableFieldEndpoint -> {
                    try {
                        Field field = fieldLazyTableFieldEndpoint.getField();
                        field.setAccessible(true);
                        return field.get(o);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());

        return new LazyTableStructure(classLazyTableEndpoint, List.of(fieldValueList));
    }

}
