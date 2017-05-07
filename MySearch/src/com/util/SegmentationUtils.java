package com.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class SegmentationUtils {

	/**
	 * 使用IK分词，默认智能分词
	 * 
	 * @param str
	 * @return
	 */
	public static Set<String> ikSplit(String str) {
		return ikSplit(str, true);
	}

	/**
	 * 使用IK分词
	 * 
	 * @param str
	 * @param b
	 *            若b=true是智能分词，b=false是细粒度分词
	 * @return
	 */
	public static Set<String> ikSplit(String str, boolean b) {

		Set<String> result = null;

		if (StringUtils.isEmpty(str)) {
			return result;
		}
		try {
			byte[] bt = str.getBytes();
			InputStream is = new ByteArrayInputStream(bt);
			Reader reader = new InputStreamReader(is);
			IKSegmenter iks = new IKSegmenter(reader, b);
			Lexeme t;
			result = new HashSet<String>();
			while ((t = iks.next()) != null) {
				result.add(t.getLexemeText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
