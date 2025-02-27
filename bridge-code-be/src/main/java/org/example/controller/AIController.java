package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.requestDto.InitialQueryRequest;
import org.example.responseDto.SubmitResponse;
import org.example.service.AIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AIController {
    private final AIService aiService;

//    public ResponseEntity<SubmitResponse> getInitialQuery(@RequestBody InitialQueryRequest request){
//        SubmitResponse response = aiService.sendInitialQuery(request, request.getProblemNum());
//        return ResponseEntity.ok().body(response);
//    }
}
