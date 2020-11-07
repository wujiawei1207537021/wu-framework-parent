package com.wu.framework.easy.temple.run;

import com.wu.framework.easy.excel.util.FastExcelImp;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.easy.temple.domain.UseExcel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 测试Excel导出
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
        List<UseExcel> userLogList = FastExcelImp.praseExcel(multipartFile, UseExcel.class);
        return userLogList.toString();
    }

}
