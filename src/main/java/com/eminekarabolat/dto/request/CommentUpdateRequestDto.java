package com.eminekarabolat.dto.request;

public class CommentUpdateRequestDto {
	
	private Long likeid;
	private Long userid;
	private Long videoid;
	private int status;
	
	public CommentUpdateRequestDto(Long likeid, Long userid, Long videoid, int status) {
		this.likeid = likeid;
		this.userid = userid;
		this.videoid = videoid;
		this.status = status;
	}
	
	public Long getLikeid() {
		return likeid;
	}
	
	public void setLikeid(Long likeid) {
		this.likeid = likeid;
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
	
	public void setStatus(int status) {
		this.status = status;
	}
}