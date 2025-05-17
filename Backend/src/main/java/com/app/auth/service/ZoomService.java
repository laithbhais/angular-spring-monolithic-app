package com.app.auth.service;

import com.app.auth.dto.request.ZoomMeetingRequestDTO;
import com.app.auth.exception.ZoomException;
import com.app.auth.feignclient.ZoomApiFeignClient;
import com.app.auth.feignclient.ZoomOAuthFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.app.auth.util.Constants.BASIC_AUTH;
import static com.app.auth.util.Constants.BEARER;

@Service
@RequiredArgsConstructor
public class ZoomService {

    @Value("${zoom.api.account-id}")
    private String accountId;
    @Value("${zoom.api.client-id}")
    private String clientId;
    @Value("${zoom.api.client-secret}")
    private String clientSecret;

    private final ZoomApiFeignClient zoomApiFeignClient;
    private final ZoomOAuthFeignClient zoomOAuthFeignClient;

    /*
    Note: Could be enhanced by using proper DTO classes instead of Map<String, Object>
    for better type safety and maintainability   */
    @SneakyThrows
    public Map<String, Object> createMeeting(ZoomMeetingRequestDTO meetingDTO) {
        try {
            String authHeader = BEARER + getOAuthAccessToken();
            return zoomApiFeignClient.createMeeting(authHeader, meetingDTO);
        } catch (FeignException e) {
            throw new ZoomException("Failed to create Zoom meeting: " + e.getMessage());
        }
    }

    /*
    Note: Could be enhanced by using proper DTO classes instead of Map<String, Object>
    for better type safety and maintainability   */
    @SneakyThrows
    public Map<String, Object> listMeetings() {
        try {
            String authHeader = BEARER + getOAuthAccessToken();
            return zoomApiFeignClient.listMeetings(authHeader);
        } catch (FeignException e) {
            throw new ZoomException("Failed to list Zoom meetings: " + e.getMessage());
        }
    }

    private String getOAuthAccessToken() {
        String authHeader = BASIC_AUTH + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "account_credentials");
        requestBody.put("account_id", accountId);

        Map<String, Object> response = zoomOAuthFeignClient.getAccessToken(authHeader, requestBody);
        return (String) response.get("access_token");
    }
}