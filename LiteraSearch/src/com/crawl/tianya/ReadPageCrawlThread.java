package com.crawl.tianya;

import java.util.concurrent.TimeUnit;

import com.crawl.tianya.db.TianYaDB;
import com.crawl.tianya.model.ChapterBean;
import com.crawl.tianya.model.ReadPageBean;

/**
 * 阅读页采集线程
 * 
 * @author CJP
 * 
 */
public class ReadPageCrawlThread extends Thread {

	private boolean flag = false;

	public ReadPageCrawlThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		flag = true;
		TianYaDB db = new TianYaDB();
		while (flag) {
			try {
				// 获取可以采集的章节信息
				ChapterBean chapter = db.getChapterRand(1);
				if (chapter != null) {
					ReadPageCrawl readPage = new ReadPageCrawl(chapter.getUrl());
					// 获取阅读页信息
					ReadPageBean bean = readPage.analyzer();
					if (bean == null) {
						continue;
					}
					// 写入章节序号
					bean.setChapterId(chapter.getChapterId());
					// 保存阅读页信息
					db.saveReadInfo(bean);
					// 将章节设置成已采集
					db.updateChapterState(chapter.getId(), 0);
					TimeUnit.MILLISECONDS.sleep(500);
				} else {
					TimeUnit.MILLISECONDS.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ReadPageCrawlThread thread = new ReadPageCrawlThread("ReadInfo");
		thread.start();
	}
}
