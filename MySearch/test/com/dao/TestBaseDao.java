package com.dao;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.crawl.ListPageCrawl;
import com.entity.Chapter;
import com.entity.ListPage;
import com.util.ParseMD5;
import com.util.RegexUtils;

public class TestBaseDao {

	/**
	 * 从数据库中获取数据
	 * 
	 * @throws IOException
	 */
	@Test
	public void test01() throws IOException {
		BaseDao dao = new BaseDao();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", "000524a250e60b2acc695b114d83c99f");
		List<Object> testList = dao.findByCondition("Chapter", paramMap);
		for (Object obj : testList) {
			if (obj instanceof Chapter) {
				Chapter chapter = (Chapter) obj;
				System.out.println(chapter.getId());
				System.out.println(chapter.getUrl());
				System.out.println(chapter.getAuthor());
				System.out.println(chapter.getTitle());
				System.out.println(chapter.getContent());
			}
		}
	}

	/**
	 * 往listPage中插入数据
	 */
	@Test
	public void test02() {
		for (int i = 2; i < 15; i++) {
			String url = "http://www.ty2016.net/world/" + i + ".html";
			ListPageCrawl crawl = new ListPageCrawl(url);
			List<ListPage> lpList = crawl.getLists();
			BaseDao dao = new BaseDao();
			for (ListPage lp : lpList) {
				dao.add(lp);
			}
		}
	}

	/**
	 * 随机获取一个URL
	 */
	@Test
	public void test03() {
		BaseDao dao = new BaseDao();
		String url = dao.getUrlByRand("listPage", "0");
		System.out.println(url);
	}

	/**
	 * 更新数据
	 */
	@Test
	public void test04() {
		BaseDao dao = new BaseDao();
		dao.updateById("test", "name", "10", "2");
	}

	/**
	 * 测试正则
	 */
	@Test
	public void test05() {
		String test = "insert into chapterpage(id,url,title,content) value(?,?,?,?)";
		Pattern pattern = Pattern.compile(".*\\(([a-zA-z_,]*)\\).*");
		Matcher matcher = pattern.matcher(test);
		while (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}

	/**
	 * 使用PD5加密
	 */
	@Test
	public void test06() {
		String url = "http://www.ty2016.net/world/Calvino13/84061.html";
		System.out.println(ParseMD5.parseStrToMD5(url));
	}

	@Test
	public void test07() {
		BaseDao dao = new BaseDao();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", "000524a250e60b2acc695b114d83c99f");
		List<Object> testList = dao.findByCondition("Chapter", paramMap);
		List<String> paras = null;
		for (Object obj : testList) {
			if (obj instanceof Chapter) {
				Chapter chapter = (Chapter) obj;
				String content = chapter.getContent();
				String regexStr = "&nbsp;&nbsp;&nbsp;&nbsp;(.*?)<br> <br>";
				paras = RegexUtils.getList(content, regexStr, 1);
			}
		}
		for (String s : paras) {
			System.out.println(s);
		}
	}

	@Test
	public void test08() throws IOException {
		Reader reader = new StringReader(
				"少数几个人的生活并不能包括各民族的生活，因为还没有发现那几个人和各民族之间的关系。");
		//Analyzer analyzer = new ChineseAnalyzer();
		//Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_46);
		Analyzer analyzer = new IKAnalyzer();
		TokenStream ts = analyzer.tokenStream("myField", reader);
		CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
		ts.reset();
		while (ts.incrementToken()) {
			System.out.print(termAtt.toString() + " ");
		}

	}

}
