package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repository.ProblemRepository;
import org.example.requestDto.*;
import org.springframework.stereotype.Service;
import org.example.responseDto.SubmitResponse;
import org.example.responseDto.SubmitAiResponse;

import java.util.Collections;
import java.util.List;

import java.util.StringTokenizer;

@Slf4j
@Service
@RequiredArgsConstructor
public class FrontService {

    private final ProblemRepository problemRepository;
    private final AIService aiService;

    // FrontService
    public InitialQueryRequest convertToInitialQueryRequest(InitRequest request) {
        String id = request.getUserId();

        InitialQueryRequest initialQueryRequest = new InitialQueryRequest();
        initialQueryRequest.setUserId(id);

        return initialQueryRequest;
    }

    public SubmitResponse userInputQuery(SubmitRequest request) {
//        List<String> checkTarget = problemRepository.getChunks()[problemNum].get(problemRepository.getCurLevel());
        List<String> checkTarget = problemRepository.getBlocks().get(problemRepository.getCurLevel());
        log.info(checkTarget.toString());
        List<String> submittedAnswer = request.getCombi();

        SubmitResponse submitResponse = new SubmitResponse();

        for(int i = submittedAnswer.size()-1, j = checkTarget.size()-1 ; i>= 0; i--, j--){
            if(submittedAnswer.get(i).equals(checkTarget.get(j))){
                continue;
            }

            // 다른게 있으면 false 리턴
            SubmitAiRequest submitAiRequest = new SubmitAiRequest();
            submitAiRequest.setUserId(request.getUserId());
            StringBuilder sb = new StringBuilder();
            for(String s : submittedAnswer){
                sb.append(s).append(" ");
            }

            submitAiRequest.setOnelineCode(sb.toString());
            SubmitAiResponse submitAiResponse = aiService.submitRequest(submitAiRequest);

            submitResponse.setFeedback(submitAiResponse.getResponse());
            submitResponse.setAnswer(false);

            return submitResponse;
        }

        submitResponse.setAnswer(true);
        problemRepository.setCurLevel(problemRepository.getCurLevel()+1);
        return submitResponse;
    }

    public FinalSummaryRequest convertToFinalSummaryRequest(FinalRequest request) {
        problemRepository.setCurLevel(0);
        FinalSummaryRequest finalSummaryRequest = new FinalSummaryRequest();
        finalSummaryRequest.setUserId(request.getUserId());
        return finalSummaryRequest;
    }
}
