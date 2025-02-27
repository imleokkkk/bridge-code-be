package org.example.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class SubmitResponse {
    private boolean isAnswer;
    private String feedback;
}