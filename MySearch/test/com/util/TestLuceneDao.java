package com.util;

import java.util.List;

import org.junit.Test;

import com.dao.BaseDao;
import com.dao.LuceneDao;

public class TestLuceneDao {

	/**
	 * 创建索引
	 */
	@Test
	public void test01() {
		BaseDao baseDao = new BaseDao();
		LuceneDao luceneDao = new LuceneDao();
		List<Object> chapterList = baseDao.findByCondition("Chapter", null);
		luceneDao.createIndex(chapterList);
	}
}
