package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repository.ProblemRepository;
import org.example.requestDto.InitialQueryRequest;
import org.example.responseDto.AIResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIService {
    private final RestClient restClient;
    private final ProblemRepository problemRepository;

    public String getInitialQuery(InitialQueryRequest request) {
        try {
            AIResponse response =  restClient.post()
                    .uri("/initial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(AIResponse.class);

            return  response != null ? response.getResponse() : "No response received";
        } catch (HttpClientErrorException e) {
            log.error("HTTP error when calling AI service: {}", e.getMessage());
            return "Error: " + e.getStatusCode() + " " + e.getResponseBodyAsString();
        } catch (RestClientException e) {
            log.error("Error when calling AI service: {}", e.getMessage());
            return "Error: " + e.getMessage();
        }
    }
}
