package com.wu.framework.easy.excel.util;

import java.util.List;

public interface ISheetShowContextMethod {

    /**
     * description
     *
     * @param size  原数组长度
     * @param limit 限制长度
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/6 下午3:19
     */
    List<String> sheetContext(long size, long limit);
}
