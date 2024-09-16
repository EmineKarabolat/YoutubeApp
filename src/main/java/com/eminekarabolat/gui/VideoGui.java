package com.eminekarabolat.gui;

import com.eminekarabolat.controller.UserController;
import com.eminekarabolat.controller.VideoController;
import com.eminekarabolat.entity.User;
import com.eminekarabolat.entity.Video;
import com.eminekarabolat.repository.VideoRepository;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class VideoGui {
	private static final Scanner scanner = new Scanner(System.in);
	
	private final VideoRepository videoRepository;
	private final VideoController videoController;
	
	public VideoGui() {
		this.videoRepository = new VideoRepository();
		this.videoController = new VideoController();
	}
	
	
	public void girisEkrani() {
		
		while (true) {
			System.out.println("Video Menüsü");
			System.out.println("1-Videoları Görüntüle");
			System.out.println("2-Video Paylaş");
			System.out.println("3-Kendi Videolarını Görüntüle");
			System.out.println("4-Kullanıcıları Listele");
			System.out.println("5-Like İşlemleri");
			System.out.println("6-Yorum İşlemleri");
			System.out.println("0-Cıkış yap");
			System.out.print("Seçiminiz: ");
			
			int secim = scanner.nextInt();
			scanner.nextLine();
			
			switch (secim) {
				case 1: {
					videoController.viewAllVideos();
					break;
				}
				case 2: {
					videoController.shareVideo();
					break;
				}
				case 3: {
					videoController.viewYourAllVideos();
					break;
				}
//				case 4:{
//					kullanicilariListele();
//					break;
//				}
				case 5: {
					LikeGui likeGui = new LikeGui();
					likeGui.likeMenu();
				}
				case 6: {
					CommentGui commentGui = new CommentGui();
					commentGui.yorumMenusu();
				}
				case 0: {
					System.out.println("Ana menuye dönülüyor");
					return;
				}
				default:
					System.out.println("Geçersiz seçenek, lütfen tekrar deneyin.");
				
			}
		}
	}
	
	

}