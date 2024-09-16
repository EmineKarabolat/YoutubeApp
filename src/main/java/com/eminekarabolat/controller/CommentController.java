package com.eminekarabolat.controller;

import com.eminekarabolat.dto.request.CommentSaveRequestDto;
import com.eminekarabolat.dto.request.CommentUpdateRequestDto;
import com.eminekarabolat.dto.response.CommentResponseDto;
import com.eminekarabolat.entity.Comment;
import com.eminekarabolat.service.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CommentController {
	static Scanner scanner = new Scanner(System.in);
	private final CommentService commentService;

	public CommentController() {
		this.commentService = new CommentService();
	}

	public Optional<CommentResponseDto> save(CommentSaveRequestDto dto) {
		try {
			commentService.save(dto);
			System.out.println("Controller Comment başarıyla kaydedildi.");
		} catch (Exception e) {
			System.out.println("Controller Comment kaydedilirken hata oluştu: " + e.getMessage());
		}
		return Optional.empty();
	}

	public Optional<CommentResponseDto> update(CommentUpdateRequestDto dto) {
		try {
			commentService.update(dto);
			System.out.println("Controller Comment başarıyla güncellendi.");
		} catch (Exception e) {
			System.out.println("Controller Comment güncellenirken hata oluştu: " + e.getMessage());
		}
		return Optional.empty();
	}

	public void delete(Long id) {
		try {
			commentService.delete(id);
			System.out.println("Controller Comment başarıyla silindi.");
		} catch (Exception e) {
			System.out.println("Controller Comment silinirken hata oluştu: " + e.getMessage());
		}
	}

	public List<Comment> findAll() {
		List<Comment> commentList = commentService.findAll();
		if (commentList.isEmpty()) {
			System.out.println("Controller Veritabanında kayıtlı Comment bulunmamaktadır.");
		}
		return commentList;
	}

	public Optional<Comment> findById(Long id) {
		Optional<Comment> commentOptional = commentService.findById(id);
		commentOptional.ifPresentOrElse(
				comment -> System.out.println("Controller Comment bulundu: " + comment.getVideoId()),
				() -> System.out.println("Controller Böyle bir Comment bulunamadı.")
		);
		return commentOptional;
	}
	
	public void removeComment() {
		System.out.println("Silmek istediğiniz yorumun video başlığını giriniz: ");
		String videoTitle = scanner.nextLine();
		String sonuc = commentService.yorumSil(videoTitle);
		System.out.println(sonuc);
	}
	
	public void addComment() {
		
		
		System.out.print("Yorum yapacağınız video başlıgınız giriniz: ");
		String videoTitle = scanner.nextLine();
		System.out.println("Yorum yapacağınız username giriniz: ");
		String username = scanner.nextLine();
		System.out.print("Yorumunuzu giriniz: ");
		String commentText = scanner.nextLine();
		
		String sonuc = commentService.yorumAt(videoTitle,username,commentText);
		System.out.println(sonuc);
	}
	
	public void editComment() {
		System.out.print("Düzenleyeceğiniz video başlığını girin: ");
		String videoTitle = scanner.nextLine();
		
		System.out.print("Eski yorumu girin: ");
		String oldCommentText = scanner.nextLine();
		
		System.out.print("Yeni yorumu girin: ");
		String newCommentText = scanner.nextLine();
		
		Optional<CommentResponseDto> result = commentService.editComment(videoTitle, oldCommentText, newCommentText);
		
		if (result.isPresent()) {
			CommentResponseDto commentResponse = result.get();
			System.out.println("Yorum başarıyla güncellendi.");
			System.out.println("Kullanıcı: " + commentResponse.getUsername());
			System.out.println("Video Başlığı: " + commentResponse.getVideotitle());
			System.out.println("Yeni Yorum: " + commentResponse.getCommentText());
		} else {
			System.out.println("Yorum güncellenirken bir sorun oluştu.");
		}
	}
	
}