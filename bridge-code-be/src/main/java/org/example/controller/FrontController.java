package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.requestDto.*;
import org.example.responseDto.FinalResponse;
import org.example.responseDto.InitResponse;
import org.example.responseDto.SubmitResponse;
import org.example.service.AIService;
import org.example.service.FrontService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FrontController {
    // 여기에서 레벨을 넘겨준다.
    // 그 레벨을 가지고 레포지토리가서 index로 가져오고
    // 그걸 AI Controller에 requestbody로 넘겨줌.

    // 얘는 맞은 거 걸러줘야됨
    // 틀린거 AI
    private final AIService aiService;
    private final FrontService frontService;

    @PostMapping("/initial")
    public ResponseEntity<InitResponse> getInitialQuery(@RequestBody InitRequest request){
        InitialQueryRequest initRequest = frontService.convertToInitialQueryRequest(request);
        InitResponse response = aiService.sendInitialQuery(initRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<SubmitResponse> userInputQuery(@RequestBody SubmitRequest request){
        // 맞는지 틀리는지 확인
        SubmitResponse response = frontService.userInputQuery(request);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/final")
    public ResponseEntity<FinalResponse> getFinalQuery(@RequestBody FinalRequest request){
        log.info(request.toString());
        FinalSummaryRequest finalSummaryRequest = frontService.convertToFinalSummaryRequest(request);
        FinalResponse response = aiService.finalQuery(finalSummaryRequest);
        return ResponseEntity.ok(response);
    }
 }
