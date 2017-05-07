package com.entity;

import java.util.List;

public class SearchResult {

	private int count; // 查询到的记录条数
	private List<Para> paraList; // 查询到的文段信息

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Para> getParaList() {
		return paraList;
	}

	public void setParaList(List<Para> paraList) {
		this.paraList = paraList;
	}

}
