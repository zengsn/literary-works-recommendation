package com.crawl.tianya;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.crawl.tianya.db.TianYaDB;
import com.crawl.tianya.model.ChapterBean;
import com.crawl.tianya.model.IntroPageBean;

/**
 * 简介页采集线程
 * 
 * @author CJP
 * 
 */
public class IntroPageCrawlThread extends Thread {

	private boolean flag = false;

	public IntroPageCrawlThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		flag = true;
		try {
			TianYaDB db = new TianYaDB();
			while (flag) {
				// 从数据库的crawlintro表中随机获取一条记录的url
				String url = db.getIntroUrlRand(1);
				if (url != null) {
					IntroPageCrawl crawl = new IntroPageCrawl(url);
					// 获取简介页信息
					IntroPageBean bean = crawl.analyzers();
					// 获取章节列表信息
					List<ChapterBean> chapters = crawl.analyzer();
					if (bean != null && chapters != null) {
						// 写入小说章节个数
						bean.setChapterCount(chapters == null ? 0 : chapters
								.size());
						// 保存简介页信息
						System.out.println(bean.getAuthor());
						//db.updateIntroInfo(bean);
						// 保存章节列表信息
						//System.out.println(bean.getId()+"----"+chapters.get(1).getUrl());
						System.out.println("数据正在存入...");
						db.saveChapterInfo(chapters, bean.getId());
					}
					TimeUnit.MILLISECONDS.sleep(50);
				} else {
					TimeUnit.MILLISECONDS.sleep(100);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		IntroPageCrawlThread thread = new IntroPageCrawlThread("IntroInfo");
		thread.start();
	}
}
