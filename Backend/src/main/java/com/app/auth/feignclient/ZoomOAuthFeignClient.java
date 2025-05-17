package com.app.auth.feignclient;

import com.app.auth.util.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "zoomOAuthClient", url = "${zoom.api.oauth-url}")
public interface ZoomOAuthFeignClient {

    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    Map<String, Object> getAccessToken(
            @RequestHeader(Constants.AUTHORIZATION_HEADER) String authHeader,
            @RequestBody Map<String, String> requestBody);
}
