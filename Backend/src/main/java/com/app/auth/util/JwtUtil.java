package com.app.auth.util;

import com.app.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.app.auth.util.Constants.*;

@RequiredArgsConstructor
@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.access-token-validity}")
    private long accessTokenValidity;
    @Value("${jwt.refresh-token-validity}") @Getter
    private long refreshTokenValidity;

    public String generateAccessToken(String email) {
        User user = new User();
        user.setEmail(email);
        return generateAccessToken(user);
    }

    public String generateRefreshToken(String email) {
        User user = new User();
        user.setEmail(email);
        return generateRefreshToken(user);
    }

    public String generateAccessToken(User user) {
        return generateToken(user, ACCESS_TOKEN, accessTokenValidity);
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, REFRESH_TOKEN, refreshTokenValidity);
    }

    public String generateToken(User user, String tokenType, long tokenValidity) {
        SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(secretKey.getBytes()).getBytes());

        return Jwts.builder()
                .subject(user.getEmail())
                .claims(getClaims(tokenType))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String validateAndExtractEmail(String token) {
        return validateAndExtractClaims(token).getSubject();
    }

    public Claims validateAndExtractClaims(String token) {
        SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(secretKey.getBytes()).getBytes());

        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Map<String, Object> getClaims(String tokenType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE, tokenType);
        return claims;
    }

}