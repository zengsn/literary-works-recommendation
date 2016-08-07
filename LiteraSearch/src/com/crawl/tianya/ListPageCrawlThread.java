package com.crawl.tianya;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.crawl.tianya.db.TianYaDB;

/**
 * �б�ҳ�ɼ��̣߳�ͨ���߳���ȡ600��������url
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
	 *            �̵߳�����
	 * @param url
	 *            �б�ҳurl
	 * @param frequency
	 *            �ɼ�Ƶ��
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
				// ��ȡ�б�ҳURL��Ϣ
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
