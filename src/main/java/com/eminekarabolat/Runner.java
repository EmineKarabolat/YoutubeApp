package com.eminekarabolat;

import com.eminekarabolat.controller.CommentController;
import com.eminekarabolat.controller.LikeController;
import com.eminekarabolat.controller.UserController;
import com.eminekarabolat.controller.VideoController;
import com.eminekarabolat.controller.CommentController;
import com.eminekarabolat.controller.LikeController;
import com.eminekarabolat.dto.request.*;
import com.eminekarabolat.dto.response.CommentResponseDto;
import com.eminekarabolat.dto.response.LikeResponseDto;
import com.eminekarabolat.dto.response.UserResponseDto;
import com.eminekarabolat.dto.response.VideoResponseDto;
import com.eminekarabolat.entity.Like;
import com.eminekarabolat.gui.UserGui;
import com.eminekarabolat.repository.UserRepository;
import com.eminekarabolat.repository.VideoRepository;
import com.eminekarabolat.service.UserService;
import com.eminekarabolat.service.VideoService;
import com.eminekarabolat.utility.ConnectionProvider;
import com.eminekarabolat.utility.DatabaseSchema;

import java.util.Optional;

public class Runner {
	public static void main(String[] args) {
		//DatabaseSchema.createTables();
		
		UserGui userGui = new UserGui();
		userGui.girisEkrani();
		
	}
	
	
}