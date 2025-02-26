package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.requestDto.InitialQueryRequest;
import org.example.service.AIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AIController {
    private final AIService aiService;

    @PostMapping("/initial")
    public ResponseEntity<String> getInitialQuery(@RequestBody InitialQueryRequest request){
        String response = aiService.getInitialQuery(request);
        return ResponseEntity.ok().body(response);
    }
}
