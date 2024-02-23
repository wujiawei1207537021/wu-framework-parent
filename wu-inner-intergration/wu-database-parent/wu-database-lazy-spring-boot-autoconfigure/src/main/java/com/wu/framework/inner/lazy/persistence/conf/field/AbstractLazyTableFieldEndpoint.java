package com.wu.framework.inner.lazy.persistence.conf.field;

import com.wu.framework.inner.layer.data.IEnum;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazySQLUtil;
import com.wu.framework.inner.lazy.persistence.util.LazyTableFieldUtil;
import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;


@Data
public abstract class AbstractLazyTableFieldEndpoint implements LazyTableFieldEndpoint {


    /**
     * 字段不是空
     *
     * @return
     */
    boolean notNull;
    /**
     * 字段默认数值
     */
    String defaultValue;
    /**
     * 字段是否为主键
     *
     * @return
     */
    boolean key;
    /**
     * 序号 数值越大越靠前
     */
    int serialNumber = 0;
    /**
     * Java 字段名称
     */
    private String name;
    /**
     * 数据库列名称
     */
    private String columnName;
    /**
     * 数据库字段别名
     */
    private String alias;
    /**
     * 数据库列类型
     */
    private String columnType;
    /**
     * 对应Java 字段类型class
     */
    private Class clazz;
    /**
     * 字段描述
     */
    private String comment;
    /**
     * 是否存在
     */
    private boolean exist;
    /**
     * upsert 时候字段策略，对应生成upsert 的sql
     */
    private LazyFieldStrategy upsertStrategy = LazyFieldStrategy.NO_VERIFY;

    /**
     * update 时候字段策略，对应生成upsert 的sql
     */
    private LazyFieldStrategy updateStrategy = LazyFieldStrategy.NO_VERIFY;
    /**
     * extra 额外的数据 如 auto_increment、DEFAULT_GENERATED on update CURRENT_TIMESTAMP
     *
     * @return
     */
    private String extra = "";
    /**
     * 特权 如 select,insert,update,references
     *
     * @return
     */
    private LazyTableField.Privilege[] privileges = {LazyTableField.Privilege.select, LazyTableField.Privilege.insert, LazyTableField.Privilege.update, LazyTableField.Privilege.references};
    /**
     * 字段对应索引
     */
    private LazyTableIndexEndpoint[] lazyTableIndexEndpoints = new LazyTableIndexEndpoint[0];
    /**
     * 字段版本
     */
    private int version;
    private long scale;
    /**
     * 参数
     */
    private String parameters;
    /**
     * 可选的
     */
    private boolean optional;
    /**
     * 数据为空的时候使用字段默认值
     */
    private String fieldDefaultValue;
    /**
     * 转换指定类型的枚举 DefaultIEnum 不转换  转换失败默认是-1
     */
    private Class<? extends IEnum> iEnum;
    /**
     * 数据格式 varchar、int、text
     */
    private String dataType;
    /**
     * 转换内容分隔符
     *
     * @return
     * @see IEnum#getConvertContentSeparator()
     */
    @Deprecated
    private String[] convertContentSeparator;
    /**
     * 通过获取字典类型转换
     *
     * @return
     */
    private String convert;
    /**
     * 通过 bean 产生的 AbstractLazyTableFieldEndpoint 字段数据
     */
    private Object fieldValue;
    /**
     * 字段
     */
    private Field field;
    private LazyTableFieldId.IdType idType;

    public static AbstractLazyTableFieldEndpoint getInstance() {
        return new FieldLazyTableFieldEndpoint();
    }

    public String getAlias() {
        return LazyTableFieldUtil.cleanSpecialColumn(alias);
    }


    @Override
    public String getColumnName() {
        return LazyTableFieldUtil.cleanSpecialColumn(columnName);
    }

    @Override
    public String getColumnType() {
        return ObjectUtils.isEmpty(columnType) ? LazyTableField.FieldType.getTypeByClass(clazz) : columnType;
    }

    /**
     * 获取 sql 字段对应的value 数据取自 getFieldValue
     *
     * @return sql value
     */
    @Override
    public Object getSqlValue() {
        return LazySQLUtil.valueToSqlValue(getFieldValue());
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be {@code true}, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be {@code true}, this is not an absolute requirement.
     * <p>
     * By convention, the returned object should be obtained by calling
     * {@code super.clone}.  If a class and all of its superclasses (except
     * {@code Object}) obey this convention, it will be the case that
     * {@code x.clone().getClass() == x.getClass()}.
     * <p>
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by {@code super.clone} before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by {@code super.clone}
     * need to be modified.
     * <p>
     * The method {@code clone} for class {@code Object} performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface {@code Cloneable}, then a
     * {@code CloneNotSupportedException} is thrown. Note that all arrays
     * are considered to implement the interface {@code Cloneable} and that
     * the return type of the {@code clone} method of an array type {@code T[]}
     * is {@code T[]} where T is any reference or primitive type.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     * <p>
     * The class {@code Object} does not itself implement the interface
     * {@code Cloneable}, so calling the {@code clone} method on an object
     * whose class is {@code Object} will result in throwing an
     * exception at run time.
     *
     * @return a clone of this instance.
     * @throws CloneNotSupportedException if the object's class does not
     *                                    support the {@code Cloneable} interface. Subclasses
     *                                    that override the {@code clone} method can also
     *                                    throw this exception to indicate that an instance cannot
     *                                    be cloned.
     * @see Cloneable
     */
    @Override
    public LazyTableFieldEndpoint clone() throws CloneNotSupportedException {
        return (LazyTableFieldEndpoint) super.clone();
    }

    @Override
    public String toString() {
        return "[" +
                "name=" + name +
                ",columnName=" + columnName +
                ",fieldValue=" + fieldValue +
                ']';
    }
}
