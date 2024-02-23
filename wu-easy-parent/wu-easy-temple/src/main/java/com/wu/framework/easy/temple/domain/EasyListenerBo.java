package com.wu.framework.easy.temple.domain;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.EasySmartField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/10/22 9:02 下午
 */
@Data
@Accessors(chain = true)
@EasySmart(perfectTable = true, tableName = "easy_listener")
public class EasyListenerBo {
    @EasySmartField(name = "user_id")
    private Integer userId;

    @EasyExcelFiled(name = "当前时间")
    @EasySmartField(name = "`current_time`")
    private LocalDateTime currentTime;

    @EasyExcelFiled(name = "内容")
    @EasySmartField(name = "`content`")
    private String content;

    @EasyExcelFiled(name = "是否成功")
    @EasySmartField(name = "is_succeed")
    private boolean isSucceed;

    private String type;


}
