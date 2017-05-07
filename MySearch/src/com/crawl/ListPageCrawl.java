package com.crawl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.entity.ListPage;
import com.util.ParseMD5;
import com.util.RegexUtils;

public class ListPageCrawl extends BaseCrawl {

	private static HashMap<String, String> params;
	private String url;

	static {
		params = new HashMap<String, String>();
		params.put("Host", "");// 网站的主机地址
		params.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
	}

	public ListPageCrawl(String url) {
		readPageByGet(url, params, "utf-8");
		this.url = url;
	}

	public List<ListPage> getLists() {
		Document doc = Jsoup.parse(getPageSourceCode());
		Elements elements = doc.select("ul[class=\"co1\"] a");
		List<ListPage> lpList = new ArrayList<ListPage>();
		for (Element e : elements) {
			String url = RegexUtils.getHttpUrl(e.attr("href"), this.url);
			String id = ParseMD5.parseStrToMD5(url);
			String[] text = e.text().split("：");
			String author = text[0];
			String name = text[1];
			ListPage listPage = new ListPage();
			listPage.setId(id);
			listPage.setUrl(url);
			listPage.setAuthor(author);
			listPage.setName(name);
			lpList.add(listPage);
		}
		return lpList;

	}

}
