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
        String problem = problemRepository.getAnswers()[problemRepository.getCurProblemNum()];

        InitialQueryRequest initialQueryRequest = new InitialQueryRequest();
        initialQueryRequest.setUserId(id);
        initialQueryRequest.setOriginalCodes(problem);

        return initialQueryRequest;
    }

    public SubmitResponse userInputQuery(SubmitRequest request) {
        int problemNum = problemRepository.getCurProblemNum();
        List<String> checkTarget = problemRepository.getChunks()[problemNum].get(problemRepository.getCurLevel());
        List<String> submittedAnswer = request.getCombi();

        SubmitResponse submitResponse = new SubmitResponse();

        for(int i = submittedAnswer.size()-1, j = checkTarget.size()-1 ; i>= 0; i--, j--){
            if(submittedAnswer.get(i).equals(checkTarget.get(j))){
                continue;
            }

            // 다른게 있으면 false 리턴
            submitResponse.setAnswer(false);
            submitResponse.setFeedback("Demo Feedback");
            return submitResponse;
        }

        submitResponse.setAnswer(true);
        problemRepository.setCurLevel(problemRepository.getCurLevel()+1);
        problemRepository.visited[problemRepository.getCurProblemNum()] = false;
        return submitResponse;
    }
}
