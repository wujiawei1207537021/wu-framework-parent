package com.wu.framework.easy.excel.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.*;


/**
 * description 导入Excel数据， MVC 自动转换成List 类型的数据
 *
 * @author Jia wei Wu
 * @date 2021/4/23 下午12:06
 *
 * <p>
 * @ApiOperation(tags = "导入注解测试", value = "导入Excel并转换成对象")
 * @PostMapping("/imp/bean") public String importBean(@RequestExcelBody("file") List<UseExcel> userLogList) {
 * return userLogList.toString();
 * }
 * </p>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestExcelBody {

    /**
     * Alias for {@link #name}.
     */
    @AliasFor("name")
    String value() default "";

    /**
     * The name of the part in the {@code "multipart/form-data"} request to bind to.
     *
     * @since 4.2
     */
    @AliasFor("value")
    String name() default "";

    /**
     * Whether the part is required.
     * <p>Defaults to {@code true}, leading to an exception being thrown
     * if the part is missing in the request. Switch this to
     * {@code false} if you prefer a {@code null} value if the part is
     * not present in the request.
     */
    boolean required() default true;

    String defaultValue() default ValueConstants.DEFAULT_NONE;
}
