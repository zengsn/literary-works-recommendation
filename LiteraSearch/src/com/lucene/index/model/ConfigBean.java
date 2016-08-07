package com.lucene.index.model;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

/**
 * ʵʱ���������࣬����һЩ������������Ϣ�����������֣������Ĵ洢��ַ�������ķִ�����������commitƵ�ʣ��������ض�Ƶ��
 * 
 * @author CJP
 * 
 */
public class ConfigBean {

	// ���������֣�Ĭ��Ϊlitera
	private String indexName = "litera";

	// �����Ĵ洢·����Ĭ����index��
	private String indexPath = "/index/";

	// ����ʹ�õķִ�����Ĭ��ʹ�ñ�׼�ִ�
	private Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);

	// �������ض�Ƶ�ʣ����Ϊ10����СΪ0.025
	private double indexReopenMaxStaleSec = 10;

	// �������ض�Ƶ�ʣ����Ϊ10����СΪ0.025
	private double indexReopenMinStaleSec = 0.025;

	// ������commitƵ�ʣ�Ĭ��Ϊ60
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
