package com.wu.framework.easy.excel.stereotype;


import java.lang.annotation.*;

/**
 * description 导出字段是个对象
 *
 * @author Jia wei Wu
 * @date 2020/10/5 下午7:08
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EasyExcelFiled(name = "default")
public @interface EasyExcelBean {

}
