package com.wu.framework.inner.layer.data.encryption.convert;

import com.wu.framework.inner.layer.data.encryption.EncryptionField;
import com.wu.framework.inner.layer.data.encryption.EncryptionFieldBean;
import com.wu.framework.inner.layer.data.encryption.adapter.EncryptionDecryptionAdapter;
import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionEnum;
import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionTypeEnum;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * description 默认实现加解密类
 *
 * @author 吴佳伟
 * @date 2023/08/30 14:00
 */
public class DefaultConvertEncryptionDecryption extends AbstractConvertEncryptionDecryption {
    /**
     * 加解密适配器
     */
    private final EncryptionDecryptionAdapter encryptionDecryptionAdapter;

    public DefaultConvertEncryptionDecryption(EncryptionDecryptionAdapter encryptionDecryptionAdapter) {
        this.encryptionDecryptionAdapter = encryptionDecryptionAdapter;
    }

    /**
     * 转译
     *
     * @param encryptionDecryptionTypeEnum
     * @param object                       原始对象
     */
    @Override
    public void transformationField(EncryptionDecryptionTypeEnum encryptionDecryptionTypeEnum, Object object) {
        Class<?> clazz = object.getClass();
        Field[] clazzDeclaredFields = clazz.getDeclaredFields();
        for (Field field : clazzDeclaredFields) {
            try {
                if (!field.canAccess(object)) {
                    field.setAccessible(true);
                }
                Object fieldVal = field.get(object);
                EncryptionFieldBean convertFieldBean = field.getAnnotation(EncryptionFieldBean.class);
                if (!ObjectUtils.isEmpty(convertFieldBean)) {
                    transformationObjects(encryptionDecryptionTypeEnum, fieldVal);
                    continue;
                }

                // 加密处理
                EncryptionField encryptionField = AnnotatedElementUtils.findMergedAnnotation(field, EncryptionField.class);
                if (!ObjectUtils.isEmpty(encryptionField) && !ObjectUtils.isEmpty(fieldVal)) {
                    if (null != encryptionDecryptionAdapter) {
                        // 加密
                        EncryptionDecryptionEnum encryptionDecryptionEnum = encryptionField.encryptionDecryptionType();
                        Supplier supplier = null;
                        if (EncryptionDecryptionEnum.AES.equals(encryptionDecryptionEnum)) {
                            supplier = new Supplier() {
                                /**
                                 * Gets a result.
                                 *
                                 * @return a result
                                 */
                                @Override
                                public Object get() {
                                    return encryptionField.aesKey();
                                }
                            };
                        } else if (EncryptionDecryptionEnum.RSA.equals(encryptionDecryptionEnum)) {
                            supplier = new Supplier() {
                                /**
                                 * Gets a result.
                                 *
                                 * @return a result
                                 */
                                @Override
                                public Object get() {
                                    Map<String, Object> keyMap = new HashMap<>(2);
                                    keyMap.put("PUBLIC_KEY", encryptionField.rsaPublicKey());
                                    keyMap.put("PRIVATE_KEY", encryptionField.rsaPrivateKey());
                                    return keyMap;
                                }
                            };
                        }
                        if (EncryptionDecryptionTypeEnum.ENCRYPTION.equals(encryptionDecryptionTypeEnum)) {
                            Object encryption = encryptionDecryptionAdapter.encryption(encryptionDecryptionEnum, fieldVal, supplier);
                            field.set(object, encryption.toString());
                        } else {
                            Object encryption = encryptionDecryptionAdapter.decryption(encryptionDecryptionEnum, fieldVal, supplier);
                            field.set(object, encryption.toString());
                        }


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 支持
     *
     * @param source 原始数据
     * @return 布尔类型
     */
    @Override
    public boolean support(Object source) {
        return !Objects.isNull(source);
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
