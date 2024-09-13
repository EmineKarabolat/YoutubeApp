package com.eminekarabolat.dto.request;

public class VideoUpdateRequestDto {
	private Long username;
	private String title;
	private String description;
	
	public VideoUpdateRequestDto(Long username, String title, String description) {
		this.username = username;
		this.title = title;
		this.description = description;
	}
	
	public Long getUsername() {
		return username;
	}
	
	public void setUsername(Long username) {
		this.username = username;
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