package com.wu.framework.easy.temple.domain;

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
    private LocalDateTime currentTime;
    private String content;
}
