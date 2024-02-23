package com.wu.freamwork.domain;

import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import lombok.Data;

import java.util.Date;

/**
 * describe : 地图天气
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/1 22:00
 */
@Data
public class AMapWeather {


    private Long id;

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
