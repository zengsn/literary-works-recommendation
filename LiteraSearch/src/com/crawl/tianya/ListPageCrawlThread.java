package com.crawl.tianya;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.crawl.tianya.db.TianYaDB;

/**
 * 列表页采集线程，通过线程爬取600部名著的url
 * 
 * @author CJP
 * 
 */
public class ListPageCrawlThread extends Thread {

	private boolean flag = false;
	private String url = "";
	private int frequency;

	/**
	 * @param name
	 *            线程的名字
	 * @param url
	 *            列表页url
	 * @param frequency
	 *            采集频率
	 */
	public ListPageCrawlThread(String name, String url, int frequency) {
		super(name);
		this.url = url;
		this.frequency = frequency;
	}

	@Override
	public void run() {
		flag = true;
		TianYaDB db = new TianYaDB();
		while (flag) {
			try {
				ListPageCrawl crawl = new ListPageCrawl(this.url);
				// 获取列表页URL信息
				List<String> urls = crawl.getPageUrl();
				db.saveInfoUrls(urls);
				TimeUnit.SECONDS.sleep(this.frequency);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 1; i < 12; i++) {
			String url = "http://www.ty2016.net/world/" + i + ".html";
			ListPageCrawlThread thread = new ListPageCrawlThread("list", url,
					30);
			thread.start();
		}
	}

}
