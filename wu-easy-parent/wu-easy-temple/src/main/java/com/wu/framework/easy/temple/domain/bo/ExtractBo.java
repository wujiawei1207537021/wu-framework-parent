package com.wu.framework.easy.temple.domain.bo;

import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.temple.domain.excel.UseUserExcel;
import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.inner.layer.data.SmartMark;
import lombok.Data;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/12/14 9:40 下午
 */
@Data
@EasySmart(perfectTable = true, dataDrillDown = true)
public class ExtractBo {

    @SmartMark
    private UserLog userLog;

    @SmartMark
    private UseUserExcel useUserExcel;
}