package com.crawl.tianya;

import java.util.List;

import com.crawl.tianya.db.TianYaDB;
import com.crawl.tianya.model.CrawlListInfo;

/**
 * 启动所有线程，实现分布式爬取
 * 
 * @author CJP
 * 
 */
public class CrawlStart {
	// 列表页
	private static boolean CrawlList = false;
	// 简介页
	private static boolean CrawlIntro = false;
	// 阅读页
	private static boolean CrawlRead = false;

	// 简介页线程数目
	private static int crawlIntroThreadNum = 2;
	// 阅读页线程数目
	private static int crawlReadThreadNum = 10;

	/**
	 * 启动列表页采集线程
	 */
	public void startCrawlList() {
		// 如果已启动，则返回，否则启动线程
		if (CrawlList) {
			return;
		}
		CrawlList = true;
		TianYaDB db = new TianYaDB();
		List<CrawlListInfo> infos = db.getCrawlListInfos();
		if (infos == null) {
			return;
		}
		for (CrawlListInfo info : infos) {
			if (info.getUrl() == null || "".equals(info.getUrl())) {
				continue;
			}
			ListPageCrawlThread thread = new ListPageCrawlThread(
					info.getInfo(), info.getUrl(), info.getFrequency());
			thread.start();
		}
	}

	/**
	 * 启动简介页采集线程
	 */
	public void startCrawlIntro() {
		// 如果已启动，则返回，否则启动线程
		if (CrawlIntro) {
			return;
		}
		CrawlIntro = true;
		for (int i = 0; i < crawlIntroThreadNum; i++) {
			IntroPageCrawlThread thread = new IntroPageCrawlThread(
					"Litera info page " + i);
			thread.start();
		}
	}

	/**
	 * 启动阅读页采集线程
	 */
	public void startCrawlRead() {
		if (CrawlRead) {
			return;
		}
		CrawlRead = true;
		for (int i = 0; i < crawlReadThreadNum; i++) {
			ReadPageCrawlThread thread = new ReadPageCrawlThread(
					"Litera read page " + i);
			thread.start();
		}
	}

	public static void main(String[] args) {
		CrawlStart start = new CrawlStart();
		//start.startCrawlList();
		start.startCrawlIntro();
		start.startCrawlRead();
	}

}
