package com.wu.framework.easy.temple.domain.excel;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import lombok.Data;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/07/02 20:00
 */
@Data
public class UseAccountExcel {

    @EasyExcelFiled(name = "原生注解-账户")
    private String account;

    @EasyExcelFiled(name = "原生注解-用户")
    private String userName;

    @EasyExcelFiled(name = "原生注解-账户编号")
    private String no;
}
