package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repository.ProblemRepository;
import org.example.requestDto.InitialQueryRequest;
import org.example.requestDto.InitRequest;
import org.example.requestDto.SubmitRequest;
import org.springframework.stereotype.Service;
import org.example.responseDto.SubmitResponse;

import java.util.Collections;
import java.util.List;

import java.util.StringTokenizer;

@Slf4j
@Service
@RequiredArgsConstructor
public class FrontService {

    private final ProblemRepository problemRepository;

    // FrontService
    public InitialQueryRequest convertToInitialQueryRequest(InitRequest request) {
        String id = request.getUserId();
        String problem = problemRepository.getAnswers()[request.getProblemNum()];

        InitialQueryRequest initialQueryRequest = new InitialQueryRequest();
        initialQueryRequest.setUserId(id);
        initialQueryRequest.setOriginalCodes(problem);

        return initialQueryRequest;
    }

    public SubmitResponse userInputQuery(SubmitRequest request) {
        int problemNum = request.getProblemNum();
        List<String> checkTarget = problemRepository.getChunks()[problemNum].get(problemRepository.getCurLevel());
        List<String> submittedAnswer = request.getCombi();

        SubmitResponse submitResponse = new SubmitResponse();

        // 사이즈가 다르면 false 리턴
        if(submittedAnswer.size() != checkTarget.size()) {
            submitResponse.setAnswer(false);
            submitResponse.setFeedback("Demo Feedback");
            return submitResponse;
        }

        for(int i = submittedAnswer.size()-1; i>= 0; i--){
            if(submittedAnswer.get(i).equals(checkTarget.get(i))){
                continue;
            }

            // 다른게 있으면 false 리턴
            log.info(submittedAnswer.get(i)+" is wrong!     "+ " Expected : "+checkTarget.get(i));
            submitResponse.setAnswer(false);
            submitResponse.setFeedback("Demo Feedback");
            return submitResponse;
        }

        submitResponse.setAnswer(true);
        return submitResponse;
    }


}
