package com.lucene.index.model;

import java.util.List;

import com.crawl.tianya.model.IntroSearchResultBean;

/**
 * 保存数据库返回的所有名著信息
 * @author CJP
 *
 */
public class LiteraSearchResultBean {
	private int count;// 符合条件的总记录条数
	private List<IntroSearchResultBean> datas;// 查询的结果集

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<IntroSearchResultBean> getDatas() {
		return datas;
	}

	public void setDatas(List<IntroSearchResultBean> datas) {
		this.datas = datas;
	}
}
