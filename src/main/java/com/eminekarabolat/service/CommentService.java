package com.eminekarabolat.service;

import com.eminekarabolat.dto.request.CommentSaveRequestDto;
import com.eminekarabolat.dto.request.CommentUpdateRequestDto;
import com.eminekarabolat.dto.response.CommentResponseDto;
import com.eminekarabolat.entity.Comment;
import com.eminekarabolat.entity.User;
import com.eminekarabolat.entity.Video;
import com.eminekarabolat.repository.CommentRepository;


import java.util.List;
import java.util.Optional;

public class CommentService {
	private final CommentRepository commentRepository;
	private final UserService userService;
	private final VideoService videoService;
	
	public CommentService() {
		this.commentRepository = new CommentRepository();
		this.userService = new UserService();
		this.videoService = new VideoService();
	}
	
	
	public Optional<CommentResponseDto> save(CommentSaveRequestDto dto) {
		Comment comment = new Comment();
		Optional<Comment> commentOptional;
		CommentResponseDto commentResponseDto = new CommentResponseDto();
		try {
			Optional<User> userOptional = userService.findByUsername(dto.getUsername());
			Optional<Video> videoOptional = videoService.findByTitle(dto.getVideotitle());
			if (userOptional.isPresent() && videoOptional.isPresent()) {
				comment.setUserId(userOptional.get().getId());
				comment.setVideoId(videoOptional.get().getId());
				comment.setCommentText(dto.getCommentText());
				
				commentOptional = commentRepository.save(comment);
				
				commentResponseDto.setUsername(userService.findById(commentOptional.get().getUserId()).get()
				                                          .getUsername());
				commentResponseDto.setVideotitle(videoService.findById(commentOptional.get().getVideoId()).get()
				                                             .getTitle());
				
				
				System.out.println(comment.getVideoId() + " başarıyla kaydedildi.");
				return Optional.of(commentResponseDto);
			}
			else {
				System.out.println("Kullanıcı bulunamadı.");
				return Optional.empty();
			}
			
			
		}
		catch (Exception e) {
			System.out.println("Service Comment kaydedilirken hata oluştu: " + e.getMessage());
		}
		
		return Optional.ofNullable(commentResponseDto);
	}
	
	
	public Optional<CommentResponseDto> update(CommentUpdateRequestDto dto) {
		CommentResponseDto commentResponseDto = new CommentResponseDto();
		Optional<Comment> byId = commentRepository.findById(dto.getId());
		try {
			if (byId.isPresent()) {
				Comment comment = byId.get();
				comment.setUserId(userService.findById(comment.getUserId()).get().getId());
				comment.setVideoId(videoService.findById(comment.getVideoId()).get().getId());
				comment.setCommentText(comment.getCommentText());
				
				if (dto.getStatus() == 0) {
					comment.setStatus(0);
					System.out.println("Comment geri çekildi.");
				}
				else if (dto.getStatus() == 1) {
					comment.setStatus(1);
					System.out.println("Comment atıldı.");
				}
				
				else if (dto.getStatus() == 2) {
					comment.setStatus(2);
					System.out.println("Comment soft delete atıldı.");
				}
				else {
					System.out.println("Geçersiz işlem.");
					return Optional.empty();
				}
				
				commentRepository.update(comment);
				
				commentResponseDto.setUsername(userService.findById(dto.getUserid()).get().getUsername());
				commentResponseDto.setVideotitle(videoService.findById(comment.getVideoId()).get().getTitle());
				
				System.out.println(comment.getVideoId() + " başarıyla güncellendi.");
				
			}
			else {
				System.out.println("Service Güncellenmek istenen Comment bulunamadı.");
			}
		}
		catch (Exception e) {
			System.out.println("Service Comment güncellenirken hata oluştu: " + e.getMessage());
		}
		return Optional.empty();
	}
	
	
	public void delete(Long id) {
		Optional<Comment> mevcutComment = findById(id);
		if (mevcutComment.isPresent()) {
			try {
				commentRepository.delete(id);
				System.out.println("Service Comment başarıyla silindi.");
			}
			catch (Exception e) {
				System.out.println("Service Comment silinirken hata oluştu: " + e.getMessage());
			}
		}
		else {
			System.out.println("Service Silinmek istenen Comment bulunamadı.");
		}
	}
	
	
	public List<Comment> findAll() {
		List<Comment> commentList = commentRepository.findAll();
		if (commentList.isEmpty()) {
			System.out.println("Service Veritabanında kayıtlı Comment bulunmamaktadır.");
		}
		return commentList;
	}
	
	
	public Optional<Comment> findById(Long id) {
		Optional<Comment> commentOptional = commentRepository.findById(id);
		commentOptional.ifPresentOrElse(comment -> System.out.println("Service Comment bulundu: " + comment.getVideoId()), () -> System.out.println("Service Böyle bir Comment bulunamadı."));
		return commentOptional;
	}
	
	
	public String yorumAt(String videoTitle, String username, String commentText) {
		// Video'yu başlığa göre bul
		Optional<Video> videoOpt = videoService.findByTitle(videoTitle);
		if (!videoOpt.isPresent()) {
			return "Video başlığı ile video bulunamadı.";
		}
		Video video = videoOpt.get();
		
		// Kullanıcıyı kullanıcı adına göre bul
		Optional<User> userOpt = userService.findByUsername(username);
		if (!userOpt.isPresent()) {
			return "Kullanıcı adı ile kullanıcı bulunamadı.";
		}
		User user = userOpt.get();
		
		// Kullanıcı ID'sinin null olmadığını kontrol edin
		Long userId = user.getId();
		if (userId == null) {
			return "Kullanıcı ID'si geçersiz.";
		}
		
		// Yeni bir yorum oluştur
		Comment newComment = new Comment();
		newComment.setVideoId(video.getId());
		newComment.setUserId(userId); // Kullanıcı ID'sini ayarla
		newComment.setCommentText(commentText);
		
		System.out.println("Yorum Metni: " + newComment.getCommentText());
		
		// Yorum kaydet
		commentRepository.save(newComment);
		
		
		return "Yorum başarıyla eklendi.";
	}
	
