package org.example.repository;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
public class ProblemRepository {
    private int curLevel = 0;

    private String[] problems = {"1. 숫자를 부호에 따라 다르게 출력하세요", "2. 1부터 10 중 홀수만 출력하세요.", "3. 주어진 샘플에서 10초과 50미만의 수를 출력하세요."};

    private String[] answers = {"num = int(input(\"숫자를 입력하세요: \"))\n" +
            "if num > 0:\n" +
            "+ print(\"양수입니다.\")\n" +
            "elif num < 0:\n" +
            "+ print(\"음수입니다.\")\n" +
            "else:\n" +
            "+ print(\"0입니다.\")"
            ,
            "for i in range(1, 11):\n" +
                    "+ if i % 2 == 1:\n" +
                    "+ + print(i)"
            ,
            "sample = [1, 11, 22, -20, 55, -33, 0, -5, 12, 35, 19, -9, -1, -100, 90]\n" +
                    "for num in sample:\n" +
                    "+ if 10 < num < 50:\n" +
                    "+ + print(num)"};

    private List<List<String>>[] chunks;
    public ProblemRepository(){
        chunks = new ArrayList[answers.length];
        for(int i = 0; i<answers.length; i++){
            chunks[i] = new ArrayList<>();
        }
        StringTokenizer st;
        for (int i = 0; i < answers.length; i++) {
            st = new StringTokenizer(answers[i], "\n");
            while(st.hasMoreTokens()) {
                chunks[i].add(tokenizePythonCode(st.nextToken()));
            }
        }
    }

    /**
     * 파이썬 코드를 토큰으로 분리하는 메서드
     */
    private static List<String> tokenizePythonCode(String code) {
        List<String> tokens = new ArrayList<>();

        // 문자열 리터럴 매칭 패턴
        Pattern stringPattern = Pattern.compile("\"[^\"]*\"");

        // 연산자 및 기호 리스트
        String[] operators = {"=", "+", "-", "*", "/", "%", "<", ">", "==", "!=", "<=", ">=", "(", ")", "[", "]", ":", ",", "."};

        // 문자열 리터럴 임시 저장
        List<String> stringLiterals = new ArrayList<>();
        Matcher stringMatcher = stringPattern.matcher(code);
        StringBuffer sb = new StringBuffer();

        // 문자열 리터럴 추출 및 임시 대체
        while (stringMatcher.find()) {
            String found = stringMatcher.group();
            stringLiterals.add(found);
            stringMatcher.appendReplacement(sb, " STRING_LITERAL_" + (stringLiterals.size() - 1) + " ");
        }
        stringMatcher.appendTail(sb);

        // 임시 대체된 코드
        String processedCode = sb.toString();

        // 연산자 주변에 공백 추가
        for (String op : operators) {
            processedCode = processedCode.replace(op, " " + op + " ");
        }

        // 공백으로 분리
        String[] rawTokens = processedCode.split("\\s+");

        // 빈 토큰 제거 및 문자열 리터럴 복원
        for (String token : rawTokens) {
            if (token.isEmpty()) continue;

            // 문자열 리터럴 복원
            if (token.startsWith("STRING_LITERAL_")) {
                int index = Integer.parseInt(token.substring("STRING_LITERAL_".length()));
                tokens.add(stringLiterals.get(index));
            } else {
                tokens.add(token);
            }
        }

        return tokens;
    }

}
