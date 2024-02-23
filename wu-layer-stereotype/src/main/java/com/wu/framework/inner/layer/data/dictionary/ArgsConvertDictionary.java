package com.wu.framework.inner.layer.data.dictionary;

import com.wu.framework.inner.layer.data.dictionary.aop.NormalConvertMapperPointcutAdvisor;

import java.lang.annotation.*;

/**
 * 方法参数转译
 *
 * @see ConvertField
 * @see NormalConvertMapper
 * @see NormalConvertMapperPointcutAdvisor
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ArgsConvertDictionary {


}
