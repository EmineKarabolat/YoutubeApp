package com.eminekarabolat.service;

import com.eminekarabolat.dto.request.LikeSaveRequestDto;
import com.eminekarabolat.dto.request.LikeUpdateRequestDto;
import com.eminekarabolat.dto.response.LikeResponseDto;
import com.eminekarabolat.entity.Like;
import com.eminekarabolat.entity.User;
import com.eminekarabolat.entity.Video;
import com.eminekarabolat.repository.LikeRepository;

import java.util.List;
import java.util.Optional;

public class LikeService {
	private final LikeRepository likeRepository;
	private final UserService userService;
	private final VideoService videoService;
	
	public LikeService() {
		this.likeRepository = new LikeRepository();
		this.userService = new UserService();
		this.videoService = new VideoService();
	}
	
	public Optional<LikeResponseDto> save(LikeSaveRequestDto dto) {
		Like like;
		Optional<Like> likeOptional;
		LikeResponseDto likeResponseDto = new LikeResponseDto();
		try {
			Optional<User> userOptional = userService.findByUsername(dto.getUsername());
			Optional<Video> videoOptional = videoService.findByTitle(dto.getVideotitle());
			if (userOptional.isPresent() && videoOptional.isPresent()) {
				like = new Like();
				like.setUserId(userOptional.get().getId());
				like.setVideoId(videoOptional.get().getId());
				
				likeOptional = likeRepository.save(like);
				
				likeResponseDto.setUsername(userService.findById(likeOptional.get().getUserId()).get().getUsername());
				likeResponseDto.setVideotitle(videoService.findById(likeOptional.get().getVideoId()).get().getTitle());
				
				System.out.println(like.getVideoId()+ " başarıyla kaydedildi.");
				return Optional.of(likeResponseDto);
				
			}
			else{
				System.out.println("Video bulunamadı. Lutfen user id'sini kontrol edin.");
				return Optional.empty();
			}
			
		}
		catch (Exception e) {
			System.out.println("Service Like kaydedilirken hata oluştu: " + e.getMessage());
		}
		
		return Optional.ofNullable(likeResponseDto);
	}
	
	
	public Optional<LikeResponseDto> update(LikeUpdateRequestDto dto) {
		LikeResponseDto responseDto = new LikeResponseDto();
		Optional<Like>  byId = likeRepository.findById(dto.getLikeid());
		
			try {
				if (byId.isPresent()) {
					Like like = byId.get();
					like.setUserId(userService.findById(like.getUserId()).get().getId());
					like.setVideoId(videoService.findById(like.getVideoId()).get().getId());
					
					
					
					if (dto.getStatus()==0){
						like.setStatus(0);
						System.out.println("Like geri çekildi.");
					}
					else if (dto.getStatus()==1) {
						like.setStatus(1);
						System.out.println("Like atıldı.");
					}
					else if (dto.getStatus()==2) {
						like.setStatus(2);
						System.out.println("Dislike atıldı.");
					}
					else if (dto.getStatus()==3) {
						like.setStatus(3);
						System.out.println("Like soft delete atıldı.");
					}
					else {
						System.out.println("Geçersiz işlem.");
						return Optional.empty();
					}
					likeRepository.update(like);
					
					responseDto.setUsername(userService.findById(dto.getUserid()).get().getUsername());
					responseDto.setVideotitle(videoService.findById(dto.getVideoid()).get().getTitle());
					
					
					System.out.println(like.getVideoId() + " başarıyla güncellendi.");
				}
				else {
					System.out.println("Service Güncellenmek istenen Like bulunamadı.");
				}
			}
			catch (Exception e) {
				System.out.println("Service Like güncellenirken hata oluştu: " + e.getMessage());
			}
		
		
		return Optional.empty();
	}
	

	
	
	public void delete(Long id) {
		Optional<Like> mevcutLike = findById(id);
		if (mevcutLike.isPresent()) {
			try {
				likeRepository.delete(id);
				System.out.println("Service Like başarıyla silindi.");
			}
			catch (Exception e) {
				System.out.println("Service Like silinirken hata oluştu: " + e.getMessage());
			}
		}
		else {
			System.out.println("Service Silinmek istenen Like bulunamadı.");
		}
	}
	
	
	public List<Like> findAll() {
		List<Like> likeList = likeRepository.findAll();
		if (likeList.isEmpty()) {
			System.out.println("Service Veritabanında kayıtlı like bulunmamaktadır.");
		}
		return likeList;
	}
	
	
	public Optional<Like> findById(Long id) {
		Optional<Like> likeOptional = likeRepository.findById(id);
		likeOptional.ifPresentOrElse(l -> System.out.println("Service Like bulundu: " + l.getVideoId()),
		                             () -> System.out.println("Service Böyle bir like bulunamadı."));
		return likeOptional;
	}
}