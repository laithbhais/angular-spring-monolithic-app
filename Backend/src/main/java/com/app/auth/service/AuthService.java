package com.app.auth.service;

import com.app.auth._enum.UserProvider;
import com.app.auth.dto.request.SignupRequestDto;
import com.app.auth.dto.response.JwtResponseDto;
import com.app.auth.dto.response.SignupResponseDto;
import com.app.auth.entity.User;
import com.app.auth.exception.SignupException;
import com.app.auth.mapper.UserMapper;
import com.app.auth.repository.UserRepository;
import com.app.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public JwtResponseDto authenticate(String username, String password, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userDetailsService.loadUserByUsername(username);

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        setRefreshTokenCookie(refreshToken, response);

        return new JwtResponseDto(accessToken);
    }

    @SneakyThrows
    public SignupResponseDto signup(SignupRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new SignupException("The username is already in use");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new SignupException("The email is already in use");
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProvider(UserProvider.LOCAL);

        userRepository.save(user);

        return new SignupResponseDto("Sign up Successfully");
    }

    public JwtResponseDto refreshToken(String refreshToken, HttpServletResponse response) {
        String email = jwtUtil.validateAndExtractEmail(refreshToken);

        String accessToken = jwtUtil.generateAccessToken(email);
        String newRefreshToken = jwtUtil.generateRefreshToken(email);

        setRefreshTokenCookie(newRefreshToken, response);

        return new JwtResponseDto(accessToken);
    }

    public void setRefreshTokenCookie(String refreshToken, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false) // HTTPS-only
                .path("/api/v1/auth/refresh-token")
                .maxAge(jwtUtil.getRefreshTokenValidity() / 1000)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
