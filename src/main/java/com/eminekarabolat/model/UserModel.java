package com.eminekarabolat.model;

import com.eminekarabolat.controller.CommentController;
import com.eminekarabolat.controller.LikeController;
import com.eminekarabolat.controller.UserController;
import com.eminekarabolat.controller.VideoController;
import com.eminekarabolat.entity.User;

public class UserModel {
	
	public CommentController commentController = new CommentController();
	public UserController userController = new UserController();
	public LikeController likeController = new LikeController();
	public VideoController videoController =new VideoController();
	
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String username;
	private String password;
	
	public UserModel(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.email = user.getEmail();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}
	
	public void displayUserInfo(){
		System.out.println("*********************************");
		System.out.println("---------------------------------");
		System.out.println("Name       : "+ name);
		System.out.println("Surname    : "+ surname);
		System.out.println("Email      : "+ email);
		System.out.println("Username   : "+ username);
		System.out.println("---------------------------------");
	}
}