package com.app.auth.feignclient;

import com.app.auth.dto.request.ZoomMeetingRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

import static com.app.auth.util.Constants.AUTHORIZATION_HEADER;

@FeignClient(name = "zoomApiClient", url = "${zoom.api.base-url}")
public interface ZoomApiFeignClient {

    @PostMapping(value = "/users/me/meetings", consumes = "application/json")
    Map<String, Object> createMeeting(
            @RequestHeader(AUTHORIZATION_HEADER) String authHeader,
            @RequestBody ZoomMeetingRequestDTO requestBody);

    @GetMapping("/users/me/meetings")
    Map<String, Object> listMeetings(
            @RequestHeader(AUTHORIZATION_HEADER) String authHeader);
}
