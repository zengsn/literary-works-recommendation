package com.lucene.index.model;

import java.util.HashSet;

/**
 * 用于在一个工程中管理多个索引配置类
 * 
 * @author CJP
 * 
 */
public class IndexConfig {

	/**
	 * 存储设置好的多个索引配置项
	 */
	private static HashSet<IndexConfigBean> indexConfigBeans;

	/**
	 * 内部静态类用于实例化indexConfigBeans，防止出现空值
	 */
	private static class DefaultIndexConfig {
		/**
		 * 如果没有设置系统索引配置，则使用默认值
		 */
		private static final HashSet<IndexConfigBean> indexConfigBeansDefault = new HashSet<IndexConfigBean>();
		static {
			IndexConfigBean bean = new IndexConfigBean();
			indexConfigBeansDefault.add(bean);
		}
	}

	/**
	 * 获取系统索引配置信息
	 */
	public static HashSet<IndexConfigBean> getIndexConfig() {
		if (indexConfigBeans == null) {
			// 如果indexConfigBeans为空，则使用默认值
			return DefaultIndexConfig.indexConfigBeansDefault;
		}
		return indexConfigBeans;
	}

	/**
	 * 设置系统索引配置
	 */
	public static void setIndexConfig(HashSet<IndexConfigBean> indexConfigBeans) {
		IndexConfig.indexConfigBeans = indexConfigBeans;
	}

}
