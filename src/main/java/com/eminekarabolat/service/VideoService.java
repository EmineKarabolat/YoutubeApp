package com.eminekarabolat.service;

import com.eminekarabolat.dto.request.VideoSaveRequestDto;
import com.eminekarabolat.dto.response.VideoResponseDto;
import com.eminekarabolat.entity.User;
import com.eminekarabolat.entity.Video;
import com.eminekarabolat.repository.VideoRepository;

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
			Optional<User> userOptional=userService.findByUsername(dto.getUsername());
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
				
			} else {
				System.out.println("Video bulunamadı. Lutfen user id'sini kontrol edin.");
				return Optional.empty();
			}
		} catch (Exception e) {
			System.out.println("Service Video kaydedilirken hata oluştu: " + e.getMessage());
		}
		
		return Optional.ofNullable(responseDto);
	}
	
	
	public Optional<VideoResponseDto> update(VideoSaveRequestDto dto) {
		Video video;
		Optional<Video>  videoOptional;
		VideoResponseDto videoResponseDto = new VideoResponseDto();
		
			try {
				Optional<User> userOptional = use
				if (videoOptional.isPresent())
					videoRepository.update(video);
				System.out.println(video.getTitle() + " başarıyla güncellendi.");
			} catch (Exception e) {
				System.out.println("Service Video güncellenirken hata oluştu: " + e.getMessage());
			}
		else {
			System.out.println("Service Güncellenmek istenen Video bulunamadı.");
		}
		return Optional.of(video);
	}
	
	
	public void delete(Long id) {
		Optional<Video> mevcutVideo = findById(id);
		if (mevcutVideo.isPresent()) {
			try {
				videoRepository.delete(id);
				System.out.println("Service Video başarıyla silindi.");
			} catch (Exception e) {
				System.out.println("Service Video silinirken hata oluştu: " + e.getMessage());
			}
		} else {
			System.out.println("Service Silinmek istenen Video bulunamadı.");
		}
	}
	
	
	public List<Video> findAll() {
		List<Video> videoList = videoRepository.findAll();
		if (videoList.isEmpty()) {
			System.out.println("Service Veritabanında kayıtlı Video bulunmamaktadır.");
		}
		return videoList;
	}
	
	
	public Optional<Video> findById(Long id) {
		Optional<Video> videoOptional = videoRepository.findById(id);
		videoOptional.ifPresentOrElse(
				video -> System.out.println("Service Video bulundu: " + video.getTitle()),
				() -> System.out.println("Service Böyle bir Video bulunamadı.")
		);
		return videoOptional;
	}
	
	public Optional<Video> findByTitle(String videoTitle) {
		return videoRepository.findByTitle(videoTitle);
	}
}