package com.entity.resp;

public class Music {

	private String Title; // 音乐名称
	private String Description; // 音乐描述
	private String MusicURL; // 音乐链接
	private String HQMusicUrl; // 高质量音乐链接，WIFI环境优先使用该链接播放音乐

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMusicURL() {
		return MusicURL;
	}

	public void setMusicURL(String musicURL) {
		MusicURL = musicURL;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

}
