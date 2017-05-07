package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class AssociationUtils {

	/**
	 * 获取关键字的同义词组
	 * 
	 * @param keyword
	 * @return
	 */
	private static String[] getSynonyms(String keyword) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
				new File("F:/MyEclipse/MySearch/src/com/util/Synonym.txt")),"utf-8"));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				temp = temp.substring(9, temp.length());
				String[] synonyms = temp.split(" ");
				for (String synoym : synonyms) {
					if (synoym.equals(keyword)) {
						return synonyms;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new String[] { keyword };
	}

	/**
	 * 获取所有关键字的同义词组
	 * 
	 * @param keywords
	 * @return
	 */
	private static Set<String> getAllSynonyms(Set<String> keywords) {

		Set<String> result = new HashSet<String>();
		for (String keyword : keywords) {
			String[] synonym = getSynonyms(keyword);
			for (String syn : synonym) {
				result.add(syn);
			}
		}
		return result;
	}

	/**
	 * 获取搜索词的联想词组
	 * 
	 * @param str
	 * @return
	 */
	public static Set<String> getAssociationWords(String str) {
		return getAllSynonyms(SegmentationUtils.ikSplit(str));
	}

}
