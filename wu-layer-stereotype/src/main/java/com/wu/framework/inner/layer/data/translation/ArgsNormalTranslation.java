package com.wu.framework.inner.layer.data.translation;

import com.wu.framework.inner.layer.data.dictionary.ConvertField;
import com.wu.framework.inner.layer.data.encryption.EncryptionField;
import com.wu.framework.inner.layer.data.encryption.NormalConvertEncryptionDecryptionMapper;
import com.wu.framework.inner.layer.data.translation.aop.NormalTranslationPointcutAdvisor;


import java.lang.annotation.*;

/**
 * description 转换控制器 基于Java JDK 数据类型转换 (不包含 Page)
 *
 * @author Jia wei Wu
 * @date 2020/8/25 下午2:47
 * @see ConvertField 字段转换注解
 * <p>
 * 数据加解密
 * @see NormalConvertEncryptionDecryptionMapper
 * @see EncryptionField 字段加密
 * @see NormalTranslationPointcutAdvisor 转译解析器
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ArgsNormalTranslation {
}
