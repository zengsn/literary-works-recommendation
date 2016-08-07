package com.crawl.tianya;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.crawl.base.CrawlBase;
import com.crawl.tianya.db.TianYaDB;
import com.crawl.tianya.model.ListPageBean;
import com.util.RegexUtil;

/**
 * 列表页信息采集：获取名著的作者、书名、下一跳的地址
 * 
 * @author CJP
 * 
 */
public class ListPageCrawl extends CrawlBase {

	private String url;
	// 当前页的url

	private static final String INFO_1 = "<ul class=\"co1\">(.*?)</ul>";
	private static final String INFO_2 = "<li>.*?<a href=\"(.*?)\" target=\"_blank\">(.*?)</a></li>";

	private static final int[] array = { 1, 2 };
	// 提取的内容在正则表达式中的位置

	// 请求头信息
	private static HashMap<String, String> params;

	static {
		params = new HashMap<String, String>();
		params.put("Host", "www.ty2016.net");// 网站的主机地址
		params.put("Referer", "http://www.ty2016.net/");// 当前页上一跳的地址
		params.put("User-Agent",// 用户代理
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
						+ "Chrome/45.0.2454.101 7Star/1.45.0.415 Safari/537.36");
	}

	public ListPageCrawl(String url) {
		readPageByGet(url, params, "utf-8");
		this.url = url;
	}

	/**
	 * 过滤出需要的部分
	 */
	private String getInfo_First() {
		return RegexUtil.getFirstString(getPageSourceCode(), INFO_1, 1);
	}

	/**
	 * 进一步过滤，筛选出含有下一跳地址、作者书名的部分
	 */
	private List<String[]> getInfo_Second() {
		return RegexUtil.getList(getInfo_First(), INFO_2, array);
	}

	/**
	 * 再次过滤，分开作者、书名，将下一跳地址转化为绝对的链接地址，存入List中
	 */
	private List<String[]> getInfo_Third() {
		List<String[]> list = getInfo_Second();
		List<String[]> new_list = new ArrayList<String[]>();
		for (String[] str : list) {
			String[] temp = new String[3];
			temp[0] = RegexUtil.getHttpUrl(str[0], url);
			String[] s = str[1].split("：");
			temp[1] = s[0];
			// 去除书名两边的标签
			if (s[1].indexOf("<b>") > 1 || s[1].indexOf("</b>") > 1) {
				temp[2] = s[1].substring("<br>".length() - 1, s[1].length()
						- "</br>".length() + 1);
			} else {
				temp[2] = s[1];
			}
			new_list.add(temp);
		}
		return new_list;
	}

	/**
	 * 将作者、书名和下一跳的地址封装成bean
	 */
	public List<ListPageBean> analyzer() {
		List<String[]> list = getInfo_Third();
		List<ListPageBean> beans = new ArrayList<ListPageBean>();
		for (String[] s : list) {
			ListPageBean bean = new ListPageBean();
			bean.setAuthor(s[1]);
			bean.setName(s[2]);
			bean.setUrl(s[0]);
			beans.add(bean);
		}
		return beans;
	}

	/**
	 * 获取列表页下一跳的url
	 * 
	 * @return
	 */
	public List<String> getPageUrl() {
		List<String> list = new ArrayList<String>();
		List<ListPageBean> beans = analyzer();
		for (ListPageBean bean : beans) {
			list.add(bean.getUrl());
		}
		return list;
	}

	public static void main(String[] args) {
		ListPageCrawl crawl = new ListPageCrawl("http://www.ty2016.net/world/15.html");
		// System.out.println(crawl.getInfo_First());
		// List<String[]> list = crawl.getInfo_Third();
		// for (String[] s : list) {
		// System.out.println(s[0] + "-->" + s[1] + "-->" + s[2]);
		// }
//		List<ListPageBean> list = crawl.analyzer();
//		for (ListPageBean bean : list) {
//			System.out.println(bean.getAuthor());
//			System.out.println(bean.getName());
//			System.out.println(bean.getUrl());
//			System.out.println("----------------------------");
//		}
		TianYaDB db = new TianYaDB();
		db.saveInfoUrls(crawl.getPageUrl());
	}

}
