//package com.wu.framework.inner.lazy.database.converter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
//import lombok.Data;
//
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author : Jia wei Wu
// * @version 1.0
// * @describe : 解析ResultSet
// * @date : 2020/7/8 下午9:21
// */
//public class ResultSetConverter {
//
//    private static ObjectMapper objectMapper = new ObjectMapper();
//
//    /**
//     * ResultSet 转换成map
//     *
//     * @param resultSet
//     * @param whetherHump
//     * @return
//     * @author Jia wei Wu
//     * @date 2020/7/8 下午9:30
//     **/
//    public static List<Map> resultSet2Map(ResultSet resultSet, boolean whetherHump) {
//
//        List<Map> mapList = new ArrayList<>();
//        try {
//            //封装结果集
//            while (resultSet.next()) {
//                Map map = new HashMap();
//                //实例化要封装的实体类对象
//                //取出结果集的元信息：ResultSetMetaData
//                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//                //取出总列数
//                int columnCount = resultSetMetaData.getColumnCount();
//                //遍历总列数
//                for (int i = 1; i <= columnCount; i++) {
//                    //获取每列的名称，列名的序号是从1开始的
//                    //根据得到列名，获取每列的值
//                    String columnName = resultSetMetaData.getColumnName(i);
//                    if (whetherHump) {
//                        columnName = CamelAndUnderLineConverter.humpToLine2(columnName);
//                    }
//                    //根据得到列名，获取每列的值
//                    Object columnValue = resultSet.getObject(columnName);
//                    map.put(columnName, columnValue);
//                }
//                mapList.add(map);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return mapList;
//    }
//
//    /**
//     * ResultSet 转换成map 下划线转驼峰
//     *
//     * @param resultSet
//     * @return
//     * @author Jia wei Wu
//     * @date 2020/7/8 下午9:31
//     **/
//    public static List<Map> resultSet2Map(ResultSet resultSet) {
//        return resultSet2Map(resultSet, true);
//    }
//
//    public static <T> List<T> resultSet2Bean(ResultSet resultSet, Class<T> clazz) {
//        List<Map> mapList = resultSet2Map(resultSet, true);
//        List<T> ts = mapList.stream().map(map -> {
//            try {
//                String m = objectMapper.writeValueAsString(map);
//                return objectMapper.readValue(m, clazz);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }).collect(Collectors.toList());
//        return ts;
//    }
//
//
//    public static void main(String[] args) {
//        List<Map> mapList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Map map = new HashMap();
//            map.put("key", "val" + i);
//            mapList.add(map);
//        }
//        List<xxx> ts = mapList.stream().map(map -> {
//            try {
//                String m = objectMapper.writeValueAsString(map);
//                return objectMapper.readValue(m, xxx.class);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }).collect(Collectors.toList());
//        System.out.println(ts);
//    }
//
//    @Data
//    static class xxx {
//        private String key;
//    }
//}
