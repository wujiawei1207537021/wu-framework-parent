package com.wu.framework.easy.temple.domain.bo;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.SmartMark;
import com.wu.framework.easy.temple.domain.UseExcel;
import com.wu.framework.easy.temple.domain.UserLog;
import lombok.Data;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/12/14 9:40 下午
 */
@Data
@EasySmart
public class MoreExtractBo {


    @SmartMark(drillDown = true)
    private ExtractBo extractBo;

    @SmartMark(drillDown = true)
    private UserLog userLog;

    @SmartMark(drillDown = true)
    private UseExcel useExcel;
}
