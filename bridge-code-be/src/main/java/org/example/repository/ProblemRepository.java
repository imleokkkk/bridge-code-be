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
    private int curLevel;
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

    public static List<String> tokenizePythonCode(String code) {
        List<String> tokens = new ArrayList<>();

        // 문자열 리터럴 패턴 ("" 또는 '')
        Pattern stringPattern = Pattern.compile("([rfb]?[\"'])(.*?)(\\1)");

        // 리스트, 딕셔너리 감지 패턴 (대괄호, 중괄호 포함)
        Pattern listDictPattern = Pattern.compile("\\[[^\\]]*]|\\{[^}]*}");

        // 연산자 및 기호 리스트
        String[] operators = {
                "==", "!=", "<=", ">=", "+=", "-=", "*=", "/=", "//", "**",
                "=", "+", "-", "*", "/", "%", "<", ">", "(", ")", "[", "]", "{", "}", ":", ",", "."
        };

        // 문자열 리터럴 및 리스트/딕셔너리 임시 저장
        List<String> specialLiterals = new ArrayList<>();
        Matcher stringMatcher = stringPattern.matcher(code);
        StringBuffer sb = new StringBuffer();

        // 문자열 리터럴 추출 및 임시 대체
        while (stringMatcher.find()) {
            String found = stringMatcher.group();
            specialLiterals.add(found);
            stringMatcher.appendReplacement(sb, " SPECIAL_LITERAL_" + (specialLiterals.size() - 1) + " ");
        }
        stringMatcher.appendTail(sb);
        code = sb.toString();

        // 리스트/딕셔너리 추출 및 임시 대체
        sb.setLength(0);
        Matcher listDictMatcher = listDictPattern.matcher(code);
        while (listDictMatcher.find()) {
            String found = listDictMatcher.group();
            specialLiterals.add(found);
            listDictMatcher.appendReplacement(sb, " SPECIAL_LITERAL_" + (specialLiterals.size() - 1) + " ");
        }
        listDictMatcher.appendTail(sb);
        code = sb.toString();

        // 연산자 주변에 공백 추가
        for (String op : operators) {
            code = code.replace(op, " " + op + " ");
        }

        // 공백으로 분리
        String[] rawTokens = code.split("\\s+");

        // 빈 토큰 제거 및 리터럴 복원
        for (String token : rawTokens) {
            if (token.isEmpty()) continue;

            // 리터럴 복원
            if (token.startsWith("SPECIAL_LITERAL_")) {
                int index = Integer.parseInt(token.substring("SPECIAL_LITERAL_".length()));
                tokens.add(specialLiterals.get(index));
            } else {
                tokens.add(token);
            }
        }

        return tokens;
    }

}
