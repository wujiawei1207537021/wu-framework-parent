package com.wu.framework.inner.layer.data.encryption;

import com.wu.framework.inner.layer.stereotype.LayerField;

import java.lang.annotation.*;

/**
 * description 加密对象
 *
 * @author 吴佳伟
 * @date 2023/08/29 12:59
 * @see EncryptionField
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerField
public @interface EncryptionFieldBean {


}
