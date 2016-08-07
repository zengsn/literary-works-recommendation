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
 * ���Բ�ͬ�ִ����ķִ�Ч��
 * 
 * @author CJP
 * 
 */
public class AnalyzerTest {

	/**
	 * ����ָ���ķִ�����text�ı��з�Ϊһ����term�����ڵ�term֮���á�|������
	 * 
	 * @param analyzer
	 *            ʹ�õķִ���
	 * @param text
	 *            ��Ҫ���зֵ��ı�
	 * @throws Exception
	 */
	public static void analyze(Analyzer analyzer, String text) {
		System.out.println("�ִ�����" + analyzer.getClass());
		TokenStream tokenStream;
		try {
			tokenStream = analyzer.tokenStream("", new StringReader(text));
			// ͨ��ָ���ķִ������ı���Ϊtoken��
			tokenStream.reset();
			// ����incrementToken()�����ȵ��ø÷���
			CharTermAttribute term = tokenStream
					.getAttribute(CharTermAttribute.class);
			// ���һ��CharTermAttributeʵ�����󣬱�ʾtoken���ַ�����Ϣ
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
		String text = "����ѧԺ��Lucene ���� ����";

		analyzer = new StandardAnalyzer(Version.LUCENE_43);
		// ��׼�ִ�������Ӣ�ķ�Ϊ�ʸ��������ķ�Ϊ����
		analyze(analyzer, text);

		// IK�ִ�
		analyzer = new IKAnalyzer();
		analyze(analyzer, text);

		// �ո�ִ�
		analyzer = new WhitespaceAnalyzer(Version.LUCENE_43);
		analyze(analyzer, text);

		// �򵥷ִ�
		analyzer = new SimpleAnalyzer(Version.LUCENE_43);
		analyze(analyzer, text);

		// ���ַ��ִ�
		analyzer = new CJKAnalyzer(Version.LUCENE_43);
		analyze(analyzer, text);

		// �ؼ��ִַ�
		analyzer = new KeywordAnalyzer();
		analyze(analyzer, text);

		// �����Դʷִ�
		analyzer = new StopAnalyzer(Version.LUCENE_43);
		analyze(analyzer, text);
	}
}
