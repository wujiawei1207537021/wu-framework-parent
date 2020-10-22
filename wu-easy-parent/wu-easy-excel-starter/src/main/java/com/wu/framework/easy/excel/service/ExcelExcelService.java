package com.wu.framework.easy.excel.service;

import com.wu.framework.easy.excel.stereotype.EasyExcel;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface ExcelExcelService {

    @SneakyThrows
    static byte[] exportExcel(EasyExcel easyExcel, Collection collection) {
        if (easyExcel.isComplicated()) {
            return ComplexExcelExportService.exportExcel(easyExcel, collection);
        } else {
            return NormalExcelExportService.exportExcel(easyExcel, collection);
        }
    }

    static List<List> splitList(List list, int len) {
        if (list == null || list.isEmpty() || len < 1) {
            return Collections.emptyList();
        }
        List<List> result = new ArrayList<>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List subList = list.subList(i * len, (Math.min((i + 1) * len, size)));
            result.add(subList);
        }
        return result;
    }
}
