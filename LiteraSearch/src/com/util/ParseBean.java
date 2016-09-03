package com.util;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.crawl.tianya.model.Para;
import com.db.manager.DBServer;

public class ParseBean {

	public static Document bean2doc(Para para) {
		if (para == null) {
			return null;
		}
		Document doc = new Document();
		if (para.getPara() != null) {
			doc.add(new TextField(Para.PARA, para.getPara(), Store.YES));
		} else {
			return null;
		}
		if (para.getAuthor() != null)
			doc.add(new StringField(Para.AUTHOR, para.getAuthor(), Store.YES));
		if (para.getName() != null)
			doc.add(new StringField(Para.NAME, para.getName(), Store.YES));
		if (para.getChaptername() != null) {
			doc.add(new StringField(Para.CHAPTERNAME, para.getChaptername(),
					Store.YES));
		}
		return doc;
	}

	public static Para doc2bean(Document doc) {
		if (doc == null) {
			return null;
		}
		Para para = new Para();
		para.setAuthor(doc.get("author"));
		para.setName(doc.get("name"));
		para.setChaptername(doc.get("chaptername"));
		para.setPara(doc.get("para"));
		return para;
	}

	public static List<String> splitContent(String content) {
		if (content == null) {
			return null;
		}
		content = content.replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;", "")
				.replaceAll("<br />", "").replaceAll("\\n{2,}", "\n");
		List<String> paras = Arrays.asList(content.split("\n"));
		return paras;
	}

	public static void main(String[] args) throws Exception {
		DBServer dbServer = new DBServer("proxool.tianya");
		ResultSet rs = dbServer
				.select("select name,author,title,content from crawlchapterdetail limit 0,1");
		while (rs.next()) {
			List<String> paras = ParseBean
					.splitContent(rs.getString("content"));
			String name = rs.getString("name");
			String author = rs.getString("author");
			String chaptername = rs.getString("title");
			for (String p : paras) {
				Para para = new Para(author, name, chaptername, p);
				Document doc = ParseBean.bean2doc(para);
				Para pa = ParseBean.doc2bean(doc);
				System.out.println(pa);
			}
		}
	}
}