	public Optional<CommentResponseDto> editComment(String videoTitle, String oldCommentText, String newCommentText) {
		Optional<Video> videoOpt = videoService.findByTitle(videoTitle);
		
		if (videoOpt.isPresent()) {
			Video video = videoOpt.get();
			List<Comment> comments = commentRepository.findByVideoId(video.getId());
			
			// Eski yorumu bul
			Comment comment = comments.stream()
			                          .filter(c -> c.getCommentText().equals(oldCommentText))
			                          .findFirst()
			                          .orElse(null);
			
			if (comment != null) {
				// Status kontrolü
				if (comment.getStatus() == null) {
					comment.setStatus(0); // Varsayılan bir değer atayın
				}
				
				// Güncelleme yap
				comment.setCommentText(newCommentText);
				commentRepository.update(comment);
				
				CommentResponseDto commentResponseDto = new CommentResponseDto();
				commentResponseDto.setUsername(userService.findById(comment.getUserId()).get().getUsername());
				commentResponseDto.setVideotitle(videoService.findById(comment.getVideoId()).get().getTitle());
				commentResponseDto.setCommentText(comment.getCommentText());
				
				return Optional.of(commentResponseDto);
			} else {
				System.out.println("Bu video için eski yorum bulunamadı.");
				return Optional.empty();
			}
		} else {
			System.out.println("Video başlığı ile video bulunamadı.");
			return Optional.empty();
		}
	}
	
	public String yorumSil(String videoTitle) {
		Optional<Video> videoOpt = videoService.findByTitle(videoTitle);
		if (videoOpt.isPresent()) {
			Video video = videoOpt.get();
			Optional<Comment> commentOpt = commentRepository.findById(video.getId());
			if (commentOpt.isPresent()) {
				Comment comment = commentOpt.get();
				commentRepository.delete(comment.getId());
				return "Yorum başarıyla kaldırıldı.";
			}else {
				System.out.println("Bu video için yorum bulunamadı");
			}
			
		}
		return "Video başlığı ile eşleşen video bulunamadı.";
	}
}