package com.eminekarabolat.controller;

import com.eminekarabolat.dto.request.CommentSaveRequestDto;
import com.eminekarabolat.dto.request.CommentUpdateRequestDto;
import com.eminekarabolat.dto.response.CommentResponseDto;
import com.eminekarabolat.entity.Comment;
import com.eminekarabolat.service.CommentService;

import java.util.List;
import java.util.Optional;

public class CommentController {
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
}