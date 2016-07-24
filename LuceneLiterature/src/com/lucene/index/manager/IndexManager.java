package com.lucene.index.manager;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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

import com.lucene.index.model.IndexConfig;
import com.lucene.index.model.IndexConfigBean;

/**
 * 实时索引管理类，保存索引的相关信息，需要是单例模式
 * 
 * @author CJP
 * 
 */
public class IndexManager {

	private IndexWriter indexWriter;
	private TrackingIndexWriter trackingIndexWriter;
	//private Analyzer analyzer;
	private NRTManager nrtManager;
	/**
	 * 内存索引的重读线程
	 */
	private NRTManagerReopenThread nrtManagerReopenThread;
	/**
	 * 内存索引写入到硬盘索引线程
	 */
	private IndexCommitThread indexCommitThread;
	private IndexConfigBean indexConfigBean;

	private IndexManager(IndexConfigBean indexConfigBean) {

		// 索引文件保存的完整路径：文件路径+文件名
		String indexFile = indexConfigBean.getIndexPath() + "/"
				+ indexConfigBean.getIndexName();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				Version.LUCENE_43, indexConfigBean.getAnalyzer());
		//analyzer = indexConfigBean.getAnalyzer();
		// 指定索引的打开方式，没有就新建，有就追加
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		this.indexConfigBean = indexConfigBean;
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File(indexFile));
			// 判断此路径上的索引是否被锁定，锁定则解锁
			if (IndexWriter.isLocked(directory)) {
				IndexWriter.unlock(directory);
			}
			this.indexWriter = new IndexWriter(directory, indexWriterConfig);
			// 将indexWriter委托给trackingIndexWriter
			this.trackingIndexWriter = new TrackingIndexWriter(indexWriter);
			this.nrtManager = new NRTManager(trackingIndexWriter,
					new SearcherFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 开启系统的守护线程
		setThread();
	}

	private static class LazyIndexManager {
		// 保存系统中的IndexManager对象
		private static HashMap<String, IndexManager> indexManagerMap = new HashMap<String, IndexManager>();

		static {
			for (IndexConfigBean bean : IndexConfig.getIndexConfig()) {
				indexManagerMap
						.put(bean.getIndexName(), new IndexManager(bean));
			}
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

	/**
	 * 设置IndexSearcher守护线程
	 */
	private void setThread() {
		// 内存索引重读线程
		this.nrtManagerReopenThread = new NRTManagerReopenThread(nrtManager,
				indexConfigBean.getIndexReopenMaxStaleSec(),
				indexConfigBean.getIndexReopenMinStaleSec());
		this.nrtManagerReopenThread.setName("NRTManager reopen thread");// 线程名
		this.nrtManagerReopenThread.setDaemon(true);
		this.nrtManagerReopenThread.start();

		// 内存索引写入到硬盘索引线程
		this.indexCommitThread = new IndexCommitThread(
				indexConfigBean.getIndexName() + " index commmit thread");
		this.indexCommitThread.setDaemon(true);
		this.indexCommitThread.start();
	}

	/**
	 * 内存索引写入到硬盘索引线程
	 */
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
							+ indexConfigBean.getIndexName() + "\tcommit");
					TimeUnit.SECONDS.sleep(indexConfigBean
							.getIndexCommitSeconds());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			super.run();
		}
	}

}