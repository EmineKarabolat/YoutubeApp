package com.eminekarabolat.controller;


import com.eminekarabolat.dto.request.VideoSaveRequestDto;
import com.eminekarabolat.dto.request.VideoUpdateRequestDto;
import com.eminekarabolat.dto.response.VideoResponseDto;
import com.eminekarabolat.entity.User;
import com.eminekarabolat.entity.Video;
import com.eminekarabolat.gui.UserGui;
import com.eminekarabolat.model.UserModel;
import com.eminekarabolat.model.VideoModel;
import com.eminekarabolat.repository.VideoRepository;
import com.eminekarabolat.service.VideoService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class VideoController {
	static Scanner scanner = new Scanner(System.in);
	private final VideoRepository videoRepository;
	private final VideoService videoService;
	private final UserController userController;
	
	
	public VideoController() {
		this.videoService = new VideoService();
		this.videoRepository = new VideoRepository();
		this.userController = new UserController();
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
	public void kullanicilariListele() {
		List<User> users = new UserController().getAllUsers();
		if (users.isEmpty()) {
			System.out.println("Hiç kullanıcı bulunmuyor.");
		} else {
			System.out.println("Kullanıcı Listesi:");
			for (User user : users) {
				UserModel userModel=new UserModel(user);
				userModel.displayUserInfo();
			}
		}
	}
	public void viewAllVideos() {
		List<Video> videoList = videoRepository.findAll();
		
		if (videoList.isEmpty()) {
			System.out.println("Hiç Video bulunmamaktadır.");
		} else {
			for (Video video : videoList) {
				Optional<User> userOpt = userController.findById(video.getUserId());
				if (userOpt.isPresent()) {
					User user = userOpt.get();
					
					VideoModel videoModel = new VideoModel(user, video);
					videoModel.displayVideo();
					
				} else {
					System.out.println("Video kullanıcısı bulunamadı: " + video.getTitle());
				}
				System.out.println("-----------------------");
			}
		}
	}
	
	public void shareVideo() {
		if (UserGui.girisYapanKullanici == null) {
			System.out.println("Video paylaşmak için giriş yapmanız gereklidir.");
			return;
		}
		
		System.out.print("Title: ");
		String title = scanner.nextLine();
		
		System.out.print("Description: ");
		String description = scanner.nextLine();
		
		Video video = new Video(UserGui.girisYapanKullanici.getId(), title, description);
		videoRepository.save(video);
		
		System.out.println("Video başarıyla paylaşıldı");
	}
	
	public void viewYourAllVideos() {
		if (UserGui.girisYapanKullanici == null) {
			System.out.println("Kendi videolarınızı görüntülemek için giriş yapmanız gerekiyor.");
			return;
		}
		
		List<Video> userVideos = videoRepository.findByUserId(UserGui.girisYapanKullanici.getId());
		
		if (userVideos.isEmpty()) {
			System.out.println("Hiç video paylaşmadınız.");
		}
		else {
			for (Video video : userVideos) {
				System.out.println("-----------------------");
				System.out.println("Başlık: " + video.getTitle());
				System.out.println("İçerik: " + video.getDescription());
				System.out.println("-----------------------");
			}
		}
	}
	public void SelectVideo() {
		List<Video> videos = videoService.viewAllVideos();
		if (videos.isEmpty()) {
			System.out.println("Listelenecek video bulunamadı.");
			return;
		}
		Scanner scanner = new Scanner(System.in);
		System.out.print("İzlemek istediğiğniz videonun başlığını giriniz: ");
		String selectedVideoTitle = scanner.nextLine();
		videoRepository.incrementViewCount(selectedVideoTitle);
	}

}