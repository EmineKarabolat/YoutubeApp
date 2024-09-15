package com.eminekarabolat.controller;

import com.eminekarabolat.dto.request.UserSaveRequestDto;
import com.eminekarabolat.dto.request.UserUpdateRequestDto;
import com.eminekarabolat.dto.response.UserResponseDto;
import com.eminekarabolat.entity.User;
import com.eminekarabolat.gui.UserGui;
import com.eminekarabolat.repository.UserRepository;
import com.eminekarabolat.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserController {
	
	private final UserRepository userRepository;
	public static User girisYapanKullanici;
	
	private final UserService userService;
	
	public UserController() {
		this.userRepository = new UserRepository();
		this.userService = new UserService();
	}
	
	public Optional<UserResponseDto> save(UserSaveRequestDto dto) {
		try {
			
			System.out.println("Controller User başarıyla kaydedildi.");
			return userService.save(dto);
		
		} catch (Exception e) {
			System.out.println("Controller User kaydedilirken hata oluştu: " + e.getMessage());
		}
		return Optional.empty();
	}
	
	public Optional<UserResponseDto> update(UserUpdateRequestDto dto) {
		try {
			System.out.println("Controller User başarıyla güncellendi.");
			return userService.update(dto);
		} catch (Exception e) {
			System.out.println("Controller User güncellenirken hata oluştu: " + e.getMessage());
		}
		return Optional.empty();
	}
	
	public void delete(Long id) {
		try {
			userService.delete(id);
			System.out.println("Controller User başarıyla silindi.");
		} catch (Exception e) {
			System.out.println("Controller User silinirken hata oluştu: " + e.getMessage());
		}
	}
	
	public List<UserResponseDto> findAll() {
		List<UserResponseDto> userList = userService.findAll();
		if (userList.isEmpty()) {
			System.out.println("Controller Veritabanında kayıtlı User bulunmamaktadır.");
		}
		return userList;
	}
	
	public Optional<User> findById(Long id) {
		Optional<User> userOptional = userService.findById(id);
		userOptional.ifPresentOrElse(
				user -> System.out.println("Controller User bulundu: " + user.getSurname()),
				() -> System.out.println("Controller Böyle bir User bulunamadı.")
		);
		return userOptional;
	}
	
	
	public void register() {
		Scanner scanner = new Scanner(System.in);
		
		String username;
		boolean isRegisteredUser;
		String name,surname,email;
		
		do {
			System.out.print("Adınızı giriniz: ");
			name = scanner.nextLine();
			
			System.out.print("Soyadınızı giriniz: ");
			surname = scanner.nextLine();
			
			System.out.print("Email giriniz: ");
			email = scanner.nextLine();
			
			System.out.print("Username giriniz: ");
			username = scanner.nextLine();
			
			
			isRegisteredUser = !userRepository.existsByUserName(username);
			if (!isRegisteredUser) {
				System.out.println("Bu username zaten alınmış, lütfen başka bir username deneyin.");
			}
			
		} while (!isRegisteredUser);
		
		System.out.print("Password giriniz: ");
		String password = scanner.nextLine();
		
		User user = new User(name,surname,email, username, password);
		userRepository.save(user);
		System.out.println("Kayıt başarıyla tamamlandı!");
	}
	
	public void login() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Username giriniz: ");
		String username = scanner.nextLine();
		
		System.out.print("Password giriniz: ");
		String password = scanner.nextLine();
		
		Optional<User> user = userRepository.doLogin(username, password);
		
		
		// TODO UserGuiyi neden kullandık.
		if (user.isPresent()) {
			UserGui.girisYapanKullanici=user.get(); //girisbasarılı ise kullanıcıyı sakladık
			System.out.println("Giriş başarılı! Hoşgeldin, " + user.get().getName() +" "+ user.get().getSurname()+ "!");
			//burada giriş yapan user'a her yerden erişebilmeli
			
		} else {
			System.out.println("Giriş bilgileri hatalı!");
		}
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}