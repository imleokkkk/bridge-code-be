package org.example.requestDto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class SubmitAiRequest {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("oneline_code")
    private String onelineCode;
}
