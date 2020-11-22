package com.wu.freamwork.controller;

import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.excel.util.FastExcelImp;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.inner.database.expand.database.persistence.LayerOperation;
import com.wu.freamwork.domain.Veh;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2020/10/19 上午8:59
 */
@Slf4j
@EasyController("/excel")
public class ExcelController {

    private final LayerOperation layerOperation;

    public ExcelController(LayerOperation layerOperation) {
        this.layerOperation = layerOperation;
    }

    /**
     * description 检查省厅数据车辆是否存在
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/19 上午8:59
     */
    @EasyExcel(fileName = "车辆缺失数据")
    @PostMapping("/check/veh")
    public List<Veh> checkVeh(@RequestBody MultipartFile multipartFile) {
        List<Veh> impVehList = FastExcelImp.praseExcel(multipartFile, Veh.class);
        System.out.println("车辆导入数据:" + impVehList);
        log.info("解析车辆{}条数", impVehList.size());
        // 查询省厅所有的车辆
        List<Veh> vehAllList = layerOperation.executeSQL("SELECT * from sys_eqp_capcon_bas_veh WHERE data_source='省平台' and plate_num is not null ", Veh.class);
        log.info("总数据{}条数", vehAllList.size());
        Map vehMap = vehAllList.stream().collect(Collectors.toMap(veh -> veh.getPlateNum() + veh.getPlateColor(), veh -> veh));
        // 过滤解析的车辆不再所有车辆中的数据
        List<Veh> lostVeh = impVehList.stream().filter(veh -> {
            String key = veh.getPlateNum() + veh.getPlateColor();
            Object o = vehMap.get(key);
            return !vehMap.containsKey(key);
        }).collect(Collectors.toList());
        log.info("缺失数据{}条数", lostVeh.size());
        String fileName = multipartFile.getName();
        System.out.println("车辆丢失数据:" + lostVeh);
        return lostVeh;
    }

}
