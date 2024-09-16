package com.eminekarabolat.gui;

import com.eminekarabolat.controller.CommentController;
import com.eminekarabolat.controller.VideoController;
import com.eminekarabolat.service.CommentService;

import java.util.Scanner;

public class CommentGui {
	private static Scanner scanner = new Scanner(System.in);
	private final CommentService commentService;
	private final CommentController commentController;
	
	public CommentGui() {
		this.commentService = new CommentService();
		this.commentController = new CommentController();
	}
	
	public void yorumMenusu(){
		while(true){
			System.out.println(" Yorum Menusu ");
			System.out.println("1-Yorum At");
			System.out.println("2-Yorumu Güncelle");
			System.out.println("3-Yorumu Kaldır");
			System.out.println("0-Çıkış Yap");
			System.out.print("Seçiminiz: ");
			int secim = scanner.nextInt();
			scanner.nextLine();
			switch(secim){
				case 1:{
				commentController.addComment();
					break;
				}
				case 2:{ commentController.editComment();
					break;
				}
				case 3:{
					commentController.removeComment();
					break;
				}
				case 0:{
					System.out.println("Çıkış yapılıyor...");
				}
				default:
					System.out.println("Geçersiz seçenek lütfen uygun seçimi yapınız.");
			}
			
		}
	}
	
}