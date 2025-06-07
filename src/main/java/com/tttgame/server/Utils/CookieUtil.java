package com.tttgame.server.Utils;

import jakarta.servlet.http.Cookie;

public class CookieUtil {
/*    public static boolean isCookieExpired(Cookie cookie) {
        return cookie.getMaxAge() == 0 || cookie.getMaxAge() == -1;
    }*/

    public static boolean doesCookieExist(Cookie cookie) {
        return cookie.getName().equals("my_access_token");
    }
}
