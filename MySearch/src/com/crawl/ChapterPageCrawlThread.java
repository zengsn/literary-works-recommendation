package com.crawl;

import com.dao.BaseDao;
import com.entity.ChapterPage;
import com.util.ParseMD5;

public class ChapterPageCrawlThread extends Thread {

	private BaseDao dao = new BaseDao();

	@Override
	public void run() {
		while (true) {
			String url = dao.getUrlByRand("contentsPage", "0");
			if (url.equals("") || url == null) {
				return;
			}
			ChapterPageCrawl cpCrawl = new ChapterPageCrawl(url);
			ChapterPage cp = cpCrawl.getChapter();
			boolean b = dao.add(cp);
			if (b) {
				System.out.println(cp.getTitle() + "-->" + "插入成功");
				dao.updateById("contentsPage", "state", "1",
						ParseMD5.parseStrToMD5(url));
			}
		}
	}

	public static void main(String[] args) {
		ChapterPageCrawlThread thread = new ChapterPageCrawlThread();
		thread.start();
	}
}
