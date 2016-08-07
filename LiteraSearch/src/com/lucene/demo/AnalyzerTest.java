package com.lucene.demo;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 测试不同分词器的分词效果
 * 
 * @author CJP
 * 
 */
public class AnalyzerTest {

	/**
	 * 采用指定的分词器将text文本切分为一个个term，相邻的term之间用“|”隔开
	 * 
	 * @param analyzer
	 *            使用的分词器
	 * @param text
	 *            将要被切分的文本
	 * @throws Exception
	 */
	public static void analyze(Analyzer analyzer, String text) {
		System.out.println("分词器：" + analyzer.getClass());
		TokenStream tokenStream;
		try {
			tokenStream = analyzer.tokenStream("", new StringReader(text));
			// 通过指定的分词器将文本分为token流
			tokenStream.reset();
			// 调用incrementToken()必须先调用该方法
			CharTermAttribute term = tokenStream
					.getAttribute(CharTermAttribute.class);
			// 获得一个CharTermAttribute实例对象，表示token的字符串信息
			while (tokenStream.incrementToken()) {
				System.out.print(term.toString() + "|");
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Analyzer analyzer = null;
		String text = "极客学院，Lucene 案例 开发";

		analyzer = new StandardAnalyzer(Version.LUCENE_43);
		// 标准分词器，将英文分为词根，将中文分为单字
		analyze(analyzer, text);

		// IK分词
		analyzer = new IKAnalyzer();
		analyze(analyzer, text);

		// 空格分词
		analyzer = new WhitespaceAnalyzer(Version.LUCENE_43);
		analyze(analyzer, text);

		// 简单分词
		analyzer = new SimpleAnalyzer(Version.LUCENE_43);
		analyze(analyzer, text);

		// 二分法分词
		analyzer = new CJKAnalyzer(Version.LUCENE_43);
		analyze(analyzer, text);

		// 关键字分词
		analyzer = new KeywordAnalyzer();
		analyze(analyzer, text);

		// 被忽略词分词
		analyzer = new StopAnalyzer(Version.LUCENE_43);
		analyze(analyzer, text);
	}
}
