package com.wu.kafka.config;

import com.supconit.its.transform.config.DataProcessConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@Configurable
@ComponentScan(basePackages = {"com.supconit.its"}, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {DataProcessConfig.class}))
public class WuTransformConfig {
}
