package com.wu.framework.inner.lazy.database.expand.database.persistence.conf;

import com.wu.framework.inner.layer.data.LayerData;

import java.util.List;


public interface LazyTableEndpoint {

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

    List<LazyTableFieldEndpoint> getFieldEndpoints();


    /**
     * 智能填充bean属性
     * 针对数据源 如mysql查询结果、http请求结果中包含的数据字段不再当前对象中
     */
    boolean isSmartFillField();
}
