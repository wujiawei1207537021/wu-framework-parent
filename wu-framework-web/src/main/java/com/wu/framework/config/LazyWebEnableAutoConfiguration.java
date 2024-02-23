package com.wu.framework.config;

import com.wu.framework.filter.LazyRequestFilter;
import com.wu.framework.info.AddressInfo;
import com.wu.framework.support.RequestBodyParamMethodArgumentResolver;
import org.springframework.context.annotation.Import;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/10/5 18:56
 */
//@Import({AddressInfo.class, RequestBodyParamMethodArgumentResolver.class, LazyRequestFilter.class}) TODO 二进制获取失败
@Import({AddressInfo.class, RequestBodyParamMethodArgumentResolver.class, LazyRequestFilter.class})
public class LazyWebEnableAutoConfiguration {

}
