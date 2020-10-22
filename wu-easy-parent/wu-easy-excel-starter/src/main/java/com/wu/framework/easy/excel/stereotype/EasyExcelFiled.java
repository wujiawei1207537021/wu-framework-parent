package com.wu.framework.easy.excel.stereotype;


import java.lang.annotation.*;

/**
 * description 导出字段注解
 *
 * @author 吴佳伟
 * @date 2020/10/5 下午7:08
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyExcelFiled {

    /**
     * 列名
     *
     * @return String
     */
    String name();
}
