package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.easy.stereotype.upsert.converter.CamelAndUnderLineConverter;
import com.wu.framework.inner.lazy.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.lazy.database.domain.ConvertedField;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description 抽象自定义数据库持久层操作方法
 *
 * @author Jia wei Wu
 * @date 2020/7/28 上午8:56
 */
public abstract class AbstractLazyOperationMethod implements LazyOperationMethod {


    /**
     * description 执行SQL 语句
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     **/
    @Override
    public Object execute(PreparedStatement preparedStatement, String resultType) throws SQLException {
        try {
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            preparedStatement.close();
        }
        return false;
    }

    public <E> List<E> resultSetConverter(ResultSet rs, String resultType) {
        try {
            Class domainClass = Class.forName(resultType);
            //封装结果集
            List list = new ArrayList();//定义返回值

            // Map 数值
            if (Map.class.isAssignableFrom(domainClass)) {
                while (rs.next()) {
                    Map hashMap = new LinkedHashMap();
                    //取出结果集的元信息：ResultSetMetaData
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    //取出总列数
                    int columnCount = resultSetMetaData.getColumnCount();
                    //遍历总列数
                    for (int i = 1; i <= columnCount; i++) {
                        //获取每列的名称，列名的序号是从1开始的
                        String columnName = resultSetMetaData.getColumnName(i);
                        //根据得到列名，获取每列的值
                        Object columnValue = rs.getObject(columnName);
                        hashMap.put(columnName, columnValue);
                    }
                    //把赋好值的对象加入到集合中
                    list.add(hashMap);
                }
            } else {
                List<ConvertedField> convertedFieldList = PreparedStatementSQLConverter.fieldNamesOnAnnotation(domainClass);
                Map<String, String> convertedFieldMap = convertedFieldList.stream().collect(Collectors.toMap(ConvertedField::getFieldName, ConvertedField::getFieldName));
                while (rs.next()) {
                    //实例化要封装的实体类对象
                    E obj = (E) domainClass.newInstance();
                    //取出结果集的元信息：ResultSetMetaData
                    ResultSetMetaData rsmd = rs.getMetaData();
                    //取出总列数
                    int columnCount = rsmd.getColumnCount();
                    //遍历总列数
                    for (int i = 1; i <= columnCount; i++) {
                        //获取每列的名称，列名的序号是从1开始的
                        String columnName = rsmd.getColumnName(i).toLowerCase();
                        columnName=CamelAndUnderLineConverter.lineToHump(columnName);
                         String columnLabel = rsmd.getColumnLabel(i);
                        String fieldName = convertedFieldMap.getOrDefault(columnName,convertedFieldMap.get(columnLabel));
                        if (ObjectUtils.isEmpty(fieldName)) {
                            continue;
                        }
                        //根据得到列名，获取每列的值
//                        Object columnValue = rs.getObject(columnName);
                        Field field = domainClass.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        rs.getObject(i,field.getType());
//                        columnValue = convertToTheCorrespondingType(columnValue, field.getType());
                        Object columnValue=rs.getObject(i,field.getType());
                        field.set(obj, columnValue);
                    }
                    //把赋好值的对象加入到集合中
                    list.add(obj);
                }
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将object 转换成相应的类型
     *
     * @param obj
     * @param clazz
     * @return
     */
    protected Object convertToTheCorrespondingType(Object obj, Class clazz) {
        obj = obj == null ? "" : obj;
        if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            return Integer.valueOf(obj.toString());
        } else if (clazz.equals(String.class)) {
            return obj.toString();
        } else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
            return Long.valueOf(obj.toString());
        } else if (clazz.equals(Short.class) || clazz.equals(short.class)) {
            return Short.valueOf(obj.toString());
        } else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
            return Double.valueOf(obj.toString());
        } else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
            return Float.valueOf(obj.toString());
        } else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            return Boolean.valueOf(obj.toString());
        } else if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
            return Byte.valueOf(obj.toString());
        } else if (clazz.equals(Character.class) || clazz.equals(char.class)) {
            return obj;
        }
        return obj;
    }

    /**
     * 转换成对应的对象
     *
     * @param obj
     * @param clazz
     * @return
     */
    protected <T> T convertToTheCorrespondingObject(Object obj, Class<T> clazz) {
        return null;
    }


}
