package com.wu.framework.easy.temple.domain;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2020/12/14 下午8:22
 */
@Data
public class UserBo {

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
