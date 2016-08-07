package com.lucene.index.model;

import java.util.HashSet;

/**
 * 在工程中管理多个索引配置类
 * 
 * @author CJP
 * 
 */
public class IndexConfig {

	// 存储设置好的索引配置对象
	private static HashSet<ConfigBean> configBeans;

	private static class DefaultIndexConfig {

		private static final HashSet<ConfigBean> configBeansDefault = new HashSet<ConfigBean>();
		static {
			ConfigBean bean = new ConfigBean();
			configBeansDefault.add(bean);
		}
	}

	/**
	 * 获取系统索引配置信息
	 */
	public static HashSet<ConfigBean> getConfigBeans() {
		// 如果没有先使用setIndexConfig()实例化configBeans，则使用内部静态类预先创建好的对象，防止出现空指针
		if (configBeans == null) {
			return DefaultIndexConfig.configBeansDefault;
		}
		return configBeans;
	}

	/**
	 * 设置系统索引配置
	 */
	public static void setConfigBeans(HashSet<ConfigBean> configBeans) {
		IndexConfig.configBeans = configBeans;
	}

}
