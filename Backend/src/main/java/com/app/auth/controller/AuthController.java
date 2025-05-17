package com.app.auth.controller;

import com.app.auth.dto.request.AuthRequestDto;
import com.app.auth.dto.request.SignupRequestDto;
import com.app.auth.dto.response.JwtResponseDto;
import com.app.auth.dto.response.SignupResponseDto;
import com.app.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.authenticate(authRequestDto.getUsername(), authRequestDto.getPassword(), response));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/refresh-token")
    public ResponseEntity<JwtResponseDto> refreshToken(@CookieValue(name = "refreshToken") String refreshToken, HttpServletResponse response) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken, response));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/google")
    public void redirectToGoogleAuth(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }
}
