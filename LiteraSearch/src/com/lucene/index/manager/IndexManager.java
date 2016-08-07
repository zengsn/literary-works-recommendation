package com.lucene.index.manager;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.search.NRTManagerReopenThread;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.lucene.index.model.ConfigBean;
import com.lucene.index.model.IndexConfig;

/**
 * 实时索引管理类，需要是单例模式，一个索引配置对应一个索引管理
 * 
 * @author CJP
 * 
 */
public class IndexManager {

	private IndexWriter indexWriter;
	// 实现索引的增加、删除、修改

	private TrackingIndexWriter trackingIndexWriter;
	// IndexWriter的代理类，由它来完成索引的增加、删除、修改

	// private Analyzer analyzer;

	private NRTManager nrtManager;
	// 管理内存索引、合并索引、硬盘索引，实现内存索引写入到硬盘索引，对外提供一个可用的IndexSearcher

	private NRTManagerReopenThread nrtManagerReopenThread;
	// 内存索引的重读线程

	private IndexCommitThread indexCommitThread;
	// 内存索引写入到硬盘索引线程

	private ConfigBean ConfigBean;// 索引配置类
	
	private Analyzer analyzer;

	private static class LazyIndexManager {
		// 保存系统中的IndexManager对象
		private static HashMap<String, IndexManager> indexManagerMap = new HashMap<String, IndexManager>();

		static {
			for (ConfigBean bean : IndexConfig.getConfigBeans()) {
				indexManagerMap
						.put(bean.getIndexName(), new IndexManager(bean));
			}
		}
	}

	private IndexManager(ConfigBean ConfigBean) {

		String indexFile = ConfigBean.getIndexPath() + "/"
				+ ConfigBean.getIndexName();
		// 索引文件保存的完整路径：文件路径+文件名
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				Version.LUCENE_43, ConfigBean.getAnalyzer());
		analyzer = ConfigBean.getAnalyzer();
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		// 指定索引的打开方式，没有就新建，有就追加
		this.ConfigBean = ConfigBean;
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File(indexFile));
			if (IndexWriter.isLocked(directory)) {
				// 判断此路径上的索引是否被锁定，锁定则解锁
				IndexWriter.unlock(directory);
			}
			this.indexWriter = new IndexWriter(directory, indexWriterConfig);
			this.trackingIndexWriter = new TrackingIndexWriter(indexWriter);
			// 将indexWriter委托给trackingIndexWriter
			this.nrtManager = new NRTManager(trackingIndexWriter,
					new SearcherFactory());
			// 由nrtManager来管理trackingIndexWriter
		} catch (Exception e) {
			e.printStackTrace();
		}
		setThread();
		// 开启系统的守护线程
	}

	// 设置IndexSearcher守护线程
	private void setThread() {
		// 内存索引重读线程
		this.nrtManagerReopenThread = new NRTManagerReopenThread(nrtManager,
				ConfigBean.getIndexReopenMaxStaleSec(),
				ConfigBean.getIndexReopenMinStaleSec());
		this.nrtManagerReopenThread.setName("NRTManager reopen thread");// 线程名
		this.nrtManagerReopenThread.setDaemon(true);
		this.nrtManagerReopenThread.start();

		// 内存索引写入到硬盘索引线程
		this.indexCommitThread = new IndexCommitThread(
				ConfigBean.getIndexName() + " index commmit thread");
		this.indexCommitThread.setDaemon(true);
		this.indexCommitThread.start();
	}

	// 内存索引写入到硬盘索引线程
	private class IndexCommitThread extends Thread {

		private boolean flag = false;

		public IndexCommitThread(String name) { // 参数为线程的名字
			super(name);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			flag = true;
			while (flag) {
				try {
					// 将内存索引提交到硬盘
					indexWriter.commit();
					System.out.println(new Date().toLocaleString() + "\t"
							+ ConfigBean.getIndexName() + "\tcommit");
					// 每隔一定的时间将内存索引提交到硬盘索引
					TimeUnit.SECONDS.sleep(ConfigBean.getIndexCommitSeconds());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			super.run();
		}
	}

	/**
	 * 获取索引的IndexManager对象
	 */
	public static IndexManager getIndexManager(String indexName) {
		return LazyIndexManager.indexManagerMap.get(indexName);
	}

	/**
	 * 获取最新可用的IndexSearcher
	 */
	public IndexSearcher getIndexSearcher() {
		try {
			return this.nrtManager.acquire();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将IndexSearcher返还给NRTManager
	 */
	public void release(IndexSearcher indexSearcher) {
		try {
			this.nrtManager.release(indexSearcher);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IndexWriter getIndexWriter() {
		return indexWriter;
	}

	public TrackingIndexWriter getTrackingIndexWriter() {
		return trackingIndexWriter;
	}

	public void commit() {
		try {
			indexWriter.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}
	
	

}