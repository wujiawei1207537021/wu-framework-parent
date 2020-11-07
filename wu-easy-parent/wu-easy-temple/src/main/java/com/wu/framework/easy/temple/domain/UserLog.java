package com.wu.framework.easy.temple.domain;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:33
 */
@Data
public class UserLog {

    @EasyTableField(name = "user_id")
    private Integer userId;

    @EasyExcelFiled(name = "当前时间")
    @EasyTableField(name = "`current_time`")
    private LocalDateTime currentTime;

    @EasyExcelFiled(name = "内容")
    @EasyTableField(name = "`content`")
    private String content;
}
