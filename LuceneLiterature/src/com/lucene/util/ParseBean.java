package com.lucene.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;

import com.lucene.index.litera.model.LiteraBean;
import com.lucene.index.litera.model.LiteraIndexBean;

public class ParseBean {

	public static Document parseLiteraIndexToDocument(LiteraIndexBean bean) {
		if (bean == null) {
			return null;
		}

		Document doc = new Document();
		if (bean.getName() != null) {
			doc.add(new StringField(LiteraIndexBean.INDEX_NAME, bean.getName(),
					Store.YES));
		} else {
			return null;
		}
		if (bean.getPath() != null) {
			doc.add(new StringField(LiteraIndexBean.INDEX_PATH, bean.getPath(),
					Store.YES));
		}
		if (bean.getContent() != null) {
			doc.add(new StringField(LiteraIndexBean.INDEX_CONTENT, bean
					.getContent(), Store.YES));
		}
		return doc;
	}

	public List<LiteraBean> getDatas(String path) {
		List<LiteraBean> beans = new ArrayList<LiteraBean>();
		File file = new File(path);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			LiteraBean bean = new LiteraBean();
			if (files[i].isFile() && files[i].getName().endsWith(".txt")) {
				bean.setName(files[i].getName());
				try {
					bean.setPath(files[i].getCanonicalPath());
					bean.setContent(getContentFromPath(files[i]
							.getCanonicalPath()));
					beans.add(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return beans;
	}

	/**
	 * 根据指定路径返回文件中的内容
	 * 
	 * @param path
	 * @return
	 */
	private String getContentFromPath(String path) {

		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer("");
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(path)), "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		ParseBean bean = new ParseBean();
		List<LiteraBean> beans = bean
				.getDatas("C:/Users/CJP/Desktop/Lucene/毕设相关/419部世界名著合集txt书籍");
		for (LiteraBean i : beans) {
			System.out.println(i.getName());
		}
	}

}
