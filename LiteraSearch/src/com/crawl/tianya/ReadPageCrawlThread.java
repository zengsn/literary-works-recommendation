package com.crawl.tianya;

import java.util.concurrent.TimeUnit;

import com.crawl.tianya.db.TianYaDB;
import com.crawl.tianya.model.ChapterBean;
import com.crawl.tianya.model.ReadPageBean;

/**
 * �Ķ�ҳ�ɼ��߳�
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
				// ��ȡ���Բɼ����½���Ϣ
				ChapterBean chapter = db.getChapterRand(1);
				if (chapter != null) {
					ReadPageCrawl readPage = new ReadPageCrawl(chapter.getUrl());
					// ��ȡ�Ķ�ҳ��Ϣ
					ReadPageBean bean = readPage.analyzer();
					if (bean == null) {
						continue;
					}
					// д���½����
					bean.setChapterId(chapter.getChapterId());
					// �����Ķ�ҳ��Ϣ
					db.saveReadInfo(bean);
					// ���½����ó��Ѳɼ�
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
