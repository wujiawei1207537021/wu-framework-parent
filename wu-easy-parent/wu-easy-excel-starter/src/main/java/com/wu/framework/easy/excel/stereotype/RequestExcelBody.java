package com.wu.framework.easy.excel.stereotype;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestExcelBody {
}
