package com.wu.framework.inner.layer.data.encryption.convert;

import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionTypeEnum;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

/**
 * description 抽象类
 *
 * @author 吴佳伟
 * @date 2023/08/30 14:00
 */
public abstract class AbstractConvertEncryptionDecryption implements ConvertEncryptionDecryption {


    /**
     * description 模糊转换
     *
     * @param objects 原始对象
     * @author Jia wei Wu
     * @date 2020/6/17 3:40 下午
     */
    public void transformationObjects(EncryptionDecryptionTypeEnum encryptionDecryptionTypeEnum, Object... objects) {
        if (ObjectUtils.isEmpty(objects)) {
            return;
        }
        for (Object object : objects) {
            if (ObjectUtils.isEmpty(object)) {
                continue;
            }
            if (object instanceof Collection) {
                transformationCollection(encryptionDecryptionTypeEnum,(Collection<?>) object);
            } else {
                transformationField(encryptionDecryptionTypeEnum,object);
            }
        }
    }

    /**
     * 集合处理
     *
     * @param collection 集合对象
     */
    protected void transformationCollection(EncryptionDecryptionTypeEnum encryptionDecryptionTypeEnum, Collection<?> collection) {
        for (Object o : collection) {
            if (o instanceof Collection) {
                transformationCollection(encryptionDecryptionTypeEnum,(Collection<?>) o);
            } else {
                transformationField(encryptionDecryptionTypeEnum,o);
            }
        }
    }

    /**
     * 转译
     *
     * @param encryptionDecryptionTypeEnum
     * @param source                       原始对象
     */
    public abstract void transformationField(EncryptionDecryptionTypeEnum encryptionDecryptionTypeEnum, Object source);

    /**
     * 字段转译
     *
     * @param source                       原始对象
     * @param encryptionDecryptionTypeEnum
     */
    @Override
    public void transformation(Object source, EncryptionDecryptionTypeEnum encryptionDecryptionTypeEnum) {
        transformationObjects(encryptionDecryptionTypeEnum,source);
    }
}
