package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.requestDto.InitialQueryRequest;
import org.example.requestDto.InitRequest;
import org.example.requestDto.SubmitRequest;
import org.example.responseDto.InitResponse;
import org.example.responseDto.SubmitResponse;
import org.example.service.AIService;
import org.example.service.FrontService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@lombok.extern.slf4j.Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FrontController {
    // 여기에서 레벨을 넘겨준다.
    // 그 레벨을 가지고 레포지토리가서 index로 가져오고
    // 그걸 AI Controller에 requestbody로 넘겨줌.

    // 얘는 맞은 거 걸러줘야됨
    // 틀린거 AI 쪽으로 위임

    // wㅓㅇ답인지 아닌지 확인하고
    // 정답이면 -> 들여쓰기 몇 번 해야하는지까지 "+" 붙여서 front에 넘겨줘야함
    // 즉, 받은 list request와 정답을 뒤에서부터 대조해야한다는 뜻.
    // 들여쓰기 몇개 필요한지도 repo에 저장해놔야됨.
    private final AIService aiService;
    private final FrontService frontService;

    @PostMapping("/initial")
    public ResponseEntity<InitResponse> getInitialQuery(@RequestBody InitRequest request){
        InitialQueryRequest initRequest = frontService.convertToInitialQueryRequest(request);
        log.info(initRequest.getOriginalCodes());
        InitResponse response = aiService.sendInitialQuery(initRequest,request.getProblemNum());
        log.info("{} {} {}", response.getProblem(), response.getComments(), response.getBlocks());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/submit")
    public ResponseEntity<SubmitResponse> userInputQeury(@RequestBody SubmitRequest request){
        // 맞는지 틀리는지 확인
        SubmitResponse response = frontService.userInputQuery(request);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/final")
//    public ResponseEntity<FinalResponse> getFinalQuery()
 }
