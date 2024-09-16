package com.eminekarabolat.entity;

public class Comment extends BaseEntity{
	private Long id;
	private Long userId;
	private Long videoId;
	private Integer status;
	private String commentText;
	
	
	public Comment() {
	}
	
	public Comment(Long id, Long userId, Long videoId, Integer status, String commentText, Integer state, Long createat,
	               Long updateat ){
		super(state, createat, updateat);
		this.id = id;
		this.userId = userId;
		this.videoId = videoId;
		this.status = status;
		this.commentText = commentText;
	}
	
	public Comment(Integer state, Long createat, Long updateat, Long userId, Long videoId, Integer status, String commentText) {
		super(state, createat, updateat);
		this.userId = userId;
		this.videoId = videoId;
		this.status = status;
		this.commentText = commentText;
	}
	
	public Comment(Integer state, Long createat, Long updateat, Long id, Long userId, Long videoId, Integer status, String commentText) {
		super(state, createat, updateat);
		this.id = id;
		this.userId = userId;
		this.videoId = videoId;
		this.status = status;
		this.commentText = commentText;
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
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getCommentText() {
		return commentText;
	}
	
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	@Override
	public String toString() {
		return "Comment{" + "id=" + getId() + ", userId=" + getUserId() + ", videoId=" + getVideoId() + ", status=" + getStatus() + ", commentText='" + getCommentText() + '\'' + ", state=" + getState() + ", createat=" + getCreateat() + ", updateat=" + getUpdateat() + '}';
	}
}