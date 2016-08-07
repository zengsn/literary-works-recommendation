package com.crawl.tianya.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.crawl.tianya.model.ChapterBean;
import com.crawl.tianya.model.CrawlListInfo;
import com.crawl.tianya.model.IntroPageBean;
import com.crawl.tianya.model.ReadPageBean;
import com.db.manager.DBServer;
import com.lucene.index.model.IntroIndexBean;
import com.lucene.index.operation.litera.IntroIndex;
import com.util.JsonUtil;
import com.util.ParseMD5;

/**
 * ���ɼ��������ݱ��浽���ݿ���
 * 
 * @author CJP
 * 
 */
public class TianYaDB {

	// ���ݿ����ӳصı���
	private static final String POOLNAME = "proxool.tianya";

	/*
	 * // ʹ��DBserverʵ�����ݿ����ģ�� 
	 * public void method() { 
	 * 	DBServer dbServer = new DBServer(POOLNAME); 
	 * 	try {
	 * 
	 * 	} catch (Exception e) { 
	 * 		e.printStackTrace(); 
	 * 	} finally {
	 * 		dbServer.close(); 
	 * 	} 
	 * }
	 */

	/**
	 * ��ȡcrawllist�������ݣ��������ݿ������Ƿ�����
	 * 
	 * @return
	 */
	public List<CrawlListInfo> getCrawlListInfos() {
		List<CrawlListInfo> infos = new ArrayList<CrawlListInfo>();
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select * from crawllist where `state` = '1'";
			ResultSet rs = dbServer.select(sql);
			while (rs.next()) {
				CrawlListInfo info = new CrawlListInfo();
				infos.add(info);
				info.setUrl(rs.getString("url"));
				info.setInfo(rs.getString("info"));
				info.setFrequency(rs.getInt("frequency"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return infos;
	}

	// ------------------- crawlintro�����ݴ洢���� ----------------------
	/**
	 * �����б�ҳ�ɼ����ļ��ҳurl���浽���ݿ���
	 */
	private void insertIntroUrl(String id, String url) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, id);
			params.put(i++, url);
			long now = System.currentTimeMillis();
			params.put(i++, now);
			params.put(i++, now);
			params.put(i++, 1);

			dbServer.insert("crawlintro", "id,url,createtime,updatetime,state",
					params);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	/**
	 * ����crawlintro����stateֵ
	 * 
	 * @param id
	 * @param state
	 */
	public void updateIntroState(String id, int state) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "update crawlintro set `state`='" + state
					+ "' where id = '" + id + "'";
			dbServer.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	/**
	 * �ж�crawlintro����ָ��id�Ƿ������ݴ���
	 * 
	 * @param id
	 * @return
	 */
	private boolean haveIntroInfo(String id) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select sum(1) as count from crawlintro where id='"
					+ id + "'";
			ResultSet rs = dbServer.select(sql);
			if (rs.next()) {
				int count = rs.getInt("count");
				return count > 0;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return true;
	}

	/**
	 * �����б�ҳ�ɼ����ļ��ҳurl���浽���ݿ���
	 * 
	 * @param urls
	 */
	public void saveInfoUrls(List<String> urls) {
		if (urls == null || urls.size() < 1) {
			return;
		}
		for (String url : urls) {
			String id = ParseMD5.parseStrToMD5(url);
			// �ж�crawlintro����ָ��id�Ƿ������ݴ���
			if (haveIntroInfo(id)) {
				// ���У��������stateֵ
				updateIntroState(id, 1);
			} else {
				// ���ޣ��������Ӧ������
				insertIntroUrl(id, url);
			}
		}
	}

	/**
	 * ����crawlintro���е���Ϣ
	 * 
	 * @param bean
	 */
	public void updateIntroInfo(IntroPageBean bean) {
		if (bean == null) {
			return;
		}
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, bean.getName());
			params.put(i++, bean.getAuthor());
			params.put(i++, bean.getDesc());
			params.put(i++, bean.getChapterCount());
			long now = System.currentTimeMillis();
			params.put(i++, now);
			params.put(i++, "0");

			String columns = "name,author,description,chaptercount,updatetime,state";
			String condition = "where id = '" + bean.getId() + "'";
			dbServer.update("crawlintro", columns, condition, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	/**
	 * �����ȡcrawlintro����һ����¼��url��Ϣ
	 * 
	 * @param state
	 * @return
	 */
	public String getIntroUrlRand(int state) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select * from crawlintro where state=" + state
					+ " order by rand() limit 1";
			// System.out.println(sql);
			ResultSet rs = dbServer.select(sql);
			while (rs.next()) {
				return rs.getString("url");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return null;
	}

	public String getIntroUrlRand() {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select * from crawlintro order by rand() limit 1";
			// System.out.println(sql);
			ResultSet rs = dbServer.select(sql);
			while (rs.next()) {
				return rs.getString("url");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return null;
	}

	// ------------------- crawlintro�����ݴ洢���� ----------------------

	// ----------------- crawlchapterlist�����ݴ洢���� -------------------

	/**
	 * ���ɼ������½���Ϣ���浽���ݿ���
	 * 
	 * @param bean
	 * @param literaId
	 */
	private void insertChapterInfo(ChapterBean bean, String literaId) {
		if (bean == null) {
			return;
		}
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, bean.getId());
			params.put(i++, literaId);
			params.put(i++, bean.getUrl());
			params.put(i++, bean.getTitle());
			params.put(i++, bean.getChapterId());
			long now = System.currentTimeMillis();
			params.put(i++, now);
			params.put(i++, "1");
			String columns = "id,literaid,url,title,chapterid,createtime,state";
			dbServer.insert("crawlchapterlist", columns, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	/**
	 * �ж�crawlchapterlist����ָ��id�Ƿ������ݴ���
	 * 
	 * @param id
	 * @return
	 */
	private boolean haveChapterInfo(String id) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select sum(1) as count from crawlchapterlist where id='"
					+ id + "'";
			ResultSet rs = dbServer.select(sql);
			if (rs.next()) {
				int count = rs.getInt("count");
				return count > 0;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return true;
	}

	/**
	 * ���ɼ������½���Ϣ���浽���ݿ���
	 * 
	 * @param beans
	 * @param novelId
	 */
	public void saveChapterInfo(List<ChapterBean> beans, String literaId) {
		if (beans == null) {
			return;
		}
		for (ChapterBean bean : beans) {
			// �ж�crawlchapterlist���������Ƿ��Ѿ�����
			if (!haveChapterInfo(bean.getId())) {
				insertChapterInfo(bean, literaId);
			}
		}
	}

	/**
	 * ����crawlchapterlist����stateֵ
	 * 
	 * @param id
	 * @param state
	 */
	public void updateChapterState(String id, int state) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "update crawlchapterlist set `state`='" + state
					+ "' where id = '" + id + "'";
			dbServer.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	/**
	 * �����ȡcrawlchapterlist���е���Ϣ
	 * 
	 * @param state
	 * @return
	 */
	public ChapterBean getChapterRand(int state) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select * from crawlchapterlist where state='" + state
					+ "' order by rand() limit 1";
			// System.out.println(sql);
			ResultSet rs = dbServer.select(sql);
			while (rs.next()) {
				ChapterBean bean = new ChapterBean();
				bean.setId(rs.getString("id"));
				bean.setUrl(rs.getString("url"));
				bean.setChapterId(rs.getInt("chapterid"));
				return bean;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return null;
	}

	// ----------------- crawlchapterlist�����ݴ洢���� -------------------

	// ---------------- crawlchapterdetail�����ݴ洢���� ------------------

	/**
	 * ���ɼ������Ķ�ҳ��Ϣ���浽���ݿ���
	 * 
	 * @param bean
	 */
	public void saveReadInfo(ReadPageBean bean) {
		if (bean == null) {
			return;
		}
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			if (haveReadInfo(bean.getId())) {
				return;
			}
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, bean.getId());
			params.put(i++, bean.getUrl());
			params.put(i++, bean.getTitle());
			params.put(i++, bean.getChapterId());
			params.put(i++, bean.getContent());
			long now = System.currentTimeMillis();
			params.put(i++, now);
			params.put(i++, now);
			String columns = "id,url,title,chapterid,content,createtime,updatetime";
			dbServer.insert("crawlchapterdetail", columns, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	/**
	 * �ж�crawlchapterdetail����ָ��id�Ƿ������ݴ���
	 * 
	 * @param id
	 * @return
	 */
	private boolean haveReadInfo(String id) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select sum(1) as count from crawlchapterdetail where id='"
					+ id + "'";
			ResultSet rs = dbServer.select(sql);
			if (rs.next()) {
				int count = rs.getInt("count");
				return count > 0;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return true;
	}

	/**
	 * ��ȡ���ݿ��м����е����ݣ����䵼��������
	 */
	public void dataImportToIndex() {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "SELECT * FROM crawlintro";
			ResultSet rs = dbServer.select(sql);
			while (rs.next()) {
				IntroIndexBean bean = new IntroIndexBean();
				bean.setId(rs.getString("id"));
				bean.setName(rs.getString("name"));
				bean.setAuthor(rs.getString("author"));
				bean.setDesc(rs.getString("description"));
				bean.setChapterCount(Integer.parseInt(rs
						.getString("chaptercount")));
				new IntroIndex().addIntroIndex(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	// ---------------- crawlchapterdetail�����ݴ洢���� ------------------

	public static void main(String[] args) {

		TianYaDB db = new TianYaDB();
		// System.out.println(JsonUtil.parseJson(db.getCrawlListInfos()));
		// System.out.println(db.getIntroUrlRand(1));
		System.out.println(JsonUtil.parseJson(db.getChapterRand(1)));
	}
}
