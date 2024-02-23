package com.wu.framework.inner.lazy.persistence.conf;

import com.wu.framework.inner.layer.data.IEnum;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;

public interface LazyTableFieldEndpoint {


    // Java 字段名称
    String getName();

    /**
     * 数据库 列名称
     *
     * @return
     */
    String getColumnName();

    /**
     * 列类型
     *
     * @return
     */
    String getColumnType();


    /**
     * 获取字段指定的类型
     *
     * @return
     */
    Class getClazz();

    /**
     * 字段描述
     */
    String getComment();

    /**
     * 是否存在
     */
    boolean isExist();


    /**
     * 字段版本
     */
    int getVersion();

    int getScale();

    /**
     * 参数
     */
    String getParameters();

    /**
     * 可选的
     */
    boolean isOptional();

    /**
     * 数据为空的时候使用字段默认值
     */
    String getFieldDefaultValue();

    /**
     * 转换指定类型的枚举 DefaultIEnum 不转换  转换失败默认是-1
     */
    Class<? extends IEnum> getIEnum();

    /**
     * 数据格式
     */
    String getDataType();

    /**
     * 转换内容分隔符
     *
     * @return
     * @see IEnum#getConvertContentSeparator()
     */
    @Deprecated
    String[] getConvertContentSeparator();

    /**
     * 通过获取字典类型转换
     *
     * @return
     */
    String getConvert();

    /**
     * 数据库字段索引类型
     */
    LayerField.LayerFieldType getFieldIndexType();

    /**
     * ID 类型 字段为主键id是有值
     */
    LazyTableFieldId.IdType getIdType();

    /**
     * @return
     * @params 创建SQL column
     * "`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
     * @author Jiawei Wu
     * @date 2020/12/31 9:19 下午
     **/
    default String createColumn() {
        return NormalUsedString.BACKTICK + getColumnName() + NormalUsedString.BACKTICK + " " + getColumnType() + " COMMENT '" + getComment() + "', \n";
    }
}
