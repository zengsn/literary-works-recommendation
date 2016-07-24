package com.lucene.demo;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 根据关键字检索索引
 * 
 * @author CJP
 * 
 */
public class IndexSearch {

	public static void main(String[] args) {
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File(
					"C:/Users/CJP/Desktop/Lucene/index/test3"));
			// 读取索引
			DirectoryReader dReader = DirectoryReader.open(directory);
			// 创建索引检索对象
			IndexSearcher indexSearcher = new IndexSearcher(dReader);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
			String key = "计算机"; // 查询关键字
			// 将查询关键字转化为Query对象，QueryParser参数为Lucene版本，查询的域，使用的分词技术
			QueryParser parser = new QueryParser(Version.LUCENE_43, "title",
					analyzer);
			Query query = parser.parse(key);
			// 检索索引，输出符合条件的前10条记录
			int topHits = 10;
			TopDocs docs = indexSearcher.search(query, topHits);
			if (docs != null) {
				System.out.println("符合查询条件的文档总数有：" + docs.totalHits);
				ScoreDoc[] hits = docs.scoreDocs; // 查询到的所有文档的编号的集合
				int end = Math.min(topHits, docs.totalHits); // 取两者中的较小值
				System.out.println("结果为：");
				for (int i = 0; i < end; i++) {
					int docID = hits[i].doc;
					// 根据文档编号获取到对应的文档
					Document d = indexSearcher.doc(docID);
					System.out.println(d.get("title"));
				}
			}
			dReader.close();
			directory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
