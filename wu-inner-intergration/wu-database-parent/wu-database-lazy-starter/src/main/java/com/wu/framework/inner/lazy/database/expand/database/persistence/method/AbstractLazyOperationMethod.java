package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.easy.stereotype.upsert.converter.CamelAndUnderLineConverter;
import com.wu.framework.easy.stereotype.upsert.enums.JavaBasicType;
import com.wu.framework.inner.lazy.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.lazy.database.domain.ConvertedField;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
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
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            preparedStatement.close();
        }
    }

    public <E> List<E> resultSetConverter(ResultSet rs, String resultType) {
        try {
            Class domainClass = Class.forName(resultType);
            //封装结果集
            List list = new ArrayList();//定义返回值

            // Map 数值
            if (Map.class.isAssignableFrom(domainClass)) {
                while (rs.next()) {
                    Map hashMap = (Map) domainClass.newInstance();
                    //取出结果集的元信息：ResultSetMetaData
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    //取出总列数
                    int columnCount = resultSetMetaData.getColumnCount();
                    //遍历总列数
                    for (int i = 1; i <= columnCount; i++) {
                        //获取每列的名称，列名的序号是从1开始的
                        String columnName = resultSetMetaData.getColumnName(i);
                        //根据得到列名，获取每列的值
                        Object columnValue = rs.getObject(i);
                        hashMap.put(columnName, columnValue);
                    }
                    //把赋好值的对象加入到集合中
                    list.add(hashMap);
                }
            } else if (isWrapClass(domainClass)) {
                //基本数据类型
                while (rs.next()) {
                    Object convertBasicTypeBean = JavaBasicType.convertBasicTypeBean(domainClass, rs.getObject(1));
                    list.add(convertBasicTypeBean);
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
                        columnName = CamelAndUnderLineConverter.lineToHump(columnName);
                        String columnLabel = rsmd.getColumnLabel(i);
                        String fieldName = convertedFieldMap.getOrDefault(columnName, convertedFieldMap.get(columnLabel));
                        if (ObjectUtils.isEmpty(fieldName)) {
                            continue;
                        }
                        //根据得到列名，获取每列的值
//                        Object columnValue = rs.getObject(columnName);
                        Field field = domainClass.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        rs.getObject(i, field.getType());
//                        columnValue = convertToTheCorrespondingType(columnValue, field.getType());
                        Object columnValue = rs.getObject(i, field.getType());
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
     * 转换成对应的对象
     *
     * @param obj
     * @param clazz
     * @return
     */
    protected <T> T convertToTheCorrespondingObject(Object obj, Class<T> clazz) {
        return null;
    }


    /**
     * 是否基本数据类型
     *
     * @param
     * @return
     * @author 吴佳伟
     * @date 2021/1/3 12:54 下午
     **/
    public static boolean isWrapClass(Class clazz) {
        try {
            if(String.class.isAssignableFrom(clazz))return true;
            return ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

}
