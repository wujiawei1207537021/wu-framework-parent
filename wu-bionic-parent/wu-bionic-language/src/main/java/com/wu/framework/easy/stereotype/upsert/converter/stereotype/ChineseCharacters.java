package com.wu.framework.easy.stereotype.upsert.converter.stereotype;


import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * 汉字
 */
@Accessors(chain = true)
@Data
@LazyTable(tableName = "chinese_characters")
public class ChineseCharacters {

    @LazyTableField(comment = "拼音")
    private String pinYin;

    @LazyTableField(comment = "语音数据", columnType = "blob")
    private byte[] voice;

    @LazyTableField(comment = "笔划", columnType = "text")
    private String strokes;

    @LazyTableField(comment = "部首", columnType = "text")
    private String radicals;

    private Integer isDeleted;

    private LocalDateTime createTime;

    @LazyTableField(comment = "更多", columnType = "text")
    private String more;

    @LazyTableField(comment = "繁体字", columnType = "text")
    private String oldWord;

    private LocalDateTime updateTime;

    private BigInteger id;

    @LazyTableField(comment = "例子", columnType = "text")
    private String explanation;

    @LazyTableFieldUnique(comment = "汉字")
    private String word;
}