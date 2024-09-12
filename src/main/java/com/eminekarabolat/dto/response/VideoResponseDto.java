package com.eminekarabolat.dto.response;

public class VideoResponseDto {
	private String username;
	private String title;
	private String description;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
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
	
	@Override
	public String toString() {
		return "VideoResponseDto{" + "username='" + getUsername() + '\'' + ", title='" + getTitle() + '\'' + ", description='" + getDescription() + '\'' + '}';
	}
}