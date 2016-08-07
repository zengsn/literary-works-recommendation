package com.crawl.tianya;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.crawl.tianya.db.TianYaDB;
import com.crawl.tianya.model.ChapterBean;
import com.crawl.tianya.model.IntroPageBean;

/**
 * ���ҳ�ɼ��߳�
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
				// �����ݿ��crawlintro���������ȡһ����¼��url
				String url = db.getIntroUrlRand(1);
				if (url != null) {
					IntroPageCrawl crawl = new IntroPageCrawl(url);
					// ��ȡ���ҳ��Ϣ
					IntroPageBean bean = crawl.analyzers();
					// ��ȡ�½��б���Ϣ
					List<ChapterBean> chapters = crawl.analyzer();
					if (bean != null && chapters != null) {
						// д��С˵�½ڸ���
						bean.setChapterCount(chapters == null ? 0 : chapters
								.size());
						// ������ҳ��Ϣ
						System.out.println(bean.getAuthor());
						//db.updateIntroInfo(bean);
						// �����½��б���Ϣ
						//System.out.println(bean.getId()+"----"+chapters.get(1).getUrl());
						System.out.println("�������ڴ���...");
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
