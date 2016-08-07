package com.crawl.tianya;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.crawl.base.CrawlBase;
import com.crawl.tianya.db.TianYaDB;
import com.crawl.tianya.model.ListPageBean;
import com.util.RegexUtil;

/**
 * �б�ҳ��Ϣ�ɼ�����ȡ���������ߡ���������һ���ĵ�ַ
 * 
 * @author CJP
 * 
 */
public class ListPageCrawl extends CrawlBase {

	private String url;
	// ��ǰҳ��url

	private static final String INFO_1 = "<ul class=\"co1\">(.*?)</ul>";
	private static final String INFO_2 = "<li>.*?<a href=\"(.*?)\" target=\"_blank\">(.*?)</a></li>";

	private static final int[] array = { 1, 2 };
	// ��ȡ��������������ʽ�е�λ��

	// ����ͷ��Ϣ
	private static HashMap<String, String> params;

	static {
		params = new HashMap<String, String>();
		params.put("Host", "www.ty2016.net");// ��վ��������ַ
		params.put("Referer", "http://www.ty2016.net/");// ��ǰҳ��һ���ĵ�ַ
		params.put("User-Agent",// �û�����
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
						+ "Chrome/45.0.2454.101 7Star/1.45.0.415 Safari/537.36");
	}

	public ListPageCrawl(String url) {
		readPageByGet(url, params, "utf-8");
		this.url = url;
	}

	/**
	 * ���˳���Ҫ�Ĳ���
	 */
	private String getInfo_First() {
		return RegexUtil.getFirstString(getPageSourceCode(), INFO_1, 1);
	}

	/**
	 * ��һ�����ˣ�ɸѡ��������һ����ַ�����������Ĳ���
	 */
	private List<String[]> getInfo_Second() {
		return RegexUtil.getList(getInfo_First(), INFO_2, array);
	}

	/**
	 * �ٴι��ˣ��ֿ����ߡ�����������һ����ַת��Ϊ���Ե����ӵ�ַ������List��
	 */
	private List<String[]> getInfo_Third() {
		List<String[]> list = getInfo_Second();
		List<String[]> new_list = new ArrayList<String[]>();
		for (String[] str : list) {
			String[] temp = new String[3];
			temp[0] = RegexUtil.getHttpUrl(str[0], url);
			String[] s = str[1].split("��");
			temp[1] = s[0];
			// ȥ���������ߵı�ǩ
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
	 * �����ߡ���������һ���ĵ�ַ��װ��bean
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
	 * ��ȡ�б�ҳ��һ����url
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
