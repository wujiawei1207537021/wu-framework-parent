package com.wu.framework.inner.layer.data.encryption;

import com.wu.framework.inner.layer.data.encryption.aop.NormalConvertEncryptionDecryptionPointcutAdvisor;

import java.lang.annotation.*;

/**
 * description 数据加密
 *
 * @author Jia wei Wu
 * @date 2020/8/25 下午2:47
 * @see EncryptionField 字段加密
 * @see NormalConvertEncryptionDecryptionPointcutAdvisor
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NormalConvertEncryptionDecryptionMapper {
}
