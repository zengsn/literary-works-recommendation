package com.crawl;

import java.util.List;

import com.dao.BaseDao;
import com.entity.ContentsPage;
import com.entity.IntroducePage;
import com.util.ParseMD5;

public class IntroducePageCrawlThread extends Thread {

	private BaseDao dao = new BaseDao();

	@Override
	public void run() {
		while (true) {
			String url = dao.getUrlByRand("listPage", "0");
			if(url.equals("")||url==null){
				return;
			}
			IntroducePageCrawl ipCrawl = new IntroducePageCrawl(url);
			IntroducePage introducePage = ipCrawl.getIntroduce();
			if (introducePage == null) {
				// 如果返回null，表示当前页的格式不符合标准
				dao.deleteById("listPage", ParseMD5.parseStrToMD5(url));
			} else {
				List<ContentsPage> cpList = ipCrawl.getContents();
				introducePage.setChaptercount(cpList.size());
				dao.add(introducePage);
				for (ContentsPage cp : cpList) {
					dao.add(cp);
				}
				System.out.println(introducePage.getName() + "-->" + "插入成功");
				dao.updateById("listPage", "state", "1",
						ParseMD5.parseStrToMD5(url));
			}
		}
	}

	public static void main(String[] args) {
		IntroducePageCrawlThread thread = new IntroducePageCrawlThread();
		thread.start();
	}
}
