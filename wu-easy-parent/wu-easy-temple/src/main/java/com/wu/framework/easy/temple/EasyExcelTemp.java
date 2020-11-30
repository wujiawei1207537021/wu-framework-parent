package com.wu.framework.easy.temple;


import com.alibaba.fastjson.annotation.JSONField;
import com.wu.framework.easy.excel.stereotype.EasyExcel;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * description 导出字段是个对象 测试使用
 *
 * @author Jia wei Wu
 * @date 2020/10/5 下午7:08
 */
@Deprecated
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EasyExcel(useAnnotation = false, fieldColumnAnnotation = JSONField.class)
public @interface EasyExcelTemp {

    /**
     * 文件名称
     *
     * @return String
     */
    @AliasFor(annotation = EasyExcel.class, attribute = "fileName")
    String fileName();
}
