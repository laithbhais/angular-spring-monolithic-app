package com.app.auth.controller;

import com.app.auth.dto.request.ZoomMeetingRequestDTO;
import com.app.auth.service.ZoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/zoom")
public class ZoomController {

    private final ZoomService zoomService;

    @RequestMapping(method = RequestMethod.POST, path = "/meeting")
    public ResponseEntity<Map<String, Object>> createMeeting(@RequestBody ZoomMeetingRequestDTO meetingDTO) {
        return ResponseEntity.ok(zoomService.createMeeting(meetingDTO));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/meetings")
    public ResponseEntity<Map<String, Object>> listMeetings() {
        return ResponseEntity.ok(zoomService.listMeetings());
    }
}
