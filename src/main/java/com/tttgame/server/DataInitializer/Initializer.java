package com.tttgame.server.DataInitializer;

import com.tttgame.server.Model.Users;
import com.tttgame.server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count()== 0){
            createDummyUsers();
        }
    }


    private void createDummyUsers(){
        Users user1 = new Users("favour", "password", "favour@mail.com");
        Users user2 = new Users("praise", "password", "praise@mail.com");

        user1.setPassword(new BCryptPasswordEncoder().encode(user1.getPassword()));
        user2.setPassword(new BCryptPasswordEncoder().encode(user2.getPassword()));
        userRepository.save(user1);
        userRepository.save(user2);

        System.out.println("Sample uses created");
    }
}
