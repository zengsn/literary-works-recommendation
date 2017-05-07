package com.entity.req;

/**
 * 文本消息
 * 
 * @author Administrator
 *
 */
public class TextMessage extends BaseMessage {

	private String Content; // 消息内容

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
