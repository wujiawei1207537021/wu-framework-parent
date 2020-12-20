package com.wu.framework.easy.temple.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:33
 */
@Data
@EasySmart(perfectTable = true)
@TableName("user_log")
public class UserLog extends Model<UserLog> {

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
