package com.crawl;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.entity.ChapterPage;
import com.util.ParseMD5;

public class ChapterPageCrawl extends BaseCrawl {

	private static HashMap<String, String> params;
	private String url;

	static {
		params = new HashMap<String, String>();
		params.put("Host", "");// 网站的主机地址
		params.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
	}

	public ChapterPageCrawl(String url) {
		readPageByGet(url, params, "utf-8");
		this.url = url;
	}

	public ChapterPage getChapter() {
		Document doc = Jsoup.parse(getPageSourceCode());
		String title = doc.getElementsByTag("h1").first().text();
		String content = doc.select("p[align=\"center\"] + p").first().html();
		ChapterPage cp = new ChapterPage();
		String id = ParseMD5.parseStrToMD5(this.url);
		cp.setId(id);
		cp.setUrl(this.url);
		cp.setTitle(title);
		cp.setContent(content);
		return cp;
	}

}
