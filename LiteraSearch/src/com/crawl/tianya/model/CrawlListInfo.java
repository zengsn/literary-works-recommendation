package com.crawl.tianya.model;

/**
 * 爬虫程序入口地址信息：列表页信息
 * 
 * @author CJP
 * 
 */
public class CrawlListInfo {

	private String url;// 爬虫程序入口的url
	private String info;// 爬虫程序入口的描述
	private int frequency;// 爬虫程序入口的抓取频率

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}
