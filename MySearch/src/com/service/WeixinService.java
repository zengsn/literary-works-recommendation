package com.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.crawl.DoubanCrawl;
import com.dao.LuceneDao;
import com.entity.Para;
import com.entity.SearchResult;
import com.entity.resp.TextMessage;
import com.util.MessageUtils;
import com.util.StringUtils;

public class WeixinService {

	private LuceneDao dao = new LuceneDao();
	private DoubanCrawl crawl = new DoubanCrawl();
	private Map<String, String> userRequest = new HashMap<String, String>();
	private int start = 0;
	private int end = 1;
	private String[] hotTopic;

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "";
			// xml请求解析
			Map<String, String> requestMap = MessageUtils.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// System.out.println(fromUserName);
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);
			// 文本消息
			if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_TEXT)) {
				String keywords = requestMap.get("Content");
				// System.out.println(keywords);
				if ("热门话题".equals(keywords)) {
					hotTopic = crawl.getHotTopic();
					String hotTopicStr = StringUtils.arr2Str(hotTopic);
					keywords = hotTopicStr;
					start = 0;
					end = 1;
					userRequest.remove(fromUserName);
				} else {
					hotTopic = null;
					if (!userRequest.containsKey(fromUserName)) {
						userRequest.put(fromUserName, keywords);
					} else {
						if (!keywords.equals(userRequest.get(fromUserName))) {
							userRequest.put(fromUserName, keywords);
							start = 0;
							end = 1;
						} else {
							start ++;
							end ++;
						}
					}
				}
				SearchResult sr = dao.searchIndex(keywords, start, end, false);
				int count = sr.getCount();
				if (count == 0) {
					respContent = "没有符合您需求的名著文段";
				} else {
					if (hotTopic != null) {
						respContent += ("本期的热门关键字为：" + hotTopic[0] + "，"
								+ hotTopic[1] + "，" + hotTopic[2]
								+ "。为您推荐的名著文段如下：" + "\n");
					}
					for (Para p : sr.getParaList()) {
						respContent += ("文段：" + p.getPara() + "\n" 
								+ p.getAuthor() + "\n" + "来源：" + p.getName()
								+ " " + p.getTitle())
								+ "\n\n";
					}
					if (hotTopic == null) {
						respContent += "一共搜索到" + count + "条名著文段，重复输入关键字可查看其他结果";
					}
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "请输入搜索的关键字！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "请输入搜索的关键字！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "请输入搜索的关键字！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "请输入搜索的关键字！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtils.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "本公众号提供如下服务：\n1.输入关键字获取符合需求的名著文段；\n2.输入“热门话题”获取与热门话题相关的名著文段；";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtils.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtils.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
				}
			}

			textMessage.setContent(respContent);
			respMessage = MessageUtils.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
}
