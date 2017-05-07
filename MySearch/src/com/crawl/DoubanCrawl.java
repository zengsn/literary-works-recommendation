package com.crawl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.util.StringUtils;

public class DoubanCrawl {

	public String[] getHotTopic() {
		String[] hotTopics = new String[3];
		try {
			Document doc = Jsoup.connect("https://www.douban.com/gallery/").get();
			Elements elements = doc.select("ul[class='trend'] li a");
			hotTopics[0] = elements.get(0).text();
			hotTopics[1] = elements.get(1).text();
			hotTopics[2] = elements.get(2).text();
			System.out.println(StringUtils.arr2Str(hotTopics));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hotTopics;
	}
	
	public static void main(String[] args) {
		new DoubanCrawl().getHotTopic();
	}
}
