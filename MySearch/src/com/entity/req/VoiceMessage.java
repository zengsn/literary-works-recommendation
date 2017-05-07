package com.entity.req;

/**
 * 音频消息
 * 
 * @author Administrator
 *
 */
public class VoiceMessage extends BaseMessage {

	private String mediaId; // 媒体ID
	private String format; // 语音格式

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
