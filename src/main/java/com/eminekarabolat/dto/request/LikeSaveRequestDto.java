package com.eminekarabolat.dto.request;

public class LikeSaveRequestDto {
	private String username;
	private String videotitle;
	
	public LikeSaveRequestDto(String username, String videoname) {
		this.username = username;
		this.videotitle = videoname;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getVideoname() {
		return videotitle;
	}
	
	public void setVideoname(String videoname) {
		this.videotitle = videotitle;
	}
}