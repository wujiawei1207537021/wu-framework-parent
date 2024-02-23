package com.wu.inner.sys.adapter.stereotype;

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
public @interface NormalConvertMapper {
}
