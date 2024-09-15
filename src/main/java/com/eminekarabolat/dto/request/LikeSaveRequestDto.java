package com.eminekarabolat.dto.request;

public class LikeSaveRequestDto {
	private String username;
	private String videotitle;
	
	public LikeSaveRequestDto(String username, String videotitle) {
		this.username = username;
		this.videotitle = videotitle;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getVideotitle() {
		return videotitle;
	}
	
	public void setVideotitle(String videotitle) {
		this.videotitle = videotitle;
	}
}