package com.wu.framework.easy.temple.controller;


import com.wu.framework.easy.excel.stereotype.RequestExcelBody;
import com.wu.framework.easy.excel.util.FastExcelImp;
import com.wu.framework.easy.temple.domain.excel.UseUserExcel;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/9/18 下午11:32
 */
@Tag(name = "导入注解测试")
@EasyController("/easy/excel/import")
public class EasyExcelImportController {


    @ApiOperation(tags = "导入注解测试", value = "导入Excel并转换成对象")
    @PostMapping("/imp1")
    public String import1(@RequestPart MultipartFile multipartFile) {
        List<UseUserExcel> userLogList = FastExcelImp.parseExcel(multipartFile, UseUserExcel.class);
        return userLogList.toString();
    }

    @ApiOperation(tags = "导入注解测试", value = "导入Excel并转换成对象")
    @PostMapping("/imp/bean")
    public String importBean(@RequestExcelBody("file") List<UseUserExcel> userLogList) {
        return userLogList.toString();
    }

    @ApiOperation(tags = "导入注解测试", value = "导入Excel并转换成EasyHashMap对象")
    @PostMapping("/imp1/hash-map")
    public List<EasyHashMap> implMap(@RequestPart MultipartFile file) {
        List<EasyHashMap> easyHashMapList = FastExcelImp.parseExcel(file, EasyHashMap.class);
        if (ObjectUtils.isEmpty(easyHashMapList)) {
            return easyHashMapList;
        }
        return easyHashMapList;
    }


    // TODO  导入Excel并自动转换成对象
    @ApiOperation(tags = "注解使用", value = "导入Excel并自动转换成对象-A")
    @PostMapping(value = "/imp/easyExport", headers = "content-columnType=multipart/form-data")
    public String easy(@RequestExcelBody("file") List<UseUserExcel> useUserExcelList) {
        return useUserExcelList.toString();
    }


}
