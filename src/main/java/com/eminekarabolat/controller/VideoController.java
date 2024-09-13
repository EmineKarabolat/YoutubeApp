package com.eminekarabolat.controller;

import com.eminekarabolat.dto.request.UserSaveRequestDto;
import com.eminekarabolat.dto.request.VideoSaveRequestDto;
import com.eminekarabolat.dto.request.VideoUpdateRequestDto;
import com.eminekarabolat.dto.response.UserResponseDto;
import com.eminekarabolat.dto.response.VideoResponseDto;
import com.eminekarabolat.entity.Video;
import com.eminekarabolat.service.VideoService;

import java.util.List;
import java.util.Optional;

public class VideoController {
	
	private final VideoService videoService;
	
	public VideoController() {
		this.videoService = new VideoService();
	}
	
	public Optional<VideoResponseDto> save(VideoSaveRequestDto dto) {
		try {
			
			System.out.println("Controller Video başarıyla kaydedildi.");
			return  videoService.save(dto);
			
		} catch (Exception e) {
			System.out.println("Controller Video kaydedilirken hata oluştu: " + e.getMessage());
		}
		return Optional.empty();
	}
	
	public Optional<VideoResponseDto> update(VideoUpdateRequestDto dto) {
		try {
			System.out.println("Controller Video başarıyla güncellendi.");
			return videoService.update(dto);
			
		} catch (Exception e) {
			System.out.println("Controller Video güncellenirken hata oluştu: " + e.getMessage());
		}
		return Optional.empty();
	}
	
	public void delete(Long id) {
		try {
			videoService.delete(id);
			System.out.println("Controller Video başarıyla silindi.");
		} catch (Exception e) {
			System.out.println("Controller Video silinirken hata oluştu: " + e.getMessage());
		}
	}
	
	public List<VideoResponseDto> findAll() {
		List<VideoResponseDto> videoList = videoService.findAll();
		if (videoList.isEmpty()) {
			System.out.println("Controller Veritabanında kayıtlı Video bulunmamaktadır.");
		}
		return videoList;
	}
	
	public Optional<Video> findById(Long id) {
		Optional<Video> videoOptional = videoService.findById(id);
		videoOptional.ifPresentOrElse(
				video -> System.out.println("Controller Takım bulundu: " + video.getTitle()),
				() -> System.out.println("Controller Böyle bir takım bulunamadı.")
		);
		return videoOptional;
	}
}