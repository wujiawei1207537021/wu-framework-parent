package com.wu.framework.easy.temple.domain.bo;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.temple.domain.SmartExcel;
import com.wu.framework.easy.temple.domain.UseExcel;
import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.inner.layer.data.SmartMark;
import lombok.Data;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 更复杂对象数据
 * @date : 2020/12/14 9:40 下午
 */
@Data
@EasySmart(dataDrillDown = true)
public class MoreExtractBo {


    @SmartMark
    private ExtractBo extractBo;

    @SmartMark
    private UserLog userLog;

    @SmartMark
    private UseExcel useExcel;

    @SmartMark
    private List<UserLog> userLogList;

    private SmartExcel smartExcel;
}
