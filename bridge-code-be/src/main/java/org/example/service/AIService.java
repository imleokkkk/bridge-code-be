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
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIService {
    private final RestClient restClient;
    private final ProblemRepository problemRepository;

    public InitResponse sendInitialQuery(InitialQueryRequest request) {
        try {
            InitAiResponse AIResponse = restClient.post()
                    .uri("/interpret_initial_code/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(InitAiResponse.class);

            log.info("******* ORIGIN ******");
            log.info(AIResponse.getOrigin());
            log.info("******* RESPONSE ******");
            log.info(AIResponse.getResponse());

            String response = "Demo";

            StringTokenizer st;

//            List<List<String>> chunks = problemRepository.getChunks()[problemRepository.getCurProblemNum()];

            st = new StringTokenizer(AIResponse.getOrigin(),"\n");
            List<List<String>> chunks = new ArrayList<>();
            List<List<String>> shuffledChunks = new ArrayList<>();
            while(st.hasMoreTokens()){
                String nextToken = st.nextToken();
                if(!nextToken.isEmpty()) {
                    if(nextToken.equals("```python") || nextToken.equals("```")){
                        continue;
                    }

                    int plus = 0;
                    if(nextToken.startsWith(" ")){
                        log.info("HERE");
                        for(int i = 0; i<nextToken.length(); i++){
                            if(nextToken.charAt(i) != ' '){
                                plus = i / 4;
                                break;
                            }
                        }

                    }
                    log.info("&&&&&&&&&&&&&&&&&&&&&");
                    log.info(plus+"");
                    log.info(nextToken);
                    log.info(problemRepository.tokenizePythonCode(nextToken).toString());
                    List<String> toAdd = new ArrayList<>();
                    List<String> shuffled = new ArrayList<>();

                    List<String> tokenized = problemRepository.tokenizePythonCode(nextToken);
                    List<String> tokenizedShuffled = new ArrayList<>(tokenized);
                    Collections.shuffle(tokenizedShuffled);

                    for(int i = 0; i<plus; i++){
                        toAdd.add("@");
                        shuffled.add("@");
                    }
                    toAdd.addAll(tokenized);
                    shuffled.addAll(tokenizedShuffled);

                    Collections.shuffle(shuffled);  // 🔹 섞기
                    shuffledChunks.add(shuffled);
                    chunks.add(toAdd);
                }
            }



//            for(List<String> l : chunks){
//                log.info("*******************");
//                log.info("문장 : "+l);
//            }

            st = new StringTokenizer(AIResponse.getResponse(),"\n");

            List<String> comments = new ArrayList<>();
            while(st.hasMoreTokens()){
                String nextToken = st.nextToken();
                if(!nextToken.isEmpty()){
//                    log.info("BYE!!!!!! : "+nextToken);
                    if(nextToken.equals("```python") || nextToken.equals("```")){
                        continue;
                    }
                    log.info(nextToken);
                    comments.add(nextToken);

                }
            }

            InitResponse initResponse = new InitResponse();
            initResponse.setProblem(AIResponse.getTopic());
            initResponse.setBlocks(shuffledChunks);
            initResponse.setComments(comments);
            problemRepository.setCurProblem(AIResponse.getTopic());
            problemRepository.setComments(comments);
            problemRepository.setBlocks(chunks);


            return initResponse;

        } catch (Exception e) {
            e.printStackTrace();
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
