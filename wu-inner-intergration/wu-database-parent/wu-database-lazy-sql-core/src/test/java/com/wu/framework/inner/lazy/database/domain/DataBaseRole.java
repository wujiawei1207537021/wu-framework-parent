package com.wu.framework.inner.lazy.database.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTableFieldCreateTime;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUpdateTime;
import lombok.Data;

import java.util.Date;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/12/28 21:06
 */
@Data
public class DataBaseRole {

    private Long id;

    private String name;


    /**
     * 操作时间
     */
    @LazyTableFieldUpdateTime
    private Date updateTime;

    /**
     * 操作时间
     */
    @LazyTableFieldCreateTime
    private Date createTime;
}
