package com.lucene.index.model;

import java.util.List;

import com.crawl.tianya.model.Para;

/**
 * 保存数据库返回的所有名著信息
 * 
 * @author CJP
 * 
 */
public class LiteraSearchResultBean {
	private int count;// 符合条件的总记录条数
	private List<Para> datas;// 查询的结果集

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Para> getDatas() {
		return datas;
	}

	public void setDatas(List<Para> datas) {
		this.datas = datas;
	}
}
