package com.eminekarabolat.controller;

import com.eminekarabolat.dto.request.LikeSaveRequestDto;
import com.eminekarabolat.dto.request.LikeUpdateRequestDto;
import com.eminekarabolat.dto.response.LikeResponseDto;
import com.eminekarabolat.entity.Like;
import com.eminekarabolat.service.LikeService;

import java.util.List;
import java.util.Optional;

public class LikeController {
	private final LikeService likeService;
	
	public LikeController() {
		likeService = new LikeService();
	}
	
	public Optional<LikeResponseDto> save(LikeSaveRequestDto dto) {
		try {
			likeService.save(dto);
			System.out.println("Controller Like başarıyla kaydedildi.");
		} catch (Exception e) {
			System.out.println("Controller Like kaydedilirken hata oluştu: " + e.getMessage());
		}
		return Optional.empty();
	}
	
	public Optional<LikeResponseDto> update(LikeUpdateRequestDto dto) {
		try {
			likeService.update(dto);
			System.out.println("Controller Takım başarıyla güncellendi.");
		} catch (Exception e) {
			System.out.println("Controller Takım güncellenirken hata oluştu: " + e.getMessage());
		}
		return Optional.empty();
	}
	
	public void delete(Long id) {
		try {
			likeService.delete(id);
			System.out.println("Controller Like başarıyla silindi.");
		} catch (Exception e) {
			System.out.println("Controller Like silinirken hata oluştu: " + e.getMessage());
		}
	}
	
	public List<Like> findAll() {
		List<Like> likeList = likeService.findAll();
		if (likeList.isEmpty()) {
			System.out.println("Controller Veritabanında kayıtlı Like bulunmamaktadır.");
		}
		return likeList;
	}
	
	public Optional<Like> findById(Long id) {
		Optional<Like> likeOptional = likeService.findById(id);
		likeOptional.ifPresentOrElse(
				like -> System.out.println("Controller Like bulundu: " + like.getVideoId()),
				() -> System.out.println("Controller Böyle bir Like bulunamadı.")
		);
		return likeOptional;
	}
}