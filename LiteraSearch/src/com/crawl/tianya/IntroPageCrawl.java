package com.crawl.tianya;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.crawl.base.CrawlBase;
import com.crawl.tianya.db.TianYaDB;
import com.crawl.tianya.model.ChapterBean;
import com.crawl.tianya.model.IntroPageBean;
import com.util.ParseMD5;
import com.util.RegexUtil;

/**
 * ���ҳ��Ϣ�ɼ�����ȡ���������ߣ���飬�½ڵ������Ϣ
 * 
 * @author CJP
 * 
 */
public class IntroPageCrawl extends CrawlBase {

	private String url;
	// ��ǰҳ��url

	private static final String NAME = "<h1>(.*?)</h1>";
	// ������Ϣ����

	private static final String AUTHOR = "<h2>.*?<a target=\"_blank\" href=\".*?\">(.*?)</a></h2>";
	// ������Ϣ����							
	
	private static final String DESC = "<p>(.*?)</p>";
	// �����Ϣ����

	private static final String CHAPTER = "<dd><a href=\"(.*?)\">(.*?)</a></dd>";
	// �½���Ϣ����

	private static final int[] array = { 1, 2 };
	// ��ȡ��������������ʽ�е�λ��

	private static HashMap<String, String> params;
	// ����ͷ��Ϣ

	static {
		params = new HashMap<String, String>();
		params.put("Host", "www.ty2016.net");// ��վ��������ַ
		params.put("Referer", "http://www.ty2016.net/");// ��ǰҳ��һ���ĵ�ַ
		params.put("User-Agent",// �û�����
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
						+ "Chrome/45.0.2454.101 7Star/1.45.0.415 Safari/537.36");
	}

	public IntroPageCrawl(String url) {
		readPageByGet(url, params, "utf-8");
		this.url = url;
	}

	private String getName() {
		return RegexUtil.getFirstString(getPageSourceCode(), NAME, 1);
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	private String getAuthor() {
		return RegexUtil.getFirstString(getPageSourceCode(), AUTHOR, 1);
	}

	/**
	 * ��ȡ�������
	 * 
	 * @return
	 */
	private String getDesc() {
		return RegexUtil.getFirstString(getPageSourceCode(), DESC, 1);
	}

	/**
	 * ��ȡ�½��б���Ϣ�����Ķ�ҳ�ĵ�ַ���½ڱ���
	 */
	private List<String[]> getChapter() {
		return RegexUtil.getList(getPageSourceCode(), CHAPTER, array);
	}

	/**
	 * ���½ڱ��⡢��Ӧ���Ķ�ҳ��url���½���ŷ�װ��bean
	 * 
	 * @return
	 */
	public List<ChapterBean> analyzer() {
		List<String[]> chapters = getChapter();
		List<ChapterBean> beans = new ArrayList<ChapterBean>();
		int n = 1;
		for (String[] s : chapters) {
			ChapterBean bean = new ChapterBean();
			bean.setTitle(s[1]);
			bean.setUrl(RegexUtil.getHttpUrl(s[0], url));
			bean.setId(ParseMD5.parseStrToMD5(bean.getUrl()));
			bean.setChapterId(n++);
			beans.add(bean);
		}
		return beans;
	}

	/**
	 * ����������Ϣ��װ��bean���������ߣ���飬�½ڵ���Ϣ
	 */
	public IntroPageBean analyzers() {
		IntroPageBean bean = new IntroPageBean();
		bean.setName(getName());
		bean.setUrl(this.url);
		bean.setId(ParseMD5.parseStrToMD5(bean.getUrl()));
		bean.setAuthor(getAuthor());
		bean.setDesc(getDesc());
		return bean;
	}

	public static void main(String[] args) {
		IntroPageCrawl crawl = new IntroPageCrawl(
				"http://www.ty2016.net/world/Calvino3/");
		 System.out.println(crawl.getAuthor());
		// System.out.println(crawl.getDesc());
		// for (String[] s : crawl.getChapter()) {
		// System.out.println(s[0] + "--->" + s[1]);
		// }
		TianYaDB db = new TianYaDB();
		db.updateIntroInfo(crawl.analyzers());
		db.saveChapterInfo(crawl.analyzer(), "1f1f1ffd86d59b04bdd3673f09b45c2c");
		// IntroPageBean bean = crawl.analyzers();
		// System.out.println(bean.getName());
		// System.out.println(bean.getAuthor());
		// System.out.println(bean.getDesc());
		// System.out.println(bean.getUrl());
		// System.out.println(bean.getId());
		// System.out.println(JsonUtil.parseJson(crawl.analyzer()));
		// System.out.println(JsonUtil.parseJson(crawl.analyzers()));

	}
}
