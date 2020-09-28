package com.wu.framework.easy.temple.domain;

import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:33
 */
@Data
public class UserLog {
    private Integer userId;

    @EasyTableFile(name = "`current_time`")
    private LocalDateTime currentTime;
    @EasyTableFile(name = "`content`")
    private String content;
}
