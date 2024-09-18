package com.eminekarabolat.service;

import com.eminekarabolat.dto.request.VideoSaveRequestDto;
import com.eminekarabolat.dto.request.VideoUpdateRequestDto;
import com.eminekarabolat.dto.response.VideoResponseDto;
import com.eminekarabolat.entity.User;
import com.eminekarabolat.entity.Video;
import com.eminekarabolat.model.VideoModel;
import com.eminekarabolat.repository.VideoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideoService {
	private final VideoRepository videoRepository;
	private final UserService userService;
	
	public VideoService() {
		this.videoRepository = new VideoRepository();
		this.userService = new UserService();
	}
	
	public Optional<VideoResponseDto> save(VideoSaveRequestDto dto) {
		Video video;
		Optional<Video> videoOptional;
		VideoResponseDto responseDto = new VideoResponseDto();
		try {
			Optional<User> userOptional = userService.findByUsername(dto.getUsername());
			if (userOptional.isPresent()) {
				video = new Video();
				video.setTitle(dto.getTitle());
				video.setDescription(dto.getDescription());
				video.setUserId(userOptional.get().getId());
				videoOptional = videoRepository.save(video);
				
				responseDto.setTitle(videoOptional.get().getTitle());
				responseDto.setDescription(videoOptional.get().getDescription());
				responseDto.setUsername(userService.findById(videoOptional.get().getUserId()).get().getUsername());
				
				System.out.println(videoOptional.get().getTitle() + "başarıyla kaydedildi.");
				return Optional.of(responseDto);
				
			}
			else {
				System.out.println("Video bulunamadı. Lutfen user id'sini kontrol edin.");
				return Optional.empty();
			}
		}
		catch (Exception e) {
			System.out.println("Service Video kaydedilirken hata oluştu: " + e.getMessage());
		}
		
		return Optional.ofNullable(responseDto);
	}
	
	public Optional<VideoResponseDto> update(VideoUpdateRequestDto dto) {
		VideoResponseDto responseDto = new VideoResponseDto();
		Optional<Video> byId = videoRepository.findById(dto.getVideoId());
		try {
			
			if (byId.isPresent()) {
				Video video = byId.get();
				video.setTitle(dto.getTitle());
				video.setDescription(dto.getDescription());
				
				Optional<Video> updateVideo = videoRepository.update(video);
				
					responseDto.setTitle(updateVideo.get().getTitle());
					responseDto.setDescription(updateVideo.get().getDescription());
					
					System.out.println(updateVideo.get().getTitle() + " başarıyla güncellendi.");
					
					
				}
				else {
					System.out.println("Service Güncellenmek istenen Video bulunamadı.");
					return Optional.empty();
				}
			
		}
		catch (Exception e) {
			System.out.println("Service Video güncellenirken hata oluştu: " + e.getMessage());
			
		}
		return Optional.of(responseDto);
	}
	
	
	public void delete(Long id) {
		Optional<Video> mevcutVideo = findById(id);
		if (mevcutVideo.isPresent()) {
			try {
				videoRepository.delete(id);
				System.out.println("Service Video başarıyla silindi.");
			}
			catch (Exception e) {
				System.out.println("Service Video silinirken hata oluştu: " + e.getMessage());
			}
		}
		else {
			System.out.println("Service Silinmek istenen Video bulunamadı.");
		}
	}
	
	
	public List<VideoResponseDto> findAll() {
		VideoResponseDto responseDto = new VideoResponseDto();
		List<Video> videoList = videoRepository.findAll();
		if (videoList.isEmpty()) {
			System.out.println("Service Veritabanında kayıtlı Video bulunmamaktadır.");
		}
		
		List<VideoResponseDto> responseDtoList = new ArrayList<>();
		for (Video video : videoList) {
			responseDto.setTitle(video.getTitle());
			responseDto.setDescription(video.getDescription());
			responseDto.setUsername(userService.findById(video.getUserId()).get().getUsername());
			responseDtoList.add(responseDto);
		}
		return responseDtoList;
	}
	
	
	public Optional<Video> findById(Long id) {
		Optional<Video> videoOptional = videoRepository.findById(id);
		videoOptional.ifPresentOrElse(video -> System.out.println("Service Video bulundu: " + video.getTitle()),
		                              () -> System.out.println("Service Böyle bir Video bulunamadı."));
		return videoOptional;
	}
	
	public Optional<Video> findByTitle(String videoTitle) {
		return videoRepository.findByTitle(videoTitle);
	}
	
	public List<Video>viewAllVideos(){
		return videoRepository.findAll();
	}
	public void incrementLikeCount(String videoTitle) {
		videoRepository.incrementLikeCount(videoTitle);
	}
	
	public void incrementCommentCount(String videoTitle){
		videoRepository.incrementCommentCount(videoTitle);
	}
	
	public void incrementDislikeCount(String videoTitle) {
	videoRepository.incrementDislikeCount(videoTitle);
	}
	

	
}