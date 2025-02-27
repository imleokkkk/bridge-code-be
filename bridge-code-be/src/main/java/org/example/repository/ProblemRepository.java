package org.example.repository;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Stack;

@Getter
@Setter
@Component
public class ProblemRepository {
    private int curLevel = 0;
    private String curProblem;
    private List<List<String>> blocks;
    private List<String> comments;


    private static final String KEYWORDS_REGEX =
            "\\b(def|return|if|else|elif|for|while|import|from|as|class|try|except|finally|with|lambda|yield|print)\\b";
    private static final String IDENTIFIER_REGEX = "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b";
    private static final String NUMBER_REGEX = "\\b\\d+(\\.\\d+)?\\b";
    private static final String OPERATOR_REGEX = "[+\\-*/=<>!&|^%]+";
    private static final String BRACKET_REGEX = "[(){}\\[\\]]";
    private static final String COMMA_COLON_REGEX = "[,:]";
    private static final String NEWLINE_REGEX = "\\n";

    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            KEYWORDS_REGEX + "|" + IDENTIFIER_REGEX + "|" + NUMBER_REGEX + "|" +
                    OPERATOR_REGEX + "|" + BRACKET_REGEX + "|" + COMMA_COLON_REGEX + "|" + NEWLINE_REGEX
    );

    public List<String> tokenize(String code) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(code);
        Stack<String> bracketStack = new Stack<>();

        while (matcher.find()) {
            String token = matcher.group();

            // 괄호 내부인지 확인
            if ("({[".contains(token)) {
                bracketStack.push(token);
            } else if (")}]".contains(token)) {
                if (!bracketStack.isEmpty()) {
                    bracketStack.pop();
                }
            }

            // 줄바꿈이 괄호 내부에 있을 경우 무시
            if (token.equals("\n") && !bracketStack.isEmpty()) {
                continue;
            }

            tokens.add(token);
        }

        return tokens;
    }

}
