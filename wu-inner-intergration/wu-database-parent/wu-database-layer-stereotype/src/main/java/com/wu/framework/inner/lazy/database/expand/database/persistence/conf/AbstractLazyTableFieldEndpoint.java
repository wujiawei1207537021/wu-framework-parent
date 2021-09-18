package com.wu.framework.inner.lazy.database.expand.database.persistence.conf;

import com.wu.framework.inner.layer.data.IEnum;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTableField;
import lombok.Data;


@Data
public abstract class AbstractLazyTableFieldEndpoint implements LazyTableFieldEndpoint {


    private String name;

    /**
     * 字段类型
     */
    // 数据库字段 当前支持 varchar number  int 默认是 varchar  优先级高于 class
    private String type;

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
     * 字段版本
     */
    private int version;

    private int scale;

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
     * 数据格式
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

    private LayerField.LayerFieldType fieldIndexType = LayerField.LayerFieldType.FILE_TYPE;

    @Override
    public String getType() {
        return null==type? LazyTableField.FileType.getTypeByClass(clazz):type;
    }
}
