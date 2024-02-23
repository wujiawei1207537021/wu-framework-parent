package com.wu.framework.inner.lazy.persistence.conf;

import com.wu.framework.inner.layer.data.LayerData;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.stereotype.LazyTable;

import java.util.List;
import java.util.Map;


public interface LazyTableEndpoint {


    String AUTHOR = "wujiawei";
    /**
     * 打印建表语句
     *
     * @param clazz 数据传输类
     */
//    SQL 描述
    String SQL_DESC =
            "-- ——————————————————————————\n" +
                    "-- create table %s  %s  \n" +  // 表名  表描述
                    "-- add by  %s  %s  \n" +   // 作者  日期
                    "-- ——————————————————————————" + "\n";

    /**
     * 获取表引擎
     *
     * @return
     */
    LazyTable.Engine getEngine();

    /**
     * 表名
     *
     * @return
     */
    String getTableName();

    /**
     * 完善表
     *
     * @return
     */
    boolean isPerfectTable();

    /**
     * 数据下钻
     * the field use Annotation with {@link LayerData#dataDrillDown()}
     */
    boolean isDataDrillDown();

    /**
     * 表注释
     *
     * @return String
     */
    String getComment();


    /**
     * 数据库名 schema
     *
     * @return String
     */
    String getSchema();

    /**
     * 数据库名 schema
     */
    void setSchema(String schema);

    /**
     * 是否存在
     */
    boolean isExist();

    /**
     * 字段
     */
    List<LazyTableFieldEndpoint> getFieldEndpoints();

    /**
     * 智能填充bean属性
     * 针对数据源 如mysql查询结果、http请求结果中包含的数据字段不再当前对象中
     */
    boolean isSmartFillField();

    /**
     * 类名
     */
    String getClassName();

    /**
     * 数据为空的时候使用字段默认值
     */
//     String getFieldDefaultValue();

    /**
     * 类
     */
    Class<?> getClazz();

    /**
     * 转换指定类型的枚举 DefaultIEnum 不转换  转换失败默认是-1
     */
    Map<String, Map<String, String>> getIEnumList();

    /**
     * 获取表全名
     * <p>
     * 如：mysql.user
     * </p>
     */
    String getFullTableName();

    /**
     * 创建建表语句
     *
     * @return
     */
    String creatTableSQL();

    /**
     * 创建修改表语句
     *
     * @param currentColumnNameList 当前数据库字段
     * @param dropColumn            是否删除列
     * @return
     */
    String alterTableSQL(List<LazyTableFieldEndpoint> currentColumnNameList, boolean dropColumn);

    /**
     * 创建每个字段修改的语句
     *
     * @param currentColumnNameList
     * @param dropColumn
     * @return
     */
    List<String> alterTableColumnSQL(List<LazyTableFieldEndpoint> currentColumnNameList, boolean dropColumn);

    /**
     * describe 获取指定注解的字段
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/1 6:40 下午
     **/
    List<LazyTableFieldEndpoint> specifiedFieldAnnotation(LayerField.LayerFieldType id);
}
