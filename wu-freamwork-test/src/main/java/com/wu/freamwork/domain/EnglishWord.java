package com.wu.freamwork.domain;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EnglishWord {

    private String word;
    private String voice;
    private String translation;
    private String id;
    private String isDeleted;
    private String createTime;
    private String updateTime;
}
