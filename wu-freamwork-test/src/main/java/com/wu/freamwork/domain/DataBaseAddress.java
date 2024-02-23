package com.wu.freamwork.domain;

import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/5/15 2:45 下午
 */
@Data
public class DataBaseAddress implements Serializable {


    private Long id;

    private String name;

    private double latitude;

    private double longitude;

    /**
     * 操作时间
     */
    @LazyTableField(comment = "操作时间", notNull = true, defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", upsertStrategy = LazyFieldStrategy.NEVER)
    private Date updateTime;

    /**
     * 操作时间
     */
    @LazyTableField(comment = "操作时间", notNull = true, defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", upsertStrategy = LazyFieldStrategy.NEVER)
    private Date createTime;
}
