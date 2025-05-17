package com.app.auth.controller;

import com.app.auth.dto.request.UserProfileRequestDto;
import com.app.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @GetMapping
    public ResponseEntity<UserProfileRequestDto> getCurrentUserProfile(
            @AuthenticationPrincipal User principal) {
        UserProfileRequestDto profileDto = new UserProfileRequestDto();
        profileDto.setName(principal.getName());
        profileDto.setEmail(principal.getEmail());
        return ResponseEntity.ok(profileDto);
    }
}