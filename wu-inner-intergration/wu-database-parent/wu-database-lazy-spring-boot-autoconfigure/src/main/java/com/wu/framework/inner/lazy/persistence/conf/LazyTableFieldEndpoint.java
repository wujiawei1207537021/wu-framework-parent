package com.wu.framework.inner.lazy.persistence.conf;

import com.wu.framework.inner.layer.data.IEnum;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

/**
 * 表字段信息
 */
public interface LazyTableFieldEndpoint extends Cloneable {


    // Java 字段名称
    String getName();

    /**
     * Java 字段名称
     */
    void setName(String name);

    /**
     * 数据库 列名称
     * <p>
     * 如：user_id
     * </p>
     *
     * @return
     */
    String getColumnName();

    void setColumnName(String columnName);

    /**
     * 数据库字段别名
     */
    String getAlias();

    /**
     * 设置别名
     *
     * @param alias
     */
    void setAlias(String alias);

    /**
     * 数据库字段类型 varchar int bigint
     *
     * @return String 返回字段类型
     */
    String getColumnType();

    void setColumnType(String columnType);

    /**
     * 获取字段指定的类型
     *
     * @return String 返回当前的class
     */
    Class getClazz();

    void setClazz(Class clazz);

    /**
     * 字段描述
     */
    String getComment();

    void setComment(String comment);

    /**
     * 不是空
     *
     * @return 布尔类型
     */
    boolean isNotNull();

    void setNotNull(boolean notNull);

    /**
     * 字段默认数值
     */
    String getDefaultValue();

    /**
     * 字段默认数值
     */
    void setDefaultValue(String defaultValue);

    /**
     * 是否为主键
     *
     * @return
     */
    boolean isKey();

    /**
     * 字段是否为主键
     *
     * @param key
     */
    void setKey(boolean key);

    /**
     * 字段对应索引
     *
     * @return
     */
    LazyTableIndexEndpoint[] getLazyTableIndexEndpoints();

    void setLazyTableIndexEndpoints(LazyTableIndexEndpoint[] lazyTableIndexEndpoints);

    /**
     * 是否存在
     */
    boolean isExist();

    /**
     * 设置是否存在
     *
     * @param exist 布尔类型
     */
    void setExist(boolean exist);


    /**
     * 设置当前字段upsert 策略
     *
     * @param lazyFieldStrategy 字段策略
     */
    void setUpsertStrategy(LazyFieldStrategy lazyFieldStrategy);

    /**
     * upsert 时候字段策略，对应生成upsert 的sql
     *
     * @return LazyFieldStrategy
     */
    LazyFieldStrategy getUpsertStrategy();

    /**
     * 设置当前字段update 策略
     *
     * @param lazyFieldStrategy 字段策略
     */
    void setUpdateStrategy(LazyFieldStrategy lazyFieldStrategy);

    /**
     * update  时候字段策略，对应生成upsert 的sql
     *
     * @return LazyFieldStrategy
     */
    LazyFieldStrategy getUpdateStrategy();

    /**
     * 字段版本
     */
    int getVersion();

    /**
     * 序号 数值越大越靠前
     */
    int getSerialNumber();

    void setSerialNumber(int serialNumber);

    long getScale();

    void setScale(long scale);

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

    void setDataType(String dataType);

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
     * ID 类型 字段为主键id是有值
     */
    LazyTableFieldId.IdType getIdType();

    /**
     * 设置当前字段ID类型 只有是主键的时候设置
     *
     * @param idType 主键类型
     */
    void setIdType(LazyTableFieldId.IdType idType);

    /**
     * 设置当前字段
     * @param field 当前字段
     */
    void setField(Field field);


    /**
     * @return 创建行sql
     * description ADD COLUMN `test_column12` varchar(255) NULL;
     * @author Jiawei Wu
     * @date 2020/12/31 9:19 下午
     **/
    default String createColumnSQL() {
        return NormalUsedString.ADD + NormalUsedString.SPACE + NormalUsedString.COLUMN + NormalUsedString.SPACE + columnSQL() + NormalUsedString.SPACE;
    }

    /**
     * @return 行sql
     * description 创建SQL column
     * "`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
     * @author Jiawei Wu
     * @date 2020/12/31 9:19 下午
     **/
    default String columnSQL() {
        return getColumnName() + NormalUsedString.SPACE +
                getColumnType() + NormalUsedString.SPACE +
                (
                        isNotNull() ?
                                NormalUsedString.SPACE + NormalUsedString.NOT + NormalUsedString.SPACE + NormalUsedString.NULL + NormalUsedString.SPACE :
                                NormalUsedString.SPACE
                ) +
                (
                        ObjectUtils.isEmpty(getDefaultValue()) ?
                                NormalUsedString.SPACE :
                                NormalUsedString.SPACE + NormalUsedString.DEFAULT + NormalUsedString.SPACE + getDefaultValue() + NormalUsedString.SPACE) +
                NormalUsedString.SPACE + getExtra() + NormalUsedString.SPACE +
                NormalUsedString.COMMENT + NormalUsedString.SPACE +
                NormalUsedString.SINGLE_QUOTE + getComment() + NormalUsedString.SINGLE_QUOTE;
    }

    /**
     * 删除 行sql DROP COLUMN xx.`request_methods`
     *
     * @return DROP COLUMN xx.`request_methods`
     */
    default String dropColumnSQL() {
        return NormalUsedString.DROP + NormalUsedString.SPACE + NormalUsedString.COLUMN + NormalUsedString.SPACE + getColumnName() + NormalUsedString.SPACE;
    }

    /**
     * MODIFY COLUMN `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除`;
     */
    default String modifyColumnSQL() {
        return NormalUsedString.MODIFY + NormalUsedString.SPACE + NormalUsedString.COLUMN + NormalUsedString.SPACE + columnSQL() + NormalUsedString.SPACE;
    }

    /**
     * 字段
     *
     * @return
     */
    Field getField();

    /**
     * 字段数据
     *
     * @return
     */
    Object getFieldValue();

    /**
     * 通过 bean 产生的 AbstractLazyTableFieldEndpoint 字段数据
     */
    void setFieldValue(Object fieldValue);

    /**
     * 获取 sql 字段对应的value 数据取自 getFieldValue
     *
     * @return sql value
     */
    Object getSqlValue();

    /**
     * 当前对象复制
     *
     * @return
     * @throws CloneNotSupportedException
     */
    LazyTableFieldEndpoint clone() throws CloneNotSupportedException;

    /**
     * extra 额外的数据 如 auto_increment、DEFAULT_GENERATED on update CURRENT_TIMESTAMP
     *
     * @return
     */
    String getExtra();

    /**
     * extra 额外的数据 如 auto_increment、DEFAULT_GENERATED on update CURRENT_TIMESTAMP
     *
     * @return
     */
    void setExtra(String extra);

    /**
     * 特权 如 select,insert,update,references
     *
     * @return
     */
    LazyTableField.Privilege[] getPrivileges();
}
