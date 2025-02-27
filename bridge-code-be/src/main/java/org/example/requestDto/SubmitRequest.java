package org.example.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class SubmitRequest {
    private String userId;
    private List<String> combi;
}
