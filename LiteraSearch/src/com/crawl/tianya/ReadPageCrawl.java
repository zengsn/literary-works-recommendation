package com.crawl.tianya;

import java.util.HashMap;

import com.crawl.base.CrawlBase;
import com.crawl.tianya.db.TianYaDB;
import com.crawl.tianya.model.ReadPageBean;
import com.util.ParseMD5;
import com.util.RegexUtil;

/**
 * @author CJP
 * 
 */
public class ReadPageCrawl extends CrawlBase {

	private String url;
	// 当前页的url

	private static final String TITLE = "<h1>(.*?)</h1>";
	// 标题信息正则

	private static final String CONTENT = "<p>(.*?)</p>";
	// 正文信息正则

	private static HashMap<String, String> params;
	// 请求头信息

	static {
		params = new HashMap<String, String>();
		params.put("Host", "www.ty2016.net");// 网站的主机地址
		params.put("Referer", "http://www.ty2016.net/");// 当前页上一跳的地址
		params.put("User-Agent",// 用户代理
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
						+ "Chrome/45.0.2454.101 7Star/1.45.0.415 Safari/537.36");
	}

	public ReadPageCrawl(String url) {
		readPageByGet(url, params, "utf-8");
		this.url = url;
	}

	/**
	 * 获取阅读页的标题
	 */
	private String getTitle() {
		return RegexUtil.getFirstString(getPageSourceCode(), TITLE, 1);
	}

	/**
	 * 获取阅读页的正文
	 */
	private String getContent() {
		return RegexUtil.getString(getPageSourceCode(), CONTENT, 1);
	}

	/**
	 * 将阅读页信息封装成bean
	 */
	public ReadPageBean analyzer() {
		ReadPageBean bean = new ReadPageBean();
		bean.setUrl(this.url);
		bean.setId(ParseMD5.parseStrToMD5(bean.getUrl()));
		bean.setTitle(getTitle());
		bean.setContent(getContent());
		return bean;
	}

	public static void main(String[] args) {
		ReadPageCrawl crawl = new ReadPageCrawl(
				"http://www.ty2016.net/horror/DarkTower_7/106324.html");
		// System.out.println(crawl.getTitle());
		// System.out.println(crawl.getContent());
		ReadPageBean bean = crawl.analyzer();
		bean.setChapterId(3);
		// System.out.println(bean.getTitle());
		// System.out.println(bean.getContent());
		// System.out.println(JsonUtil.parseJson(crawl.analyzer()));
		TianYaDB db = new TianYaDB();
		db.saveReadInfo(bean);
	}

}
