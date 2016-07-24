package com.lucene.index.model;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

/**
 * <p>
 * 实时索引配置类，保存索引的配置信息，包括：
 * </p>
 * 索引的名字，索引的存储地址，索引的分词器，索引的commit频率（每隔多长时间将内存索引合并到硬盘索引）， 索引的重读频率（每隔多长时间读一次内存索引）
 * 
 * @author CJP
 * 
 */
public class IndexConfigBean {
	/**
	 * 索引的名字，默认为index
	 */
	private String indexName = "index";
	/**
	 * 索引的存储路径，默认在index下
	 */
	private String indexPath = "/index/";
	/**
	 * 索引使用的分词器，默认使用标准分词
	 */
	private Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
	/**
	 * 索引的重读频率，最大为10，最小为0.025
	 */
	private double indexReopenMaxStaleSec = 10;
	/**
	 * 索引的重读频率，最大为10，最小为0.025
	 */
	private double indexReopenMinStaleSec = 0.025;
	/**
	 * 索引的commit频率，默认为60
	 */
	private int indexCommitSeconds = 60;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public double getIndexReopenMaxStaleSec() {
		return indexReopenMaxStaleSec;
	}

	public void setIndexReopenMaxStaleSec(double indexReopenMaxStaleSec) {
		this.indexReopenMaxStaleSec = indexReopenMaxStaleSec;
	}

	public double getIndexReopenMinStaleSec() {
		return indexReopenMinStaleSec;
	}

	public void setIndexReopenMinStaleSec(double indexReopenMinStaleSec) {
		this.indexReopenMinStaleSec = indexReopenMinStaleSec;
	}

	public int getIndexCommitSeconds() {
		return indexCommitSeconds;
	}

	public void setIndexCommitSeconds(int indexCommitSeconds) {
		this.indexCommitSeconds = indexCommitSeconds;
	}

}
