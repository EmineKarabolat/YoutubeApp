package com.eminekarabolat.service;

import com.eminekarabolat.dto.request.UserSaveRequestDto;
import com.eminekarabolat.dto.request.UserUpdateRequestDto;
import com.eminekarabolat.dto.response.UserResponseDto;
import com.eminekarabolat.entity.User;
import com.eminekarabolat.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserService {
	private final UserRepository userRepository;
	
	
	public UserService() {
		this.userRepository = new UserRepository();
	}
	
	
	public Optional<UserResponseDto> save(UserSaveRequestDto dto) {
		User user = new User();
		UserResponseDto responseDto = new UserResponseDto();//kullanıcıya dönecek olan cevap
		
		try {
			
			user.setName(dto.getName());
			user.setSurname(dto.getSurname());
			user.setUsername(dto.getUsername());
			user.setEmail(dto.getEmail());
			user.setPassword(dto.getPassword());
			
			Optional<User> savedUser = userRepository.save(user);
			
			responseDto.setName(savedUser.get().getName());
			responseDto.setSurname(savedUser.get().getSurname());
			responseDto.setUsername(savedUser.get().getUsername());
			
			System.out.println(savedUser.get().getUsername() + " başarıyla kaydedildi.");
		}
		catch (Exception e) {
			System.out.println("Service User kaydedilirken hata oluştu: " + e.getMessage());
		}
		return Optional.of(responseDto);
	}
	
	public Optional<UserResponseDto> update(UserUpdateRequestDto dto) {
		UserResponseDto responseDto = new UserResponseDto();//kullanıcıya dönecek olan cevap
		
			try {
				Optional<User> savedUser = userRepository.findByUsername(dto.getUsername());
				if (savedUser.isPresent()) {
					User user = savedUser.get();
					user.setEmail(dto.getEmail());
					user.setUsername(dto.getUsername());
					user.setPassword(dto.getPassword());
					
					Optional<User> updatedUser = userRepository.save(user);
					
					responseDto.setName(updatedUser.get().getName());
					responseDto.setSurname(updatedUser.get().getSurname());
					responseDto.setUsername(updatedUser.get().getUsername());
					
					System.out.println(updatedUser.get().getUsername() + " başarıyla güncellendi.");
				}
				else{
					System.out.println("Aranılan kullanıcı adıyla kullanıcı bulunamadı.");
					return Optional.empty();
				}
			}
			catch (Exception e) {
				System.out.println("Service User güncellenirken hata oluştu: " + e.getMessage());
			}
			
		return Optional.of(responseDto);
	}
	
	
	public void delete(Long id) {
		Optional<User> mevcutUser = findById(id);
		if (mevcutUser.isPresent()) {
			try {
				userRepository.delete(id);
				System.out.println("Service User başarıyla silindi.");
			}
			catch (Exception e) {
				System.out.println("Service User silinirken hata oluştu: " + e.getMessage());
			}
		}
		else {
			System.out.println("Service Silinmek istenen user bulunamadı.");
		}
	}
	
	public List<UserResponseDto> findAll() {
		UserResponseDto responseDto = new UserResponseDto();
		List<User> userList = userRepository.findAll();
		if (userList.isEmpty()) {
			System.out.println("Service Veritabanında kayıtlı user bulunmamaktadır.");
		}
		List<UserResponseDto> responseDtoList = new ArrayList<>();
		for (User user : userList) {
			responseDto.setName(user.getName());
			responseDto.setSurname(user.getSurname());
			responseDto.setUsername(user.getUsername());
			responseDtoList.add(responseDto);
			
		}
		return responseDtoList;
	}
	
	public Optional<User> findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		user.ifPresentOrElse(t -> System.out.println("Service User bulundu: " + t.getUsername()),
		                          () -> System.out.println("Service Böyle bir username bulunamadı."));
		return user;
	}
	
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
}