package com.tttgame.server.Service;

import com.tttgame.server.Model.Users;
import com.tttgame.server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean isUserLoggedIn() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken);
    }

    public Users getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {

            Object principal = authentication.getPrincipal();
            if (principal instanceof Users) {
                return (Users) principal;
            }
        }
        return null;
    }


    public Map<String, Object> getUserObject() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {

            Object principal = authentication.getPrincipal();
            if (principal instanceof Users) {
                return Map.of("email", ((Users) principal).getEmail(), "user"
                        , ((Users) principal).getUsername(), "uid", ((Users) principal).getUid());
            }
        }

        return null;
    }

    public void setCurrentUser(UserDetails user) {
        if (user == null) {
            System.out.println("User passed is null (User service)");
            return;
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public List<Users> retrieveAllUsers(){
       return userRepository.findAll();
    }
}