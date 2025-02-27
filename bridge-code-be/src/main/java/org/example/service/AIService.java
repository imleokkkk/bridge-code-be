package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repository.ProblemRepository;
import org.example.requestDto.FinalSummaryRequest;
import org.example.requestDto.InitialQueryRequest;
import org.example.requestDto.SubmitAiRequest;
import org.example.responseDto.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIService {
    private final RestClient restClient;
    private final ProblemRepository problemRepository;

    public InitResponse sendInitialQuery(InitialQueryRequest request) {
        try {
            AiInitialResponse AIResponse = restClient.post()
                    .uri("/interpret_initial_code/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(AiInitialResponse.class);

            String response = AIResponse.getResponse();
            log.info(response);

//            String response = "Demo";



            List<List<String>> chunks = problemRepository.getChunks()[problemRepository.getCurProblemNum()];
            for(List<String> l : chunks){
                log.info("*******************");
                log.info("문장 : "+l);
            }

            StringTokenizer st = new StringTokenizer(response,"\n");

            List<String> comments = new ArrayList<>();
            while(st.hasMoreTokens()){
                comments.add(st.nextToken());
            }

            InitResponse initResponse = new InitResponse();
            initResponse.setProblem(problemRepository.getProblems()[problemRepository.getCurProblemNum()]);
            initResponse.setBlocks(chunks);
            initResponse.setComments(comments);

            return initResponse;

        } catch (Exception e) {
            log.error("Error sending request to AI server: {}", e.getMessage());
            InitResponse errorResponse = new InitResponse();
//            errorResponse.setResponse("Error: " + e.getMessage());
            return errorResponse;
        }
    }

    public SubmitAiResponse submitRequest(SubmitAiRequest submitAiRequest) {
        try{
            return restClient.post()
                    .uri("/interpret_user_input/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(submitAiRequest)
                    .retrieve()
                    .body(SubmitAiResponse.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public FinalResponse finalQuery(FinalSummaryRequest finalSummaryRequest) {
        try{
            return restClient.post()
                    .uri("/summarize-user-inputs/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(finalSummaryRequest)
                    .retrieve()
                    .body(FinalResponse.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
