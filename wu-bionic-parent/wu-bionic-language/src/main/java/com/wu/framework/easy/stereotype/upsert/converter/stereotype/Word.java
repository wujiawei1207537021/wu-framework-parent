package com.wu.framework.easy.stereotype.upsert.converter.stereotype;


import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
public class Word {
    private byte[] voice;
    private String strokes;
    private String pinyin;
    private String radicals;
    private Boolean isDeleted;
    private Timestamp createTime;
    private String more;
    private String oldword;
    private Timestamp updateTime;
    private BigInteger id;
    private String explanation;
    private String word;
}