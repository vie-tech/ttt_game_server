package com.tttgame.server.Service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    public Cookie createCookie(String cookieName, String cookieValue
                               ) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;

    }
}
