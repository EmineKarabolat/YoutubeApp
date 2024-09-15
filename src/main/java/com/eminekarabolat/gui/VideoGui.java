package com.eminekarabolat.gui;

import com.eminekarabolat.controller.UserController;
import com.eminekarabolat.entity.User;
import com.eminekarabolat.entity.Video;
import com.eminekarabolat.repository.VideoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class VideoGui {
	private static final Scanner scanner = new Scanner(System.in);

	private final VideoRepository videoRepository;
	
	public VideoGui() {
		this.videoRepository = new VideoRepository();
	}
	
	
	public void girisEkrani(){
		
		while (true){
			System.out.println("Video Menüsü");
			System.out.println("1-Videoları Görüntüle");
			System.out.println("2-Video Paylaş");
			System.out.println("3-Kendi Videolarını Görüntüle");
			System.out.println("4-Kullanıcıları Listele");
			System.out.println("0-Cıkış yap");
			System.out.print("Seçiminiz: ");
			
			int secim = scanner.nextInt();
			scanner.nextLine();
			
			switch (secim){
				case 1:{
					viewAllVideos();
					break;
				}
				case 2:{
					shareVideo();
					break;
				}
				case 3:{
					viewYourAllVideos();
					break;
				}
//				case 4:{
//					kullanicilariListele();
//					break;
//				}
				case 0:{
					System.out.println("Ana menuye dönülüyor");
					return;
				}
				default:
					System.out.println("Geçersiz seçenek, lütfen tekrar deneyin.");
				
			}
		}
	}
	
	
	private void viewAllVideos() {
		List<Video> videos = videoRepository.findAll();
		
		if (videos.isEmpty()) {
			System.out.println("Hiç video bulunmamaktadır.");
		} else {
			for (Video video: videos) {
				System.out.println("Kullanıcı: " + video.getUserId());
				System.out.println("Başlık: " + video.getTitle());
				System.out.println("İçerik: " + video.getDescription());
				System.out.println("-----------------------");
			}
		}
	}
	
	private void shareVideo() {
		if (UserGui.girisYapanKullanici==null){
			System.out.println("Video paylaşmak için giriş yapmanız gereklidir.");
			return;
		}
		
		System.out.print("Title: ");
		String title = scanner.nextLine();
		
		System.out.print("Description: ");
		String description = scanner.nextLine();
		
		Video video  =new Video(UserGui.girisYapanKullanici.getId(), title, description);
		videoRepository.save(video);
		
		System.out.println("Video başarıyla paylaşıldı");
	}
	
	private void viewYourAllVideos() {
		if (UserGui.girisYapanKullanici == null) {
			System.out.println("Kendi videolarınızı görüntülemek için giriş yapmanız gerekiyor.");
			return;
		}

		List<Video> userVideos = videoRepository.findByUserId(UserGui.girisYapanKullanici.getId());

		if (userVideos.isEmpty()) {
			System.out.println("Hiç video paylaşmadınız.");
		} else {
			for (Video video : userVideos) {
				System.out.println("Başlık: " + video.getTitle());
				System.out.println("İçerik: " + video.getDescription());
		
				System.out.println("-----------------------");
			}
		}

	}
//
//	private void kullanicilariListele() {
//		List<User> users = new UserController().getAllUsers(); // Bu metodu UserController'dan implement edin
//
//		if (users.isEmpty()) {
//			System.out.println("Hiç kullanıcı bulunmuyor.");
//		} else {
//			System.out.println("Kullanıcı Listesi:");
//			for (User user : users) {
//				System.out.println("Username: " + user.getUsername());
//			}
//		}
		
}