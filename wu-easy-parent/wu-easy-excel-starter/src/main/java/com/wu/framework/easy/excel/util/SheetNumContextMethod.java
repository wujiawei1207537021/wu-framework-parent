package com.wu.framework.easy.excel.util;

import java.util.ArrayList;
import java.util.List;

/**
 * description 工作簿显示的文本内容
 *
 * @author Jia wei Wu
 * @date 2020/10/6 下午2:36
 */
public class SheetNumContextMethod implements ISheetShowContextMethod {


    @Override
    public List<String> sheetContext(long size, long limit) {
        List<String> stringList = new ArrayList<>();
        long page = (size + limit - 1) / limit;
        for (int i = 0; i < page - 1; i++) {
            stringList.add((i * limit) + "～" + (i * limit + limit));
        }
        stringList.add(((page - 1) * limit) + "～" + ((page - 1) * limit + size % limit));
        return stringList;
    }
}
