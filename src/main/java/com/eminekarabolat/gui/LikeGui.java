package com.eminekarabolat.gui;

import com.eminekarabolat.controller.LikeController;
import com.eminekarabolat.entity.Like;
import com.eminekarabolat.repository.LikeRepository;
import com.eminekarabolat.service.LikeService;

import java.util.Scanner;

public class LikeGui {
	private static final Scanner scanner=new Scanner(System.in);
	private final LikeService likeService;
	private final LikeController likeController;
	
	public LikeGui() {
		this.likeService = new LikeService();
		this.likeController= new LikeController();
	}
	
	
	public void likeMenu(){
		while (true) {
			System.out.println("Like Menusu");
			System.out.println("1-Like at");
			System.out.println("2-DissLike at");
			System.out.println("3-Like'ı geri çek");
			System.out.println("0-Cıkış Yap");
			System.out.print("Seçiminiz: ");
			int secim = scanner.nextInt();
			scanner.nextLine();
			
			switch (secim) {
				case 1:{
					likeController.throwALike();
					break;
				}
				case 2:{
					likeController.throwADissLike();
					break;
				}
				case 3:{
					likeController.withDrawLike();
					break;
				}
				case 0:{
					System.out.println("Çıkış yapılıyor...");
					break;
				}
				default:
					System.out.println("Geçersiz seçenek, lütfen tekrar deneyin.");
				
			}
		}
	}
	

	
}