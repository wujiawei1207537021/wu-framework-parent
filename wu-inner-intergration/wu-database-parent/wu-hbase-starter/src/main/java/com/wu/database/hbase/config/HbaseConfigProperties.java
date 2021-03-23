package com.wu.database.hbase.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
 
import java.util.Map;
 
/**
 * Hbase配置类
 * @author sixmonth
 * @Date 2019年5月13日
 *
 */
@Configuration
@ConfigurationProperties(prefix = HbaseConfigProperties.CONF_PREFIX)
public class HbaseConfigProperties {
 
	public static final String CONF_PREFIX = "hbase.conf";
 
	private Map<String,String> confMaps;
 
	public Map<String, String> getConfMaps() {
		return confMaps;
	}

	public void setConfMaps(Map<String, String> confMaps) {
		this.confMaps = confMaps;
	}
}