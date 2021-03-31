package com.wu.framework.easy.excel.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

class FastExcelImpTest {

    public static void main(String[] args) throws FileNotFoundException {

        String path = "/Users/wujiawei/Desktop/省厅新老接口合并后重复车牌.xlsx";
        path = "/Users/wujiawei/Downloads/AMap_adcode_citycode.xlsx/AMap_adcode_citycode.xlsx";
        final List<Map> mapList = FastExcelImp.parseExcel(new FileInputStream(new File(path)), Map.class);
        System.out.println(mapList);
    }
}