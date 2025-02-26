package org.example.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserInputRequest {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("oneline_code")
    private String onelineCode;
}
