package com.eminekarabolat.dto.request;

public class CommentUpdateRequestDto {
	private Long id;
	private Long userid;
	private Long videoid;
	private Integer status;
	private String commentText;
	
	
	public CommentUpdateRequestDto(Long id, Long userid, Long videoid, Integer status, String commentText) {
		this.id = id;
		this.userid = userid;
		this.videoid = videoid;
		this.status = status;
		this.commentText = commentText;
	}
	
	public Long getUserid() {
		return userid;
	}
	
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	public Long getVideoid() {
		return videoid;
	}
	
	public void setVideoid(Long videoid) {
		this.videoid = videoid;
	}
	
	public int getStatus() {
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
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}