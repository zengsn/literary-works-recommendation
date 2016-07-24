package com.lucene.demo;

import java.io.File;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 创建索引
 * 
 * @author CJP
 * 
 */
public class IndexCreate {

	public static void main(String[] args) {
		IndexWriter indexWriter = null;
		Directory directory = null;
		// 指定默认的分词技术：标准分词——英文按词根分，中文按单个汉字分
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
		// 索引的配置信息,参数Analyzer和Lucene的版本
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43,
				analyzer);
		// 设置索引的打开方式，没有就新建，有就覆盖
		config.setOpenMode(OpenMode.CREATE);
		try {
			// 指定索引在硬盘上的存储路径
			directory = FSDirectory.open(new File(
					"C:/Users/CJP/Desktop/Lucene/index/test3"));
			// 如果索引处于锁定状态就解锁
			if (IndexWriter.isLocked(directory)) {
				IndexWriter.unlock(directory);
			}
			// 通过IndexWriter创建索引,参数为Directory和IndexWriterConfig
			indexWriter = new IndexWriter(directory, config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 往索引中添加文档
		Document doc1 = new Document();
		// 创建域,指定域名，域值，是否可以被存储
		Field number1 = new IntField("number", 1, Field.Store.YES);
		Field id1 = new StringField("id", "abcde", Field.Store.YES); // StringField作为一个整体，不会被分词
		Field title1 = new TextField("title", "惠州学院计算机科学系", Field.Store.YES); // 采用默认的分词器分词
		// 往文档中添加域
		doc1.add(number1);
		doc1.add(id1);
		doc1.add(title1);

		Document doc2 = new Document();
		// 创建域,指定域名，域值，是否可以被存储
		Field number2 = new IntField("number", 2, Field.Store.YES);
		Field id2 = new StringField("id", "zyxwv", Field.Store.YES); // StringField作为一个整体，不会被分词
		Field title2 = new TextField("title", "计算机科学系计师2班", Field.Store.YES); // 采用默认的分词器分词
		// 往文档中添加域
		doc2.add(number2);
		doc2.add(id2);
		doc2.add(title2);

		try {
			// 往索引中添加文档
			indexWriter.addDocument(doc1);
			indexWriter.addDocument(doc2);

			// 将indexWriter操作提交，如果不提交，之前的操作将不会保存到硬盘
			// 但是这一步很消耗系统资源，索引执行该操作需要有一定的策略
			// 每一步的操作都需要调用该方法才能将请求提交的硬盘中的索引中
			indexWriter.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 关闭资源
			indexWriter.close();
			directory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
