package com.eminekarabolat.dto.request;

public class VideoUpdateRequestDto {
	private Long videoId;
	private String title;
	private String description;
	
	public VideoUpdateRequestDto(Long videoId, String title, String description) {
		this.videoId = videoId;
		this.title = title;
		this.description = description;
	}
	
	public Long getVideoId() {
		return videoId;
	}
	
	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}