package com.wu.framework.easy.excel.service.style;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * @author : 吴佳伟
 * @version : 1.0
 * @describe: 颜色类型
 * @date : 2020/12/9 8:57 下午
 */
public interface Style {


    /**
     * 标题样式
     * @param styleParam
     * @return
     */
    HSSFCellStyle titleStyle(StyleParam styleParam);

    /**
     * 列样式
     * @param styleParam
     * @return
     */
    HSSFCellStyle columnStyle(StyleParam styleParam);


}
