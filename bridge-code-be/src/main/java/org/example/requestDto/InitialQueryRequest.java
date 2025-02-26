package org.example.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InitialQueryRequest {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("original_codes")
    private String originalCodes;
}
