package com.board.auth.handler;

import com.board.auth.utils.ErrorResponder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ErrorResponder errorResponder;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        errorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, exception);
    }
}
