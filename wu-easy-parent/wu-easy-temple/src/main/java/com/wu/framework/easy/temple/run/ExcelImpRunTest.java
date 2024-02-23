package com.wu.framework.easy.temple.run;

import com.wu.framework.easy.excel.util.FastExcelImp;
import com.wu.framework.easy.temple.domain.excel.UseUserExcel;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.QuickEasyUpsert;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 测试Excel导出
 * @date : 2020/10/18 下午9:32
 */
@EasyController
public class ExcelImpRunTest {

    /**
     * 导入 Excel 查询重复数据
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/imp1")
    public String import1(@RequestBody MultipartFile multipartFile) {
        List<UseUserExcel> userLogList = FastExcelImp.parseExcel(multipartFile, UseUserExcel.class);
        return userLogList.toString();
    }

    @QuickEasyUpsert(type = EasyUpsertType.MySQL)
    @PostMapping("/easy/hash-map")
    public List<EasyHashMap> saveCity(MultipartFile file) {
        List<EasyHashMap> easyHashMapList = FastExcelImp.parseExcel(file, EasyHashMap.class);
        if (ObjectUtils.isEmpty(easyHashMapList)) {
            return easyHashMapList;
        }
        EasyHashMap firstEasyHashMap = new EasyHashMap(file.getName());
        firstEasyHashMap.putAll(easyHashMapList.get(0));
        easyHashMapList.add(0, firstEasyHashMap);
        return easyHashMapList;
    }

}
