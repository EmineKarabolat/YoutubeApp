package com.eminekarabolat.model;

import com.eminekarabolat.controller.CommentController;
import com.eminekarabolat.controller.LikeController;
import com.eminekarabolat.controller.UserController;
import com.eminekarabolat.controller.VideoController;
import com.eminekarabolat.entity.Comment;
import com.eminekarabolat.entity.User;
import com.eminekarabolat.entity.Video;

import java.util.List;

public class VideoModel {
	public CommentController commentController = new CommentController();
	public UserController userController = new UserController();
	
	private String username;
	private String title;
	private String description;
	private int viewCount;
	private int likeCount;
	private int commentCount;
	private int dislikeCount;
	private Long videoId;
	
	public VideoModel(User user, Video video) {
		this.username = user.getUsername();
		this.title = video.getTitle();
		this.description = video.getDescription();
		this.viewCount = video.getViewCount();
		this.likeCount = video.getLikeCount();
		this.commentCount = video.getCommentCount();
		this.dislikeCount = video.getDislikeCount();
		this.videoId = video.getId();
	}
	
	//ahmet neden böyle olduki
	private void printDescriptionWithoutBreakingWords(String description, int lineLength) {
		String[] words = description.split(" ");
		StringBuilder currentLine = new StringBuilder();
		
		for (String word : words) {
			if (currentLine.length() + word.length() + 1 <= lineLength) {
				if (currentLine.length() > 0) {
					currentLine.append(" ");
				}
				currentLine.append(word);
			} else {
				System.out.println(currentLine.toString());
				currentLine = new StringBuilder(word);
			}
		}
		
		if (currentLine.length() > 0) {
			System.out.println(currentLine.toString());
		}
	}
	
	public List<Comment> getCommentsForVideo(Long videoId) {
		return commentController.findByIdComment(videoId);
	}
	
	public void displayVideo() {
		System.out.println("*************************************************");
		for (int i = 0; i < 10; i++) {
			System.out.println("*\t\t\t\t\t\t\t\t\t\t\t\t*");
		}
		System.out.println("*************************************************");
		
		System.out.println("Başlık: " + title);
		System.out.println("Kullanıcı: " + username);
		System.out.printf("\uD83D\uDC4D:%d \uD83D\uDC4E:%d \t📺:%d \t💬:%d\n", likeCount, dislikeCount, viewCount, commentCount);
		System.out.println("-------------------------------------------------");
		
		printDescriptionWithoutBreakingWords("Acıklama: "+description, 50);
		System.out.println("-------------------------------------------------");
	
		List<Comment> comments = getCommentsForVideo(videoId);
		if (comments.isEmpty()) {
			System.out.println("Bu video için yorum bulunmamaktadır.");
		} else {
			comments.forEach(comment -> {
				User userCommented = userController.findById(comment.getUserId()).get();
				System.out.println(userCommented.getUsername() + ": " + comment.getCommentText());
			});
		}
		
		System.out.println();
	}
}