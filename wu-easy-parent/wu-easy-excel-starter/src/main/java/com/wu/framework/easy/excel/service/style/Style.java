package com.wu.framework.easy.excel.service.style;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * 颜色类型
 * @date : 2020/12/9 8:57 下午
 */
public interface Style {


    /**
     * 标题样式
     *
     * @param styleParam
     * @return
     */
    @Deprecated
    CellStyle titleStyle(StyleParam styleParam);


    /**
     * 标题样式
     *
     * @param styleParam
     * @return
     */
    CellStyle titleStyle(DefaultStyleParam styleParam);

    /**
     * 列样式
     *
     * @param styleParam
     * @return
     */
    @Deprecated
    CellStyle columnStyle(StyleParam styleParam);

    /**
     * 创建 列样式
     *
     * @param styleParam 列样式参数
     * @return
     */
    CellStyle columnStyle(DefaultStyleParam styleParam);


}
