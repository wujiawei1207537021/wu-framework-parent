package com.wu.framework.log.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldCreateTime;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUpdateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * describe : 请求日志
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/12/29 21:31
 */
@Data
@LazyTable(comment = "请求日志")
public class RequestLog {
    /**
     * 请求ID
     */
    @Schema(description = "requestId", name = "请求ID")
    @LazyTableField(name = "requestId", comment = "请求ID")
    private String requestId;
    /**
     * 请求参数
     */
    @Schema(description = "请求参数", name = "requestParameters")
    @LazyTableField(name = "requestParameters", comment = "请求参数", columnType = "text")
    private String requestParameters;
    /**
     * 请求url
     */
    @Schema(description = "请求url", name = "requestUrl")
    @LazyTableField(name = "requestUrl", comment = "请求url")
    private String requestUrl;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法", name = "requestMethod")
    @LazyTableField(name = "requestMethod", comment = "请求方法")
    private String requestMethod;
    /**
     * 请求是否成功
     */
    @Schema(description = "请求是否成功", name = "success")
    @LazyTableField(name = "success", comment = "请求是否成功")
    private boolean success;
    /**
     * ip
     */
    @Schema(description = "ip", name = "ip")
    @LazyTableField(name = "ip", comment = "ip")
    private String ip;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableFieldCreateTime
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableFieldUpdateTime
    private LocalDateTime updateTime;
}
