package com.crawl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.entity.ContentsPage;
import com.entity.IntroducePage;
import com.util.ParseMD5;
import com.util.RegexUtils;

public class IntroducePageCrawl extends BaseCrawl {

	private static HashMap<String, String> params;
	private String url;

	static {
		params = new HashMap<String, String>();
		params.put("Host", "");// 网站的主机地址
		params.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
	}

	public IntroducePageCrawl(String url) {
		readPageByGet(url, params, "utf-8");
		this.url = url;
	}

	public IntroducePage getIntroduce() {
		Document doc = Jsoup.parse(getPageSourceCode());
		Element flag = doc.getElementsByTag("h3").first();
		if (flag == null) {
			return null;
		}
		String name = doc.getElementsByTag("h1").first().text();
		String author = doc.select("div[id=\"main\"] h2").first().text();
		String description = doc.select("div[id=\"main\"] p").first().text();
		IntroducePage introducePage = new IntroducePage();
		String id = ParseMD5.parseStrToMD5(this.url);
		introducePage.setId(id);
		introducePage.setUrl(this.url);
		introducePage.setName(name);
		introducePage.setAuthor(author);
		introducePage.setDescription(description);
		return introducePage;
	}

	public List<ContentsPage> getContents() {
		Document doc = Jsoup.parse(getPageSourceCode());
		Element element = doc.getElementsByTag("h3").first();
		String flag = element.text();
		if (flag == null || flag.equals("")) {
			return null;
		}
		List<ContentsPage> cpList = new ArrayList<ContentsPage>();
		Elements elements = doc.select("dd a");
		for (Element e : elements) {
			String url = RegexUtils.getHttpUrl(e.attr("href"), this.url);
			//System.out.println(url);
			String id = ParseMD5.parseStrToMD5(url);
			String title = e.text();

			ContentsPage cp = new ContentsPage();
			cp.setId(id);
			cp.setUrl(url);
			cp.setTitle(title);
			cp.setIntroid(ParseMD5.parseStrToMD5(this.url));
			cp.setState("0");
			cpList.add(cp);
		}
		return cpList;
	}
	
}
