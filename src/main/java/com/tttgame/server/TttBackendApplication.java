package com.tttgame.server;

import com.tttgame.server.Model.Users;
import com.tttgame.server.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootApplication
public class TttBackendApplication {

	@Autowired
	UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(TttBackendApplication.class, args);

	}


}
