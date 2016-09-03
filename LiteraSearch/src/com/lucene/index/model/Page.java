package com.lucene.index.model;

import java.util.List;

import com.crawl.tianya.model.Para;

public class Page {

	private int currPageNO;// 当前页号
	private int perPageSize = 15;// 每页显示的记录数
	private int totalRecords;// 总记录数
	private int totalPageNO;// 总页数
	private List<Para> paraList;// 存放每页显示的记录

	public int getCurrPageNO() {
		return currPageNO;
	}

	public void setCurrPageNO(int currPageNO) {
		this.currPageNO = currPageNO;
	}

	public int getPerPageSize() {
		return perPageSize;
	}

	public void setPerPageSize(int perPageSize) {
		this.perPageSize = perPageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPageNO() {
		return totalPageNO;
	}

	public void setTotalPageNO(int totalPageNO) {
		this.totalPageNO = totalPageNO;
	}

	public List<Para> getParaList() {
		return paraList;
	}

	public void setParaList(List<Para> paraList) {
		this.paraList = paraList;
	}

}
