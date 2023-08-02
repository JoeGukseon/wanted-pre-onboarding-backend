package com.board.auth.utils;


import com.board.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class ErrorResponder {
    private final ObjectMapper objectMapper;
    public void sendErrorResponse(HttpServletResponse response, HttpStatus status, Exception exception) throws IOException {
        ErrorResponse errorResponse;
        if (exception == null) {
            errorResponse = ErrorResponse.of(status);
        } else {
            errorResponse = ErrorResponse.of(status, exception.getMessage());
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());

        try (PrintWriter writer = response.getWriter()) {
            objectMapper.writeValue(writer, errorResponse);
        }
    }
}
