package com.wu.framework.log.platform.provider;

import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.log.platform.domain.RequestLog;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * describe: 请求日志
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/30 18:31
 */
@Tag(name = "请求日志")
@EasyController("/run/log")
public class RequestLogProvider extends AbstractLazyCrudProvider<RequestLog,RequestLog, Long> {


}
