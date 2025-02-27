package org.example.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class InitResponse {
    public String problem;
    public List<String> comments;
    public List<List<String>> blocks;

    // indent 횟수 +
}
