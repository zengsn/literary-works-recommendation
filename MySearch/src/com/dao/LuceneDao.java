package com.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.config.ConstantParams;
import com.entity.Chapter;
import com.entity.Para;
import com.entity.SearchResult;
import com.util.AssociationUtils;
import com.util.RegexUtils;
import com.util.StringUtils;

public class LuceneDao {

	/**
	 * 创建索引
	 * 
	 * @param objList
	 */
	public void createIndex(List<Object> objList) {
		try {
			File path = new File(ConstantParams.INDEX);
			Directory directory = FSDirectory.open(path);
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46,new IKAnalyzer());
			IndexWriter indexWriter = new IndexWriter(directory, config);
			for (Object o : objList) {
				if (o instanceof Chapter) {
					Chapter c = (Chapter) o;
					String title = c.getTitle();
					String author = c.getAuthor();
					String content = c.getContent();
					String name = c.getName();

					String regexStr = "&nbsp;&nbsp;&nbsp;&nbsp;(.*?)<br> <br>";
					List<String> paras = RegexUtils.getList(content, regexStr,
							1);
					for (String para : paras) {
						if (para.length() > 40) {
							Document doc = new Document();
							doc.add(new TextField("title", title, Field.Store.YES));
							doc.add(new TextField("author", author, Field.Store.YES));
							doc.add(new TextField("name", name, Field.Store.YES));
							doc.add(new TextField("para", para, Field.Store.YES));
							indexWriter.addDocument(doc);
						}
					}
				}
			}
			indexWriter.commit();
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关键字搜索
	 * 
	 * @param keywords
	 * @param start
	 * @param end
	 * @return
	 */
	public SearchResult searchIndex(String str, int start, int end,
			boolean isHighlight) {
		start = start > 0 ? start : 0;
		end = end > 0 ? end : 0;
		if (StringUtils.isEmpty(str) || start > end) {
			return null;
		}

		SearchResult sr = new SearchResult();
		List<Para> paraList = new ArrayList<Para>();
		try {
			Directory directory = FSDirectory.open(new File(ConstantParams.INDEX));
			IndexReader indexReader = DirectoryReader.open(directory);
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);

			BooleanQuery query = new BooleanQuery();

			// 获取联想词组
			Set<String> associations = AssociationUtils.getAssociationWords(str);
			for (String s : associations) {
				Term term = new Term("para", s);
				TermQuery termQuery = new TermQuery(term);
				query.add(termQuery, Occur.SHOULD);
			}

			// 查询结果关键字高亮显示
			String preTag = "<font color =\"red\">";
			String postTag = "</font>";
			Formatter formatter = new SimpleHTMLFormatter(preTag, postTag);
			Scorer fragmentScorer = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
			Fragmenter fragmenter = new SimpleFragmenter(1000);
			highlighter.setTextFragmenter(fragmenter);
			

			TopDocs topDocs = indexSearcher.search(query, end);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			sr.setCount(topDocs.totalHits);
			end = end > topDocs.totalHits ? topDocs.totalHits : end;
			for (int i = start; i < end; i++) {
				int docID = scoreDocs[i].doc;
				Document doc = indexSearcher.doc(docID);
				Para para = new Para();
				String hpara = null;
				if (isHighlight) {
					hpara = highlighter.getBestFragment(new IKAnalyzer(),"para", doc.get("para"));
				}
				String name = doc.get("name");
				String title = doc.get("title");
				String author = doc.get("author");
				if (hpara == null) {
					hpara = doc.get("para");
				}

				para.setPara(hpara);
				para.setAuthor(author);
				para.setName(name);
				para.setTitle(title);
				paraList.add(para);
			}
			sr.setParaList(paraList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sr;
	}

	public static void main(String[] args) {
		LuceneDao dao = new LuceneDao();
		String str = "流水";
		SearchResult sr = dao.searchIndex(str, 0, 5, true);
		List<Para> paraList = sr.getParaList();
		for (Para p : paraList) {
			System.out.println(p.getPara());
		}
	}
}
