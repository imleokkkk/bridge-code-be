package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repository.ProblemRepository;
import org.example.requestDto.InitialQueryRequest;
import org.example.responseDto.AiInitialResponse;
import org.example.responseDto.InitResponse;
import org.example.responseDto.SubmitResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
            log.info("Request as JSON: {}", new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(request));

//            AiInitialResponse AIResponse = restClient.post()
//                    .uri("/interpret_initial_code")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(request)
//                    .retrieve()
//                    .body(AiInitialResponse.class);

//            String response = AIResponse.getResponse();
//            log.info(response);

            String response = "Demo";
            StringTokenizer st = new StringTokenizer(response,"\n");


            List<List<String>> chunks = problemRepository.getChunks()[problemRepository.getCurProblemNum()];
            for(List<String> l : chunks){
                log.info("*******************");
                log.info("문장 : "+l);
            }

            List<String> comments = new ArrayList<>();
            while(st.hasMoreTokens()){
                comments.add(st.nextToken());
            }

            for(int i = 0; i<chunks.size(); i++){
                comments.add("주석"+(i+1));
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
}
