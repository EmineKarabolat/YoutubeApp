package com.eminekarabolat.entity;

public class Like extends BaseEntity{
	private Long id;
	private Long userId;
	private Long videoId;
	private int status;
	
	
	
	public Like() {
	}
	
	public Like(Long id, Long userId, Long videoId) {
		this.id = id;
		this.userId = userId;
		this.videoId = videoId;
	}
	
	public Like(Long userId, Long videoId) {
		this.userId = userId;
		this.videoId = videoId;
	}
	
	public Like(Long id, Long userId, Long videoId,Integer state, Long createat, Long updateat) {
		super(state, createat, updateat);
		this.id = id;
		this.userId = userId;
		this.videoId = videoId;
	}
	
	public Like(Long id, Long userId, Long videoId, int status) {
		this.id = id;
		this.userId = userId;
		this.videoId = videoId;
		this.status = status;
	}
	
	public Like(Long id, Long userId, Long videoId, Integer status, Integer state, Long createat, Long updateat) {
		super(state, createat, updateat);
		this.id = id;
		this.userId = userId;
		this.videoId = videoId;
		this.status = status;
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getVideoId() {
		return videoId;
	}
	
	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Like{" + "id=" + getId() + ", userId=" + getUserId() + ", videoId=" + getVideoId() + ", status=" + getStatus() + ", state=" + getState() + ", createat=" + getCreateat() + ", updateat=" + getUpdateat() + '}';
	}
	
}