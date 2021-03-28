package com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
 
import java.util.Map;
 
/**
 * Hbase配置类
 * @author sixmonth
 * @Date 2019年5月13日
 *
 */
@ConfigurationProperties(prefix = HBaseConfigProperties.CONF_PREFIX)
public class HBaseConfigProperties {
 
	public static final String CONF_PREFIX = "hbase.conf";
 
	private Map<String,String> confMaps;
 
	public Map<String, String> getConfMaps() {
		return confMaps;
	}

	public void setConfMaps(Map<String, String> confMaps) {
		this.confMaps = confMaps;
	}
}