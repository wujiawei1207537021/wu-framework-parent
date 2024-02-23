package com.wu.framework.inner.sys.adapter.stereotype;

import java.lang.annotation.*;

/**
 * description 对象中的对象的字典项目 优先级高于ConvertConvertFile
 *
 * @Author Jia wei Wu
 * @Date 2020/6/18 2:23 下午
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ConvertFieldBean {
}
