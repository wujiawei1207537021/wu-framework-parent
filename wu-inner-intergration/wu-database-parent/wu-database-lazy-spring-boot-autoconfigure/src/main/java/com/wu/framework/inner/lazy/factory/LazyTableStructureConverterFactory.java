package com.wu.framework.inner.lazy.factory;

import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableStructure;
import com.wu.framework.inner.lazy.persistence.conf.mysql.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
     * @return LazyTableStructure 惰性表结构
     */
    public static LazyTableStructure dataStructure(Object source) {
        Assert.notNull(source, "source 数据不能为空");
        final Class<?> sourceClass = source.getClass();
        if (Collection.class.isAssignableFrom(sourceClass)) {
            return LazyTableStructureConverterFactory.wrapperIterable((Iterable) source);
        } else if (EasyHashMap.class.isAssignableFrom(sourceClass)) {
            return LazyTableStructureConverterFactory.wrapperEasyHashMap((EasyHashMap) source);
        } else if (sourceClass.isArray()) {
            return LazyTableStructureConverterFactory.wrapperArray((Object[]) source);
        } else {
            return LazyTableStructureConverterFactory.wrapperBean(source);
        }
    }


    /**
     * 解析 转换数组
     *
     * @param iterable 原始数据迭代器
     * @return LazyTableStructure 惰性表结构
     */
    public static LazyTableStructure wrapperIterable(Iterable iterable) {
        final Object next = iterable.iterator().next();
        final Class<?> nextClass = next.getClass();
        List<List<LazyTableFieldEndpoint>> fieldValueList = new ArrayList<>();
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
     * 解析数组
     *
     * @param o 原始数据
     * @return LazyTableStructure 惰性表结构
     */
    private static LazyTableStructure wrapperArray(Object[] array) {
        final Object next = array[0];
        final Class<?> nextClass = next.getClass();
        List<List<LazyTableFieldEndpoint>> fieldValueList = new ArrayList<>();
        // 首次计算
        boolean hasCalculation = false;
        // 数据schema
        LazyTableEndpoint lazyTableEndpoint = null;
        if (EasyHashMap.class.isAssignableFrom(nextClass)) {
            for (Object o : array) {

                final LazyTableStructure lazyTableStructure = wrapperEasyHashMap((EasyHashMap) o);
                if (!hasCalculation) {
                    hasCalculation = true;
                    lazyTableEndpoint = lazyTableStructure.schema();
                }
                fieldValueList.add(lazyTableStructure.payload().get(0));
            }
        } else if (Object.class.isAssignableFrom(nextClass)) {
            for (Object o : array) {
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
     * @param easyHashMap 原始数据map
     * @return LazyTableStructure 惰性表结构
     */
    public static LazyTableStructure wrapperEasyHashMap(EasyHashMap easyHashMap) {
        final ClassLazyTableEndpoint lazyTableEndpoint = easyHashMap.toEasyTableAnnotation(false, true);
        List<LazyTableFieldEndpoint> fieldLazyTableFieldEndpointList = easyHashMap.getFieldLazyTableFieldEndpointList();
        return new LazyTableStructure(lazyTableEndpoint, Arrays.asList(fieldLazyTableFieldEndpointList));
    }

    /**
     * 解析对象
     *
     * @param o 原始数据
     * @return LazyTableStructure 惰性表结构
     */
    public static LazyTableStructure wrapperBean(Object o) {
        Assert.notNull(o, "source 数据不能为空");
        Class<?> oClass = o.getClass();
        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(oClass);
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(oClass);
        List<LazyTableFieldEndpoint> fieldValueList = lazyTableEndpoint.getFieldEndpoints()
                .stream()
                .filter(LazyTableFieldEndpoint::isExist)
//                filter(fieldLazyTableFieldEndpoint -> !LazyTableFieldId.IdType.AUTOMATIC_ID.equals(fieldLazyTableFieldEndpoint.getIdType())).
                .map(fieldLazyTableFieldEndpoint -> {
                    try {
                        LazyTableFieldEndpoint clone = fieldLazyTableFieldEndpoint.clone();
                        Field field = clone.getField();
                        field.setAccessible(true);
                        Object fieldValue = field.get(o);
                        clone.setFieldValue(fieldValue);
                        return clone;
                    } catch (IllegalAccessException | CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());

        return new LazyTableStructure(lazyTableEndpoint, Arrays.asList(fieldValueList));
    }


}
