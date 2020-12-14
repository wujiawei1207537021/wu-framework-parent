package com.wu.framework.easy.stereotype.upsert.entity.stereotye;

import com.wu.framework.easy.stereotype.upsert.entity.ConvertedField;
import lombok.Data;

import java.util.List;

/**
 * description 表注解属性
 *
 * @author Jia wei Wu
 * @date 2020/9/3 上午9:52
 */
@Data
public class EasyTableAnnotation {

    /**
     * 类名
     */
    private String className;

    /**
     * 表名称
     */
    private String name;

    /**
     * 描述信息
     */
    private String comment;

    /**
     * kafka  schema 名称
     *
     * @return
     */
    private String kafkaSchemaName;

    /**
     * kafka 主题 为空使用类名
     *
     * @return
     */
    private String kafkaTopicName;
    /**
     * kafka code 编码
     */
    private String kafkaCode;

    /**
     * 字段
     */
    private List<ConvertedField> convertedFieldList;
}
