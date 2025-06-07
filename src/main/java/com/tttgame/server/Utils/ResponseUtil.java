package com.tttgame.server.Utils;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class ResponseUtil {

    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status,
                                         String error, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String jsonResponse = String.format("{\"error\": \"%s\", \"message\": \"%s\", \"status\":" +
                " %d}", error, message, status.value());

        response.getWriter().write(jsonResponse);
    }


}

