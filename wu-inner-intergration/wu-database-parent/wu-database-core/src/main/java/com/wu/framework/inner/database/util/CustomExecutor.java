package com.wu.framework.inner.database.util;

import com.wu.framework.inner.database.converter.Parser;
import com.wu.framework.inner.database.domain.CustomRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomExecutor {

    private final static Log log = LogFactory.getLog(CustomExecutor.class);


    public static <E> List<E> selectList(Method method, Object[] args, CustomRepository customRepository, Connection connection) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        //1.取出mapper中的数据
        // 6.1 添加参数
        String queryString = customRepository.getQueryString();//select * from user
        queryString = Parser.parse2(method, args, customRepository);
        String resultType = customRepository.getResultType();//com.itheima.domain.User
        try {

            Class domainClass = Class.forName(resultType);
            //2.获取PreparedStatement对象
            pstm = connection.prepareStatement(queryString);
            //3.执行SQL语句，获取结果集
            rs = pstm.executeQuery();
            //4.封装结果集
            // 基本数据类型
            if (domainClass.isPrimitive()) {
                System.out.println("基本数据类型： " + domainClass.getName() + "  " + domainClass.getName());
            }
            List<E> list = new ArrayList<E>();//定义返回值
            if ("java.util.Map".equals(domainClass.getName())) {
                list = dataTypeConversion(domainClass, rs);
            } else {
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
                        String columnName = rsmd.getColumnLabel(i);
                        //根据得到列名，获取每列的值
                        Object columnValue = rs.getObject(columnName);
                        //给obj赋值：使用Java内省机制（借助PropertyDescriptor实现属性的封装）
                        try {
                            PropertyDescriptor pd = new PropertyDescriptor(columnName, domainClass);//要求：实体类的属性和数据库表的列名保持一种
                            //获取它的写入方法
                            Method writeMethod = pd.getWriteMethod();
                            //把获取的列的值，给对象赋值
                            writeMethod.invoke(obj, columnValue);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    //把赋好值的对象加入到集合中
                    list.add(obj);
                }
            }
            return list;
        } catch (Exception e) {
            log.error("executor sql:" + queryString);
            throw new RuntimeException(e);
        } finally {
            release(pstm, rs);
        }
    }

    /**
     * 查询出所有的总数
     *
     * @param customRepository
     * @param connection
     * @return
     */
    public static long selectCount(Method method, Object[] args, CustomRepository customRepository, Connection connection) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        //1.取出mapper中的数据
        String queryString = customRepository.getQueryString();//select * from user
        queryString = Parser.countSQL(method, args, queryString);
        queryString = Parser.countSQL(method, args, customRepository);
        try {
            //2.获取PreparedStatement对象
            pstm = connection.prepareStatement(queryString);
            //3.执行SQL语句，获取结果集
            rs = pstm.executeQuery();
            //4.封装结果集
            while (rs.next()) {
                return rs.getObject(1, long.class);
            }
            return 0;
        } catch (Exception e) {
            log.error("executor sql:" + queryString);
            throw new RuntimeException(e);
        } finally {
            release(pstm, rs);
        }
    }

    private static void release(PreparedStatement pstm, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 基本数据类型转换
     */
    public static <E> List<E> dataTypeConversion(Class domainClass, ResultSet rs) throws SQLException {
        List<E> list = new ArrayList<E>();//定义返回值
        while (rs.next()) {
            Map map = new HashMap<>();
            //取出结果集的元信息：ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            //取出总列数
            int columnCount = rsmd.getColumnCount();
            //遍历总列数
            for (int i = 1; i <= columnCount; i++) {
                //获取每列的名称，列名的序号是从1开始的
                String columnName = rsmd.getColumnName(i);
                //根据得到列名，获取每列的值
                Object columnValue = rs.getObject(columnName);
                map.put(columnName, columnValue);
            }
            list.add((E) map);
        }
        return list;
    }


}
