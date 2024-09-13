package com.eminekarabolat;

import com.eminekarabolat.controller.UserController;
import com.eminekarabolat.dto.request.UserSaveRequestDto;
import com.eminekarabolat.dto.request.UserUpdateRequestDto;
import com.eminekarabolat.dto.response.UserResponseDto;
import com.eminekarabolat.repository.UserRepository;
import com.eminekarabolat.service.UserService;
import com.eminekarabolat.utility.DatabaseSchema;

import java.util.Optional;

public class Runner {
	public static void main(String[] args) {
	//	DatabaseSchema.createTables();
		
		UserController userController = new UserController();
		
		//Optional<UserResponseDto> dto = userController.save(new UserSaveRequestDto("Emine","Karabolat","emine@gmail" +
			//	".com","eminek","12345"));
		
		
		UserRepository userRepository = new UserRepository(); // Kullanılan UserRepository implementasyonunuza göre ayarlayın
		UserService userService = new UserService(); // UserService update metodunu içeren sınıf
		
		// Test için UserUpdateRequestDto nesnesi oluştur
		// Kullanıcı bilgilerini güncellemek için DTO nesnesi oluşturuyoruz
		UserUpdateRequestDto updateUser = new UserUpdateRequestDto("newemail@example.com", "newusername", "newpassword");
		
		// Update metodunu test et
		Optional<UserResponseDto> updatedUser = userService.update(updateUser);
		
		// Güncelleme işleminin başarılı olup olmadığını kontrol et
		if (updatedUser.isPresent()) {
			System.out.println("Kullanıcı başarıyla güncellendi: " + updatedUser.get().getUsername());
		} else {
			System.err.println("Kullanıcı güncelleme işlemi başarısız oldu.");
		}
	
	
	}
	
	
	
}