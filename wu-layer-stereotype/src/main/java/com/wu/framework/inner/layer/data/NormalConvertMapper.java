package com.wu.framework.inner.layer.data;

import com.wu.framework.inner.layer.stereotype.LayerMethod;

import java.lang.annotation.*;

/**
 * description 转换控制器 基于Java JDK 数据类型转换 (不包含 Page)
 *
 * @author Jia wei Wu
 * @date 2020/8/25 下午2:47
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerMethod
public @interface NormalConvertMapper {
}
